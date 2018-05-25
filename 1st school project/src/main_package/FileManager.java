package main_package;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.tree.*;
import java.io.*;
import java.nio.channels.FileChannel;

import javax.swing.event.*;

import java.util.*;

import java.text.*;

class FileManager implements TreeWillExpandListener,TreeSelectionListener
{	
	static String title[]={"이름(Name)","크기(Size)","수정한 날짜(Modified)"};
	static String error = "디스크 혹은 파일을 찾을수 없습니다.\n 혹은 불가능한 접근경로입니다.";
	static String Show = "폴더로 보기";
	static String Copy = "복사 ";
	static String Paste = "붙여넣기";
	static String Delete = "삭제";
	static String replaceMessage = "그곳에 똑같은 이름의 파일이 존재합니다. \n 복사하시겠습니까?";
	private String language[] = {"한국어","English"};
	static JFrame frame = new JFrame("파일 탐색기");
 	private Container con = null;

 	private JSplitPane pMain=new JSplitPane();
 	private JScrollPane pLeft=null;
 	private JPanel pRight=new JPanel(new BorderLayout());
 
 	private DefaultMutableTreeNode root = new DefaultMutableTreeNode("내 컴퓨터");
 	private JTree tree;
 
 	private JPanel pNorth=new JPanel();
 	private JPanel pSouth=new JPanel();
 	private JPanel northText=new JPanel();
 	private JLabel northLabel=new JLabel("경  로");
 	private JLabel southLabel=new JLabel("파일  관리자");
 	static JTextField pathText=new JTextField();
 	private JComboBox<String> langchk=new JComboBox<String>(language);
 
 	private Dimension dim,dim1;
 	private int xpos,ypos;
 	
 	static JPopupMenu rPopup = new JPopupMenu();
 	static JMenuItem showMenu = new JMenuItem(Show);
 	static JMenuItem copyMenu = new JMenuItem(Copy);
    static JMenuItem pasteMenu = new JMenuItem(Paste);
    static JMenuItem deleteMenu = new JMenuItem(Delete);
 	
    FileManager()
	{
		  init();
		  start();
		  langchk.setSelectedIndex(0);
		  langchk.addActionListener(new ActionListener() {
			  public void actionPerformed(ActionEvent e) {
				    String lang = (String)langchk.getSelectedItem();
				    if (lang.equals("한국어")) {
				   	 	northLabel.setText("경  로");
				    	southLabel.setText("파일  관리자");
				    	root.setUserObject("내컴퓨터");
				    	error = "디스크 혹은 파일을 찾을수 없습니다.\n 혹은 불가능한 접근경로입니다";
				    	tree.repaint();
				    	frame.setTitle("파일 탐색기");
				    	showMenu.setText("폴더로 보기");
				    	copyMenu.setText("복사");
				    	pasteMenu.setText("붙여넣기");
				    	deleteMenu.setText("삭제");
				    	replaceMessage = "그곳에 똑같은 이름의 파일이 존재합니다. \n 복사하시겠습니까?";
				    	showMenu.repaint();
				    	copyMenu.repaint();
				    	pasteMenu.repaint();
				    	deleteMenu.repaint();
				    	rPopup.repaint();
				    	FView.jt.repaint();
				    }
				    else if (lang.equals("English")) {
				    	northLabel.setText("Location");
				    	southLabel.setText("File  Manager");
				    	root.setUserObject("My PC");
				    	error = "Can't fine Disk or File \n or can't access directory";
				    	tree.repaint();
				    	frame.setTitle("File Explorer");
				    	showMenu.setText("Show Item in the Folder");
				    	copyMenu.setText("Copy");
				    	pasteMenu.setText("Paste");
				    	deleteMenu.setText("Delete");
				    	replaceMessage = "There's already a file with the same name.\n" + 
				    			"Do you want to replace it?";
				    	showMenu.repaint();
				    	copyMenu.repaint();
				    	pasteMenu.repaint();
				    	deleteMenu.repaint();
				    	rPopup.repaint();
				    	FView.jt.repaint();
				    }
				}
		  });
		  
		  
		  frame.setSize(800,600);
		  dim=Toolkit.getDefaultToolkit().getScreenSize();
		  dim1=frame.getSize();
		  xpos=(int)(dim.getWidth()/2-dim1.getWidth()/2);
		  ypos=(int)(dim.getHeight()/2-dim1.getHeight()/2);
		  frame.setLocation(xpos,ypos);
		  frame.setVisible(true);
	 }
	
void init()
{
	pMain.setResizeWeight(1);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	con = frame.getContentPane();
	con.setLayout(new BorderLayout());
	pSouth.setLayout(new BorderLayout());
	rPopup.add(showMenu);
	rPopup.add(copyMenu);
	rPopup.add(pasteMenu);
	rPopup.add(deleteMenu);
	pasteMenu.setEnabled(false);
	pathText.setPreferredSize(new Dimension(600,20));
	northText.add(northLabel);
	northText.add(pathText);
	pSouth.add(southLabel,"West");
	pSouth.add(langchk,"East");
	pNorth.add(northText);
	con.add(pNorth,"North");
	con.add(pSouth,"South");
	File file=new File("");
	File list[]=file.listRoots();
	DefaultMutableTreeNode temp;

  for(int i=0;i<list.length;++i)
  {
	   temp=new DefaultMutableTreeNode(list[i].getPath());
	   temp.add(new DefaultMutableTreeNode("X"));
	   root.add(temp);
  }
	  tree=new JTree(root);
	  pLeft=new JScrollPane(tree);
	  
	  pMain.setDividerLocation(150);
	  pMain.setLeftComponent(pLeft);
	  pMain.setRightComponent(pRight);
	  con.add(pMain);
}

