package libSource.Attributes;


public class BaseAttribute
{
    protected String tableName = "";
    protected String name = "";
    protected String value = "";
    protected String midT = "";

    public BaseAttribute(String value) {
        tableName = "";
        name = "";
        this.value = value;
        midT = "";
    }

    public BaseAttribute()
    {
        tableName = "";
        name = "";
        value = "";
        midT = "";
    }

    public BaseAttribute(String _tableName, String _name, String _value, String _midT)
    {
        tableName = _tableName;
        name = _name;
        value = _value;
        midT = _midT;
    }

    public BaseAttribute(BaseAttribute attr)
    {
        tableName = attr.tableName;
        name = attr.name;
        value = attr.value;
        midT = attr.midT;
    }


    public String getMidT() {
        return midT;
    }

    public void setMidT(String _midT) {
        this.midT = _midT;
    }

    public String getAttributeTableName()
    {
        return tableName;
    }

    public String getAttributeName()
    {
        return name;
    }

    public String getAttributeValue()
    {
        return value;
    }


    public void setAttributeTableName(String _tableName)
    {
        tableName = _tableName;
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