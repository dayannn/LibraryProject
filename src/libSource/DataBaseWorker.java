package libSource;


import libSource.Attributes.AttributeName;
import libSource.Attributes.BaseAttribute;
import libSource.Database.DBFacade;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataBaseWorker
{
    private DBFacade dbFacade;

    public DataBaseWorker() throws SQLException
    {
        dbFacade = new DBFacade();
        dbFacade.connectDB();
    }

    public List<AttributeList> getSomeResources(AttributeList lst) throws SQLException
    {
        List<AttributeList> sources = new ArrayList<AttributeList>();
        ResultSet rs = dbFacade.getSomeResources(lst);
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

    public Source getSource(int id) throws SQLException
    {
        Source src = new Source();

        ResultSet rs = dbFacade.getCard(id);

        for(int i = 0; i < rs.getMetaData().getColumnCount(); i++)
            src.getAttribute(i).setAttributeName(rs.getString(src.getAttribute(i).getAttributeName()));

        return src;
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

    public void deleteSource(int id) throws SQLException {
        dbFacade.deleteSource(id);
    }

    public void editSource(int id, BaseSource src) throws  SQLException     {
        dbFacade.editSource(id, src);
    }

    public BaseSource getCard(int id) throws SQLException {
        BaseSource source = new Source();
        ResultSet rs = dbFacade.getCard(id);
        //rs.next();
        for (int i = 0; i < source.getAttributeCount(); i++) {
            String attributeName = source.getAttribute(i).getAttributeName();
            source.setAttributeValueByName(attributeName, rs.getString(attributeName));
        }
        return source;
    }

    public Archive getArchive(int id) throws SQLException {
        ResultSet rs = dbFacade.getArchiveForSourceID(id);
        Archive archive = new Archive();
        while(rs.next()) {
            ArchiveRecord archiveR = new ArchiveRecord();
            archiveR.setChg_dscr(rs.getString("resource_chg_description"));
            archiveR.addAttribute(new AttributeName(rs.getString("resource_name")));
            archiveR.setDate(rs.getString("archive_date"));
            archive.addArchiveRecord(archiveR);
        }
        return archive;
    }

    public void addSource(Source source) throws SQLException {
        dbFacade.addSource(source);
    }

    public DefaultListModel getAccessTypeDictionary() throws SQLException {
        DefaultListModel lst = new DefaultListModel();

        ResultSet rs = dbFacade.GetAccessTypeDictionary();
        while (rs.next()){
            lst.addElement(rs.getString(1));
        }

        return lst;
    }

    public DefaultListModel getKindDictionary() throws SQLException {
        DefaultListModel lst = new DefaultListModel();

        ResultSet rs = dbFacade.GetKindDictionary();
        while (rs.next()){
            lst.addElement(rs.getString(1));
        }

        return lst;
    }

    public DefaultListModel getTypeDictionary() throws SQLException {
        DefaultListModel lst = new DefaultListModel();

        ResultSet rs = dbFacade.GetTypeDictionary();
        while (rs.next()){
            lst.addElement(rs.getString(1));
        }

        return lst;
    }

    public DefaultListModel getThemeDictionary() throws SQLException {
        DefaultListModel lst = new DefaultListModel();

        ResultSet rs = dbFacade.GetThemeDictionary();
        while (rs.next()){
            lst.addElement(rs.getString(1));
        }

        return lst;
    }
}
