package libSource;
import  libSource.Attributes.*;

import java.util.Iterator;

public class Source extends  BaseSource
{
    public Source()
    {
        attributeList.add(new AttributeName(""));
        attributeList.add(new AttributeDescription(""));
        attributeList.add(new AttributeTheme(""));
        attributeList.add(new AttributeLink(""));
        attributeList.add(new AttributeAccessType(""));
        attributeList.add(new AttributeType(""));
        attributeList.add(new AttributeKind(""));


/*      пока так
        attributeList.add(new AttributeLanguage(""));


        attributeList.add(new AttributeAmount(""));
        attributeList.add(new AttributeArchivationDate(""));
        attributeList.add(new AttributeArchiveKey(""));
        attributeList.add(new AttributeChronologic(""));
        attributeList.add(new AttributeContent(""));
        attributeList.add(new AttributeContractDetails(""));
        attributeList.add(new AttributeContractDuration(""));
        attributeList.add(new AttributeID(""));

        attributeList.add(new AttributeOperator(""));
        attributeList.add(new AttributePayType(""));
        attributeList.add(new AttributePublicationArchive(""));
        attributeList.add(new AttributeSourceInfo(""));
        attributeList.add(new AttributeStatus(""));
        attributeList.add(new AttributeSubscriptionAccessType(""));
        attributeList.add(new AttributeSubscriptionPrice(""));
        attributeList.add(new AttributeSubscriptionSource(""));
        attributeList.add(new AttributeSubscriptionType(""));
        attributeList.add(new AttributeTestAccess(""));
        attributeList.add(new AttributeTestAccessConclusion(""));
*/
    }

    public int getLength() {
        return attributeList.size();
    }

    public AttributeList getList() { return attributeList; }

    public Source(AttributeList _attributeList)
    {
        Iterator<BaseAttribute> itr = _attributeList.getIterator();
        while (itr.hasNext())
            attributeList.add(itr.next());
    }

    @Override
    public String getAttributeValueByName(String attributeName)
    {
        for (int i = 0; i < attributeList.size(); i++) {
            if (attributeList.get(i).getAttributeName() == attributeName)
                return attributeList.get(i).getAttributeValue();
        }
        return "";
    }

    @Override
    public void setAttributeValueByName(String attributeName, String attributeValue)
    {
        for (int i = 0; i < attributeList.size(); i++) {
            if (attributeList.get(i).getAttributeName() == attributeName)
                attributeList.get(i).setAttributeValue(attributeValue);
        }
    }

    @Override
    public int getAttributeCount() {
        return attributeList.size();
    }

    @Override
    public BaseAttribute getAttribute (int index) {
        return attributeList.get(index);
    }

    @Override
    public String getName() {
        BaseAttribute attr = attributeSearcher.getAttribute(attributeList, "Name");
        if (attr != null)
            return attr.getAttributeValue();
        return "";
    }

    @Override
    public String getAccessType() {
        BaseAttribute attr = attributeSearcher.getAttribute(attributeList, "AccessType");
        if (attr != null)
            return attr.getAttributeValue();
        return "";
    }

    @Override
    public String getDescription() {
        BaseAttribute attr = attributeSearcher.getAttribute(attributeList, "Description");
        if (attr != null)
            return attr.getAttributeValue();
        return "";
    }

    @Override
    public String getLink() {
        BaseAttribute attr = attributeSearcher.getAttribute(attributeList, "Link");
        if (attr != null)
            return attr.getAttributeValue();
        return "";
    }

    @Override
    public String getTheme()
    {
        BaseAttribute attr = attributeSearcher.getAttribute(attributeList, "Theme");
        if (attr != null)
            return attr.getAttributeValue();
        return "";
    }

    @Override
    public String getType()
    {
        BaseAttribute attr = attributeSearcher.getAttribute(attributeList, "Type");
        if (attr != null)
            return attr.getAttributeValue();
        return "";
    }
    @Override
    public void setName(String name)
    {
        attributeSearcher.setAttributeValue(attributeList, "Name", name);
    }

    @Override
    public void setAccessType(String accessType)
    {
        attributeSearcher.setAttributeValue(attributeList, "AccessType", accessType);
    }

    @Override
    public void setDescription(String description)
    {
        attributeSearcher.setAttributeValue(attributeList, "Description", description);
    }

    @Override
    public void setLink(String link)
    {
        attributeSearcher.setAttributeValue(attributeList, "Link", link);
    }

    @Override
    public void setTheme(String theme)
    {
        attributeSearcher.setAttributeValue(attributeList, "Theme", theme);
    }

    @Override
    public void setType(String type)
    {
        attributeSearcher.setAttributeValue(attributeList, "Type", type);
    }
}
