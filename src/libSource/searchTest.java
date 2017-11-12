package libSource;

import libSource.Attributes.*;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Created by dayan on 12.11.2017.
 */
public class searchTest {
    mainwindow mw = new mainwindow();
    AttributeList lstOut = new AttributeList();;
    @Before
    public void setUp() throws Exception {
        lstOut.add(new AttributeID(""));
        lstOut.add(new AttributeName(""));
        lstOut.add(new AttributeDescription(""));
        lstOut.add(new AttributeLink(""));
        lstOut.add(new AttributeTheme(""));
        lstOut.add(new AttributeAccessType(""));

    }

    @Test
    public void simpleSearchTest() throws Exception {
        mw.updateTable();
        JTable tbl = mw.getTable1();

        TableRowSorter<TableModel> rowSorter
                = new TableRowSorter<>(tbl.getModel());
        rowSorter.setRowFilter(RowFilter.regexFilter("Нау"));

        // новая модель для поиска
        DefaultTableModel mdl = (DefaultTableModel) tbl.getModel();
        mw.LALtoModel(mdl, mw.getMgr().simpleSearch(lstOut, "Нау"));

        TableModel old_mdl =  tbl.getModel();
        assertEquals(old_mdl.getRowCount(), mdl.getRowCount());
        assertEquals(old_mdl.getColumnCount(), mdl.getColumnCount());

        for (int i = 0; i < old_mdl.getRowCount(); i++)
            for (int j = 0; j < old_mdl.getColumnCount(); j++)
                assertEquals(old_mdl.getValueAt(i, j), mdl.getValueAt(i, j));

       // TimeUnit.SECONDS.sleep(5);
    }

    @Test
    public void extendedSearchTest() throws Exception {

    }

}