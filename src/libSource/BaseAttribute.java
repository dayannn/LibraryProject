package libSource;


public class BaseAttribute
{
    protected String value = "";

    protected String name = "";

    public BaseAttribute(String _name, String _value)
    {
        name = _name;
        value = _value;
    }

    public BaseAttribute(BaseAttribute attr)
    {
        name = attr.name;
        value = attr.value;
    }

    public BaseAttribute()
    {
    }

    public String getAttributeName()
    {
        return name;
    }

    public String getAttributeValue()
    {
        return value;
    }



    public void setAttributeName(String _name)
    {
        name = _name;
    }

    public void setAttributeValue(String _value)
    {
        value = _value;
    }
}
