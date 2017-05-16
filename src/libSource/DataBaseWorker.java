package libSource;


import libSource.Attributes.BaseAttribute;
import libSource.Database.DBFacade;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataBaseWorker
{
    DBFacade dbFacade = new DBFacade();

    public DataBaseWorker() throws SQLException
    {
        dbFacade.connectDB();
    }

    public List<AttributeList> getSomeResources(AttributeList lst, BaseAttribute sortAttr) throws SQLException
    {
        List<AttributeList> sources = new ArrayList<AttributeList>();
        ResultSet rs = dbFacade.getSomeResources(lst, sortAttr);
        while (rs.next())
        {
            AttributeList currentList = new AttributeList();
            for(int i = 0; i < lst.size(); i++)
            {
                BaseAttribute currentAttribute = new BaseAttribute();
                currentAttribute.setAttributeName(lst.get(i).getAttributeName());
                currentAttribute.setAttributeValue(rs.getString(lst.get(i).getAttributeName()));
                currentList.add(currentAttribute);
            }
            sources.add(currentList);
        }
        return sources;
    }

    public List<AttributeList> simpleSearch(AttributeList lstOut, String searchQuery) throws SQLException
    {
        List<AttributeList> sources = new ArrayList<AttributeList>();

        ResultSet rs = dbFacade.simpleSearch(lstOut, searchQuery);
        while (rs.next())
        {
            AttributeList currentList = new AttributeList();
            for(int i = 0; i < lstOut.size(); i++)
            {
                BaseAttribute currentAttribute = new BaseAttribute();
                currentAttribute.setAttributeName(lstOut.get(i).getAttributeName());
                currentAttribute.setAttributeValue(rs.getString(lstOut.get(i).getAttributeName()));
                currentList.add(currentAttribute);                
            }
            sources.add(currentList);
        }

        return sources;
    }

    public List<AttributeList> extendedSearch(AttributeList lstOut, AttributeList lst) throws SQLException
    {
        List<AttributeList> sources = new ArrayList<AttributeList>();

        ResultSet rs = dbFacade.extendedSearch(lstOut, lst);
        while (rs.next())
        {
            AttributeList currentList = new AttributeList();
            for(int i = 0; i < lstOut.size(); i++)
            {
                BaseAttribute currentAttribute = new BaseAttribute();
                currentAttribute.setAttributeName(lstOut.get(i).getAttributeName());
                currentAttribute.setAttributeValue(rs.getString(lstOut.get(i).getAttributeName()));
                currentList.add(currentAttribute);
            }
            sources.add(currentList);
        }

        return sources;
    }

    public void deleteSource(int id) throws  SQLException
    {
        dbFacade.deleteSource(id);
    }

    public void editSource(int id, BaseSource src) throws  SQLException
    {
        dbFacade.editSource(id, src);
    }

    //  Returns ID
    public int addSource(BaseSource src) throws  SQLException
    {
        return dbFacade.addSource(src);
    }

    public BaseSource getCard(int id) throws SQLException
    {
        BaseSource source = new Source();

        ResultSet rs = dbFacade.getCard(id);
        rs.next();
        AttributeList currentList = new AttributeList();
        for(int i = 0; i < source.getAttributeCount(); i++)
        {
            String attributeName = source.getAttribute(i).getAttributeName();
            source.setAttributeValueByName(attributeName, rs.getString(attributeName));
        }

        return source;
    }
}
