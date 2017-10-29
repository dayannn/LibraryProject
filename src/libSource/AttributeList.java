package libSource;
import  libSource.Attributes.*;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AttributeList
{
    private List<BaseAttribute> list = new ArrayList<BaseAttribute>();

    public AttributeList()
    {

    }

    public AttributeList(AttributeList attrList)
    {
        Iterator<BaseAttribute> itr = attrList.getIterator();
        while (itr.hasNext())
        {
            list.add(itr.next());
        }
    }

    public Iterator<BaseAttribute> getIterator()
    {
        return list.iterator();
    }

    public int size()
    {
        return list.size();
    }

    public BaseAttribute get(int index) {
        if (index >= 0 && index < list.size())
            return list.get(index);
        else
            return null;
    }


    public void add(BaseAttribute attribute)
    {
        list.add(attribute);
    }

    public void remove (int index)
    {
        if (index < list.size() && index >= 0)
            list.remove(index);
    }

    public void set(int index, BaseAttribute attribute)
    {
        if (index < list.size() && index >= 0)
            list.set(index, attribute);
    }

    public void setAttributeValue (int index, String value)
    {
        BaseAttribute attr = list.get(index);
        attr.setAttributeValue(value);
        list.set(index, attr);
    }

    public void setAttributeValue (String name, String value)
    {
        Iterator<BaseAttribute> itr = list.iterator();
        BaseAttribute attr;
        while(itr.hasNext())
        {
            attr = itr.next();
            if (attr.getAttributeName().equals(name))
            {
                attr.setAttributeValue(value);
                return;
            }
        }
    }
}
