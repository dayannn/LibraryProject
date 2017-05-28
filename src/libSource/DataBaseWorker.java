package libSource;


import com.sun.org.apache.regexp.internal.RE;
import libSource.Attributes.AttributeName;
import libSource.Attributes.BaseAttribute;
import libSource.Database.DBFacade;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

    public BaseSource getCard(int id) throws SQLException {
        BaseSource source = new Source();
        ResultSet rs = dbFacade.getCard(id);
        rs.next();
        for (int i = 0; i < source.getAttributeCount(); i++) {
            String attributeName = source.getAttribute(i).getAttributeName();
            source.setAttributeValueByName(attributeName, rs.getString(attributeName));
        }
        return source;
    }

    public BaseSource getCardIndexes(int ID) throws SQLException {
        BaseSource source = new Source();
        ResultSet rs = dbFacade.getCardIndexes(ID);
        rs.next();
        for (int i = 0; i < source.getAttributeCount(); i++) {
            BaseAttribute baseAttribute = source.getAttribute(i);
            String attributeName = baseAttribute.getAttributeName();

            baseAttribute.setAttributeValue(rs.getString(attributeName));
            if (!baseAttribute.getMidT().isEmpty()) {
                Scanner scanner = new Scanner(rs.getString(attributeName));
                scanner.useDelimiter(",");
                DefaultListModel<String> defaultListModel = new DefaultListModel<>();
                defaultListModel.clear();

                while(scanner.hasNext()) {
                    defaultListModel.addElement(scanner.next());
                }
                baseAttribute.setValues(defaultListModel);

            }
            source.setAttribute(baseAttribute);
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
            archiveR.addAttribute(new AttributeName(rs.getString("resource_provider")));
            archiveR.addAttribute(new AttributeName(rs.getString("subscription_model_value")));
            archiveR.addAttribute(new AttributeName(rs.getString("subscription_price")));
            archiveR.addAttribute(new AttributeName(rs.getString("resource_contract_duration")));
            archiveR.setDate(rs.getString("archive_date"));
            archive.addArchiveRecord(archiveR);
        }
        return archive;
    }

    public void addSource(Source source) throws SQLException {
        dbFacade.addSource(source);
    }

    public Dictionary getDictionary(String table_name) throws SQLException {
        Dictionary dict = new Dictionary(table_name);

        ResultSet rs = dbFacade.getDictionary(table_name);

        dict.parseDictionary(rs);

        return dict;
    }

    public void updateSource(int id, Source source) throws SQLException {
        BaseSource srctemp = this.getCardIndexes(id);
        String tempstring = "Изменилось:" ;
        if (!(source.getAttributeValueByName("resource_name").equals(srctemp.getAttributeValueByName("resource_name"))))
            //addToArchiveSourceByID("Изменилось имя c ( " + source.getAttributeValueByName("resource_name") + ") на ( " + srctemp.getAttributeValueByName("resource_name") + " ) ", id);
            tempstring = tempstring + " имя";

        if (!(source.getAttributeValueByName("resource_provider").equals(srctemp.getAttributeValueByName("resource_provider"))))
            if (tempstring != "Изменилось: ") tempstring = tempstring + ", поставщик";
                else tempstring = tempstring + " поставщик";
            //addToArchiveSourceByID("Изменился поставщик", id);

        if (!(source.getAttributeValueByName("subscription_model_value").equals(srctemp.getAttributeValueByName("subscription_model_value"))))
            if (tempstring != "Изменилось: ") tempstring = tempstring + ", модель подписки";
                else tempstring = tempstring + " модель подписки ";
            //addToArchiveSourceByID("Изменился тип подписки", id);

        if (!(source.getAttributeValueByName("subscription_price").equals(srctemp.getAttributeValueByName("subscription_price"))))
            if (tempstring != "Изменилось: ") tempstring = tempstring + ", цена подписки";
                else tempstring = tempstring + " цена подписки";
                //addToArchiveSourceByID("Изменилась цена", id);

        if (!(source.getAttributeValueByName("contract_duration").equals(srctemp.getAttributeValueByName("contract_duration"))))
            if (tempstring != "Изменилось: ") tempstring = tempstring + ", срок контракта";
                else tempstring = tempstring + " cрок контракта";

        tempstring = tempstring + ".";

           addToArchiveSourceByID(tempstring, id);
        dbFacade.chgSource(id, source);
    }

    public DictionaryInfoProxy getDictInfo() throws SQLException {
        DictionaryInfoProxy dictionaryInfoProxy = new DictionaryInfoProxy("dict_info");

        dictionaryInfoProxy.parseDictionary(dbFacade.getDictionariesInfo());

        return dictionaryInfoProxy;
    }

    public void addDictionaryValue(String table_name, String value) throws SQLException {
        dbFacade.addDictValue(table_name, value);
    }

    public void addToArchiveSourceByID(String description, int id) throws SQLException {
        BaseSource src = this.getCard(id);
        BaseAttribute attributeName = src.getAttributeByName("resource_name");
        BaseAttribute attributeProvider = src.getAttributeByName("resource_provider");
        BaseAttribute attributeSubType = src.getAttributeByName("subscription_model_value");
        BaseAttribute attributePrice = src.getAttributeByName("subscription_price");
        BaseAttribute attributeContract = src.getAttributeByName("resource_contract_duration");

        AttributeList lst = new AttributeList();
        lst.add(attributeName);
        lst.add(attributeProvider);
        lst.add(attributeSubType);
        lst.add(attributePrice);
        lst.add(attributeContract);

        dbFacade.addToArchiveSourceByID(description, id, lst);
    }
}