 void start()
 {
	  tree.addTreeWillExpandListener(this);
	  tree.addTreeSelectionListener(this);
	  
 }
public static void main(String args[])
{
	  new FileManager();
}

 String getPath(TreeExpansionEvent e)
 {
	  StringBuffer path=new StringBuffer();
	  TreePath temp=e.getPath(); 
	  Object list[]=temp.getPath();
	  for(int i=0;i<list.length;++i)
  {
   if(i>0)
   {
	   path.append(((DefaultMutableTreeNode)list[i]).getUserObject()+"\\");
   }
  }
  return path.toString();
 }
 String getPath(TreeSelectionEvent e)
{
	  StringBuffer path=new StringBuffer();
	  TreePath temp=e.getPath(); 
	  Object list[]=temp.getPath();
	  for(int i=0;i<list.length;++i)
	  {
		  if(i>0)
		  {
			  path.append(((DefaultMutableTreeNode)list[i]).getUserObject()+"\\");
		  }
	  }	
	  return path.toString();
}
 
 public void treeWillCollapse(TreeExpansionEvent event){}
 
 public void treeWillExpand(TreeExpansionEvent e)
 {
  if(((String)((DefaultMutableTreeNode)e.getPath().getLastPathComponent()).getUserObject()).equals("����ǻ��")){}
  
  else
  {
	DefaultMutableTreeNode parent=(DefaultMutableTreeNode)e.getPath().getLastPathComponent();
	File tempFile=new File(getPath(e));
	File list[]=tempFile.listFiles();
	DefaultMutableTreeNode tempChild;
	for(File temp:list)
	{
	    try {
	    	if(temp.isDirectory() && !temp.isHidden())
	    	{
	    		tempChild=new DefaultMutableTreeNode(temp.getName());
	    			if(true)
	    			{
				       File inFile=new File(getPath(e)+temp.getName()+"\\");
				       File inFileList[]=inFile.listFiles();
				       		for(File inTemp:inFileList)
				       		{
				       			if(inTemp.isDirectory() && !inTemp.isHidden())
				       			{
				       				tempChild.add(new DefaultMutableTreeNode("X"));
				       				break;
				       			}
				       		}
	    			}
	    	parent.add(tempChild);
	    	}
	    }
	   	catch(Exception ex)
	    {
	     //JOptionPane.showMessageDialog(frame, error);
	    
	    }
	}
    parent.remove(0);
  }
}
 public void valueChanged(TreeSelectionEvent e)
 {
	 if(((String)((DefaultMutableTreeNode)e.getPath().getLastPathComponent()).getUserObject()).equals("����ǻ��")){
		 pathText.setText("����ǻ��");
 }
  else
  {
	  try
	  {
	    pathText.setText(getPath(e));
	    FView View = new FView(getPath(e));
	    pRight= View.getTablePanel();
	    pMain.setRightComponent(pRight);
	  }
	  catch(Exception ex)
   {
    JOptionPane.showMessageDialog(frame, error);
   }
  }
 }
}



 

class FView
{ 
 static ATable at=new ATable();
 static JTable jt=new JTable(at);
 static File copySourceDir;
 static String copySourceName;
 
 private static JPanel pMain=new JPanel(new BorderLayout());
 private static JScrollPane pCenter=new JScrollPane(jt);

 private File file;
 private File list[];
 private long size=0,time=0;

