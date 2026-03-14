import java.awt.*;
import java.awt.event.*;
public class ConfirmationDialog extends Dialog
{
private ConfirmationDialogResult confirmationDialogResult;
public static final int YES_BUTTON = 20;
public static final int NO_BUTTON = 33;
private Button yesButton;
private Button noButton;
private Frame frame;
private ConfirmationDialog(Frame frame, String title, String message,ConfirmationDialogResult confirmationDialogResult)
{
super(frame,title,true);
this.frame = frame;
int frameWidth = 0;
int frameHeight = 0;
int h = 125;
int w = 600;
int x,y;

Toolkit toolkit = Toolkit.getDefaultToolkit();
Dimension dimension = toolkit.getScreenSize();

if(this.frame != null)
{
frameWidth = frame.getWidth();
frameHeight = frame.getHeight();
Point p = this.frame.getLocation();
x = p.x + frameWidth/2 - w/2;
y = p.y + frameHeight/2 - h/2;
}
else
{
x = (dimension.width/2) - w/2;
y = (dimension.height/2) - h/2;
}

this.confirmationDialogResult = confirmationDialogResult;
Label label = new Label(message);
label.setAlignment(Label.CENTER);
add(label,BorderLayout.CENTER);
yesButton = new Button("Yes");
noButton = new Button("No");

Panel panel;
panel = new Panel();
panel.setLayout(new GridLayout(2,5));
panel.add(new Label(" "));
panel.add(yesButton);
panel.add(new Label(" "));
panel.add(noButton);
panel.add(new Label(" "));
for(int i = 1; i <= 5; i++) panel.add(new Label(" "));
add(panel,BorderLayout.SOUTH);

Image iconImage = toolkit.getImage("notebook.png");

setIconImage(iconImage);
setLocation(x,y);
setSize(w,h);

yesButton.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent ev)
{
ConfirmationDialog.this.confirmationDialogResult.setYes();
ConfirmationDialog.this.dispose();
}
});

noButton.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent ev)
{
ConfirmationDialog.this.confirmationDialogResult.setNo();
ConfirmationDialog.this.dispose();
}
});

addWindowListener(new WindowAdapter() {
public void windowClosing(WindowEvent ev)
{
ConfirmationDialog.this.confirmationDialogResult.setNo();
ConfirmationDialog.this.dispose();
}
});

setVisible(true);

}

public static int showConfirmationDialog(Frame frame, String title, String message)
{
ConfirmationDialogResult confirmationDialogResult;
confirmationDialogResult = new ConfirmationDialogResult();
ConfirmationDialog confirmationDialog;
confirmationDialog = new ConfirmationDialog(frame,title,message,confirmationDialogResult);

if(confirmationDialogResult.isYes())
{
return ConfirmationDialog.YES_BUTTON;
}
else
{
return ConfirmationDialog.NO_BUTTON;
}

}

public static void main(String aa[])
{
int buttonClicked = ConfirmationDialog.showConfirmationDialog(null,"Save","File not saved, SAVE?");
System.out.println("Returned :"+buttonClicked);
if(buttonClicked==ConfirmationDialog.YES_BUTTON)
{
System.out.println("User says yes");
}
if(buttonClicked==ConfirmationDialog.NO_BUTTON)
{
System.out.println("User says No");
}
}
}