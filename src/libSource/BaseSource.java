package libSource;


import libSource.Attributes.BaseAttribute;

import javax.swing.*;

abstract public class BaseSource
{
    protected AttributeList attributeList = new AttributeList();

    protected BaseAttributeListWorker attributeSearcher = new AttributeListWorker();

    abstract public String getAttributeValueByName(String attributeName);
    abstract public void setAttributeValueByName(String attributeName, String attributeValue);
    abstract public void setAttribute(BaseAttribute baseAttribute);
    abstract public int getAttributeCount();
    abstract public BaseAttribute getAttribute (int index);

    abstract public String getName();

    abstract public String getAccessType();

    abstract public String getDescription();

    abstract public String getLink();

    abstract public AttributeList getList();

    abstract public String getTheme();

    abstract public String getType();

    abstract public int getLength();

    abstract public void setName(String name);

    abstract public BaseAttribute getAttributeByName(String name);

    abstract public void setAccessType(String accessType);

    abstract public void setDescription(String description);

    abstract public void setLink(String link);

    abstract public void setThemeList(DefaultListModel<String> lst);

    abstract public void setTheme(String theme);

    abstract public void setType(String type);
}