 FView(String str){
  init();
  start(str);
  FileManager.showMenu.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			int row = jt.getSelectedRow();
			int col = jt.getSelectedColumn();
				try {
					File open_Directory = new File(FileManager.pathText.getText());
					Desktop.getDesktop().open(new File(open_Directory,jt.getValueAt(row, col).toString()));
				} catch (Exception ignored) {

				}
		}
	});
  FileManager.copyMenu.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			int row = jt.getSelectedRow();
			int col = jt.getSelectedColumn();
			try {
				File open_Directory = new File(FileManager.pathText.getText());
				copySourceDir = new File(open_Directory, jt.getValueAt(row, col).toString());
				copySourceName = ((String)jt.getValueAt(jt.getSelectedRow(),0));
			} catch (Exception e2) {
				// TODO: handle exception
				
			}
			FileManager.pasteMenu.setEnabled(true);
		}
		
	});
  FileManager.pasteMenu.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			
			File copyFile = new File(copySourceDir, copySourceName);
	        if (copyFile.exists()) {
	            try {
	                if (copySourceDir.getCanonicalPath().equals(copySourceDir.getCanonicalPath())) {
	                    int extensionPoint = copySourceName.lastIndexOf('.');
	                    String name = (extensionPoint != -1) ? copySourceName.substring(0, extensionPoint) : copySourceName;
	                    String extension = (extensionPoint != -1) ? copySourceName.substring(extensionPoint) : "";
	                    copyFile = new File(copySourceDir, name + " - " + "Copy" + extension);
	                    {
	                        int i = 2;
	                        while (copyFile.exists()) {
	                            copyFile = new File(copySourceDir, name + " - " + "Copy" + " (" + i++ + ")" + extension);
	                        }
	                    }
	                } 
	             }catch (IOException e5) {
	                e5.printStackTrace();
	                System.out.println("X");
	            }
	        }
	        //무조건 실행
	        final File newCopyFile = copyFile;
	        Thread pasteThread = new Thread(() -> {
	            try (
	                    FileInputStream inputStream = new FileInputStream(new File(copySourceDir, copySourceName));
	                    FileOutputStream outputStream = new FileOutputStream(newCopyFile);
	                    FileChannel inChannel = inputStream.getChannel();
	                    FileChannel outChannel = outputStream.getChannel()) {
	                long size = inChannel.size();
	                inChannel.transferTo(0, size, outChannel);
	            } catch (FileNotFoundException e3) {
	            	System.out.println("XF");
	            } catch (IOException e4) {
	                e4.printStackTrace();
	                System.out.println("X");
	            }
	        });
	        pasteThread.start();
	        FileManager.pasteMenu.setEnabled(false);
		}
	});
  FileManager.deleteMenu.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			//File f;
			//f.delete();
		}
	});
  jt.addMouseListener(new MouseListener() {
	
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getButton() ==3) //���콺 ������ ��ư�̸�
        {
			FileManager.rPopup.show((Component)e.getSource(), e.getX(), e.getY());
        }
	}
});
 }

 static void init(){
  pMain.add(pCenter,"Center");
 }
 ATable getATable() {
	 return at;
 }
 void start(String strPath)
 {
  file=new File(strPath);
  list=file.listFiles();
  at.setValueArr(list.length);
  for(int i=0;i<list.length;++i)
  {
   size=list[i].length();
   time=list[i].lastModified();
   for(int j=0;j<3;++j)
   {
    switch(j)
    {
     case 0:
      at.setValueAt(list[i].getName(),i,j);
      break;
     case 1:
      if(list[i].isFile())
    	  if((long)Math.round(size)>1024)
    		  at.setValueAt(Long.toString((long)Math.round(size/1024.0))+"KB",i,j);
    	  if((long)Math.round(size/1024.0)>1024)
    		  at.setValueAt(Long.toString(Math.round(size/1024.0/1024.0))+"MB",i,j);
    	  if(1024>(long)Math.round(size));
    		  
    		  
      break;
     case 2:
      at.setValueAt(getFormatString(time),i,j);
      break;
    }
   }
  }
  jt.repaint();
  pCenter.setVisible(false);
  pCenter.setVisible(true);
 }

 String getLastName(String name)
 {
  int pos=name.lastIndexOf(".");
  String result=name.substring(pos+1,name.length());
  return result;
 }
 String getFormatString(long time)
 {
  SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm a");
  Date d=new Date(time);
  String temp = sdf.format(d);
  return temp;
 }
 JPanel getTablePanel()
 {
  return pMain;
 }
}

 

class ATable extends AbstractTableModel
{
 static String val[][]=new String[1][3];
 
 public void setValueArr(int i)
 {
  val=new String[i][3];
 }
 public int getRowCount()
 {
  return val.length;
 }
 public int getColumnCount()
 {
  return val[0].length;
 }
 public void setColumnName(String[] e)
 {
	 FileManager.title = e;
 }
 public String getColumnName(int column )
 {
  return FileManager.title[column];
 }
 public boolean isCellEditable(int rowIndex, int columnIndex)
 {
  if(columnIndex==0)
   return true;
  else
   return false;
 }
 public Object getValueAt(int row, int column)
 {
  return val[row][column];
 }
 public void setValueAt(String aValue, int rowIndex, int columnIndex ){
  val[rowIndex][columnIndex] = aValue;
 }
}
