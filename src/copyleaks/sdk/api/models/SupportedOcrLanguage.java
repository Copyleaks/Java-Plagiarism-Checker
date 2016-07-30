package copyleaks.sdk.api.models;

public class SupportedOcrLanguage
{
    private String Name;

    private String ID;

    public String getName ()
    {
        return Name;
    }

    public void setName (String Name)
    {
        this.Name = Name;
    }

    public String getID ()
    {
        return ID;
    }

    public void setID (String ID)
    {
        this.ID = ID;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Name = "+Name+", ID = "+ID+"]";
    }
}