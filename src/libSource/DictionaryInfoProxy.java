package libSource;

import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

/**
 * Created by JacKeTUs on 27.05.2017.
 */
public class DictionaryInfoProxy extends Dictionary {


    public DictionaryInfoProxy(String table_name) {
        this.table_name = table_name;
        dictData = new DefaultTableModel();
        dictData.addColumn("id");
        dictData.addColumn("DBKey");
        dictData.addColumn("table_name");
        dictData.addColumn("dict_description");
    }

    @Override
    public void parseDictionary(ResultSet databaseData) throws SQLException {
        int curIdx = 0;
        while (databaseData.next()) {
            Vector<String> v = new Vector<>();
            v.add(String.valueOf(curIdx));
            v.add(databaseData.getString(1));
            v.add(databaseData.getString(2));
            v.add(databaseData.getString(3));
            curIdx++;
            dictData.addRow(v);
        }
    }

    public String getTableNameByIdx(int idx) {
        return getValueByIdx(idx, 2);
    }

    public String getDescriptionByIdx(int idx) {
        return getValueByIdx(idx, 3);
    }
}
