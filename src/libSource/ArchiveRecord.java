package libSource;

import libSource.Attributes.BaseAttribute;

/**
 * Created by JacKeTUs on 18.05.2017.
 */
public class ArchiveRecord {
    private AttributeList attributeList;
    private String chg_dscr;

    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArchiveRecord() {
        attributeList = new AttributeList();
    }

    public ArchiveRecord(AttributeList attributeList) {
        this.attributeList = attributeList;
    }

    public AttributeList getAttributeList() {
        return attributeList;
    }

    public void setAttributeList(AttributeList attributeList) {
        this.attributeList = attributeList;
    }

    public String getChg_dscr() {
        return chg_dscr;
    }

    public void setChg_dscr(String chg_dscr) {
        this.chg_dscr = chg_dscr;
    }

    public void addAttribute(BaseAttribute baseAttribute) {
        attributeList.add(baseAttribute);
    }

}
