import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class CloneEditor extends Frame implements CrossButtonListener
{
private boolean changesSaved;
private String fileName;
private MenuBar menuBar;
private Menu fileMenu;
private Menu editMenu;

// for File Menu

private MenuItem newMenuItem;
private MenuItem saveMenuItem;
private MenuItem saveAsMenuItem;
private MenuItem openMenuItem;
private MenuItem exitMenuItem;

// for Edit Menu

private MenuItem copyMenuItem;
private MenuItem cutMenuItem;
private MenuItem pasteMenuItem;
private MenuItem fontMenuItem;
private TextArea editorTextArea;

private void initialSetup()
{
this.changesSaved = true;
}


public CloneEditor(String fileName)
{
this.fileName = fileName;
initialSetup();
createComponents();
addEventListener();
setupAppearance();

if(this.fileName != null)
{
boolean create = false;
File file = new File(this.fileName);

if(file.exists() == false)
{

int buttonClicked = ConfirmationDialog.showConfirmationDialog(this,"Create","File does not exists, create?");
if(buttonClicked == ConfirmationDialog.YES_BUTTON)
{
create = true;
}
if(buttonClicked == ConfirmationDialog.NO_BUTTON)
{
this.fileName = "noname.txt";
}

}

if(create) createFile();
openFile();

}
else
{
this.fileName = "noname.txt";
}
setTitle(this.fileName+"-CloneEditor");
}

private void createFile()
{
try
{
File file = new File(this.fileName);
if(file.exists()) return;
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");
randomAccessFile.close();
}catch(IOException ioException)
{
}

}

private void createComponents()
{
newMenuItem = new MenuItem("New");
openMenuItem = new MenuItem("Open");
saveMenuItem = new MenuItem("Save");
saveAsMenuItem = new MenuItem("Save As");

// exit menu shortcut
MenuShortcut exitMenuShortcut;
exitMenuShortcut = new MenuShortcut(KeyEvent.VK_X);
exitMenuItem = new MenuItem("Exit",exitMenuShortcut);

// save menu shortcut
MenuShortcut saveMenuShortcut;
saveMenuShortcut = new MenuShortcut(KeyEvent.VK_S);
saveMenuItem = new MenuItem("Save",saveMenuShortcut);

// new menu shortcut
MenuShortcut newMenuShortcut;
newMenuShortcut = new MenuShortcut(KeyEvent.VK_N);
newMenuItem = new MenuItem("New",newMenuShortcut);

// open menu shortcut
MenuShortcut openMenuShortcut;
openMenuShortcut = new MenuShortcut(KeyEvent.VK_O);
openMenuItem = new MenuItem("Open",openMenuShortcut);

// saveAs menu shortcut
MenuShortcut saveAsMenuShortcut;
saveAsMenuShortcut  = new MenuShortcut(KeyEvent.VK_R);
saveAsMenuItem = new MenuItem("Save As",saveAsMenuShortcut);

copyMenuItem = new MenuItem("Copy");
cutMenuItem = new MenuItem("Cut");
pasteMenuItem = new MenuItem("Paste");
fontMenuItem = new MenuItem("Set Font");

fileMenu = new Menu("File");
editMenu = new Menu("Edit");

fileMenu.add(newMenuItem);
fileMenu.add(openMenuItem);
fileMenu.add(saveMenuItem);
fileMenu.add(saveAsMenuItem);
fileMenu.add(exitMenuItem);
editMenu.add(copyMenuItem);
editMenu.add(cutMenuItem);
editMenu.add(pasteMenuItem);
editMenu.add(fontMenuItem);

menuBar = new MenuBar();
menuBar.add(fileMenu);
menuBar.add(editMenu);
setMenuBar(menuBar);

editorTextArea = new TextArea();

setLayout(new BorderLayout());
add(editorTextArea,BorderLayout.CENTER);

}

private void setupAppearance()
{
Toolkit toolkit = Toolkit.getDefaultToolkit();
Dimension dimension = toolkit.getScreenSize();

int w = dimension.width - 100;
int h = dimension.height - 100;

Image iconImage = toolkit.getImage("notebook.png");
setIconImage(iconImage);
setSize(w,h);
int x = dimension.width/2  - w/2;
int y = dimension.height/2 - h/2;
setLocation(x,y);
setVisible(true);

}

private void addEventListener()
{

exitMenuItem.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent ev)
{
CloneEditor.this.closeApplication();
}
});

saveMenuItem.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent ev)
{
CloneEditor.this.saveFile();
}
});

this.editorTextArea.addTextListener(new TextListener() {
public void textValueChanged(TextEvent ev)
{
CloneEditor.this.changesSaved = false;
}
});

addWindowListener(new CrossButtonHandler(this));
}

public void crossButtonClicked(WindowEvent ev)
{
this.closeApplication();
}

public void saveFile()
{

try
{

File file = new File(this.fileName);
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");
randomAccessFile.writeBytes(editorTextArea.getText());
this.changesSaved = true;

}catch(IOException ioException)
{
}

}

private boolean openFile()
{
try
{
File file = new File(this.fileName);
if(file.exists() == false) return false;
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"r");
StringBuffer stringBuffer = new StringBuffer();
while(randomAccessFile.getFilePointer() < randomAccessFile.length())
{
stringBuffer.append(randomAccessFile.readLine() + "\n");
}
randomAccessFile.close();
editorTextArea.append(stringBuffer.toString());
}catch(IOException ioException)
{
return false;
}

return true;
}

public void closeApplication()
{
if(!this.changesSaved)
{
//Show confirmation dialog box
int buttonClicked = ConfirmationDialog.showConfirmationDialog(this,"Save","file Not saved, Save?");
if(buttonClicked == ConfirmationDialog.YES_BUTTON)
{
this.saveFile();
}
this.dispose();
System.exit(0);
}
}

public static void main(String args[])
{
String fileName = "noname.txt";
if(args.length > 0) fileName = args[0];
CloneEditor cloneEditor = new CloneEditor(fileName);
}
}