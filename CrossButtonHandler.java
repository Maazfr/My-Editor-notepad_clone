import java.awt.event.*;

public class CrossButtonHandler extends WindowAdapter
{
private CrossButtonListener target;
public CrossButtonHandler(CrossButtonListener target)
{
this.target = target;
}
public void windowClosing(WindowEvent ev)
{
this.target.crossButtonClicked(ev);
}
}