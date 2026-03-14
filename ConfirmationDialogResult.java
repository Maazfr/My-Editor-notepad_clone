public class ConfirmationDialogResult
{
private boolean _isYes = false;
public void setYes()
{
this._isYes = true;
}
public void setNo()
{
this._isYes = false;
}
public boolean isYes()
{
return this._isYes;
}

}