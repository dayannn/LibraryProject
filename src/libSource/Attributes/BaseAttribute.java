package libSource.Attributes;


import javax.swing.*;
import java.util.List;

public class BaseAttribute
{
    protected String tableName = "";
    protected String name = "";
    protected String value = "";
    protected String midT = "";
    protected DefaultListModel<String> values = new DefaultListModel<String>();

    public DefaultListModel<String> getValues() {
        if (values.size() == 0)
            values.addElement("0");
        return values;
    }

    public void setValues(DefaultListModel<String> values) {
        this.values = values;
    }

    public BaseAttribute(String value) {
        tableName = "";
        name = "";
        this.value = value;
        midT = "";
        values.clear();
    }

    public BaseAttribute()
    {
        tableName = "";
        name = "";
        value = "";
        midT = "";
        values.clear();
    }

    public BaseAttribute(String _tableName, String _name, String _value, String _midT)
    {
        tableName = _tableName;
        name = _name;
        value = _value;
        midT = _midT;
        values.clear();
    }

    public BaseAttribute(BaseAttribute attr)
    {
        tableName = attr.tableName;
        name = attr.name;
        value = attr.value;
        midT = attr.midT;
        values.clear();
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