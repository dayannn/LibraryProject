package libSource;

import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

/**
 * Created by JacKeTUs on 27.05.2017.
 */
public class Dictionary {
    protected String table_name;
    protected DefaultTableModel dictData;

    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }

    public DefaultTableModel getDictData() {
        return dictData;
    }

    public void setDictData(DefaultTableModel dictData) {
        this.dictData = dictData;
    }

    public Dictionary() {
        table_name = "";
        dictData = new DefaultTableModel();
    }

    public Dictionary(String table_name) {
        this.table_name = table_name;
        dictData = new DefaultTableModel();
        dictData.addColumn("id");
        dictData.addColumn("DBKey");
        dictData.addColumn("dict_value");
    }

    public void parseDictionary(ResultSet databaseData) throws SQLException {
        int curIdx = 0;
        while (databaseData.next()) {
            Vector<String> v = new Vector<>();
            v.add(String.valueOf(curIdx));
            v.add(databaseData.getString(1));
            v.add(databaseData.getString(2));
            curIdx++;
            dictData.addRow(v);
        }
    }

    public int getDBIdxByIdx(int idx) {
        return Integer.parseInt(dictData.getValueAt(idx,1).toString());
    }

    public int getIdxByDBIdx(int idx) {
        for (int i = 0; i < dictData.getRowCount(); i++) {
            if (Integer.parseInt(dictData.getValueAt(i, 1).toString()) == idx) {
                return i;
            }
        }
        return -1;
    }

    public String getDictValueByIdx(int idx) {
        return getValueByIdx(idx,2);
    }

    public String getValueByIdx(int idx, int value) {
        return dictData.getValueAt(idx,value).toString();
    }

    public int getSize() {
        return dictData.getRowCount();
    }

}
