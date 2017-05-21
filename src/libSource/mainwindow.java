package libSource;
import  libSource.Attributes.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import java.util.List;

/**
 * Created by admin on 07/04/17.
 */
public class mainwindow extends JFrame{
    private JPanel panel1;
    private JButton changeUserButton;
    private JTextField SearchEdit;
    private JTextField NameEdit;
    private JTextField DescrEdit;
    private JScrollPane sp;
    private JTable table1;
    private JButton openCardButton;
    private JTextField LinkEdit;
    private JTextField ThemeEdit;
    private JTextField AccessTypeEdit;
    private JButton simpleSearchButton;
    private JButton deleteResourceButton;
    private JButton addResourceButton;
    private JButton getbutton;
    private JButton extendedSearchButton;
    private JScrollPane jp;
    private DataBaseWorker mgr;
    private ChangeUser chgUser;
    private CardForm cardForm;
    private boolean UserRole = false;
    private JPopupMenu tablePopupMenu;
    private DefaultTableModel model;


    public mainwindow() {
        model = new DefaultTableModel();
        chgUser = new ChangeUser(this);
        cardForm = new CardForm(this);
        changeUserButton.setText("Change user role (now User)");
        table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablePopupMenu = new JPopupMenu();
        setUpPopupMenu();

        try {
            mgr = new DataBaseWorker();
            cardForm.setAccessTypeDictionary(mgr.getAccessTypeDictionary());
            cardForm.setKindDictionary(mgr.getKindDictionary());
            cardForm.setTypeDictionary(mgr.getTypeDictionary());
            cardForm.setThemeDictionary(mgr.getThemeDictionary());
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        table1.setAutoCreateRowSorter(true);
        table1.setFillsViewportHeight(true);
        table1.setPreferredScrollableViewportSize(new Dimension(550, 200));

        //table1.setModel(model);
        openCardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardForm.setAdmin(UserRole);
                int ID = Integer.parseInt(table1.getValueAt(table1.getSelectedRow(), 0).toString());
                getCardForTable();
                cardForm.show(CardMode.EDITING);
            }
        });

        changeUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chgUser.show();
            }
        });

        getbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTable();
            }
        });
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                getCardForTable();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (SwingUtilities.isRightMouseButton(e))
                {
                    int r = table1.rowAtPoint(e.getPoint());
                    if (r >= 0 && r < table1.getRowCount())
                        table1.setRowSelectionInterval(r, r);
                    else
                        table1.clearSelection();

                    int rowindex = table1.getSelectedRow();
                    if (rowindex < 0)
                        return;
                }
                if (e.isPopupTrigger() && e.getComponent() instanceof JTable )
                    doPop(e);
                else
                    hidePop();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if (e.isPopupTrigger() && e.getComponent() instanceof JTable )
                    doPop(e);
                else
                    hidePop();
            }
        });
        extendedSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AttributeList lst = new AttributeList();

                AttributeName           atn = new AttributeName("");
                AttributeLink           atl = new AttributeLink("");
                AttributeTheme          ath = new AttributeTheme("");
                AttributeDescription    atd = new AttributeDescription("");
                AttributeAccessType     atat = new AttributeAccessType("");

                if (!NameEdit.getText().isEmpty()) {
                    atn.setAttributeValue(NameEdit.getText());
                    lst.add(atn);
                }
                if (!DescrEdit.getText().isEmpty()) {
                    atd.setAttributeValue(DescrEdit.getText());
                    lst.add(atd);
                }
                if (!LinkEdit.getText().isEmpty()) {
                    atl.setAttributeValue(LinkEdit.getText());
                    lst.add(atl);
                }
                if (!ThemeEdit.getText().isEmpty()) {
                    ath.setAttributeValue(ThemeEdit.getText());
                    lst.add(ath);
                }

                if (!AccessTypeEdit.getText().isEmpty()) {
                    atat.setAttributeValue(AccessTypeEdit.getText());
                    lst.add(atat);
                }

                AttributeList lstOut = new AttributeList();
                lstOut.add(new AttributeID(""));
                lstOut.add(new AttributeName(""));
                lstOut.add(new AttributeDescription(""));
                lstOut.add(new AttributeLink(""));
                lstOut.add(new AttributeTheme(""));
                lstOut.add(new AttributeAccessType(""));

                try {
                    LALtoModel(model, mgr.extendedSearch(lstOut, lst));
                    table1.setModel(model);
                    hideIDColumn();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

            }
        });
        simpleSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AttributeList lstOut = new AttributeList();
                lstOut.add(new AttributeID(""));
                lstOut.add(new AttributeName(""));
                lstOut.add(new AttributeDescription(""));
                lstOut.add(new AttributeLink(""));
                lstOut.add(new AttributeTheme(""));
                lstOut.add(new AttributeAccessType(""));

                try {
                    LALtoModel(model, mgr.simpleSearch(lstOut, SearchEdit.getText()));
                    table1.setModel(model);

                    hideIDColumn();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        deleteResourceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int r = table1.getSelectedRow();
                if (r < 0 || r > table1.getRowCount())
                    return;
                deleteSelectedRow(r);
            }
        });
        addResourceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardForm.show(CardMode.ADDDITION);
            }
        });
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("mainwindow");
        frame.setContentPane(new mainwindow().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public  void setTableColor ()
    {
        table1.setBackground(Color.red);
    }


    public void setUserRole(boolean role) {
        UserRole = role;
        if (role)
            changeUserButton.setText("Change user role (now Admin)");
        else
            changeUserButton.setText("Change user role (now User)");
        cardForm.setAdmin(role);
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    private void setUpPopupMenu()
    {
        JMenuItem deleteItem = new JMenuItem("Удалить запись");
        deleteItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int r = table1.getSelectedRow();
                if (r < 0 || r > table1.getColumnCount())
                    return;
                deleteSelectedRow(r);
            }
        });
        tablePopupMenu.add(deleteItem);
    }

    private void doPop(MouseEvent e) {
        if (table1.getSelectedRowCount() == 0) {
            return;
        }
        tablePopupMenu.setVisible(true);
        tablePopupMenu.show(e.getComponent(), e.getX(), e.getY());
    }

    private void hidePop() {
        tablePopupMenu.setVisible(false);
    }

    private void LALtoModel(DefaultTableModel tm, List<AttributeList> LAL) {
        tm.setColumnCount(0);
        tm.setRowCount(0);
        tm.addColumn("ID(you can't see me)");
        tm.addColumn("Name");
        tm.addColumn("Description");
        tm.addColumn("Source");
        tm.addColumn("Theme");
        tm.addColumn("Access Type");


        for(int i = 0; i < LAL.size(); i++)  {
            AttributeList temp2 = LAL.get(i);
            Vector<String> vct =  new Vector<>();
            for (int j = 0; j < temp2.size(); j++)  {
                BaseAttribute temp3 = temp2.get(j);
                vct.add(temp3.getAttributeValue());
            }
            tm.addRow(vct);
        }
    }

    private void hideIDColumn() {
        table1.getColumnModel().getColumn(0).setResizable(false);
        table1.getColumnModel().getColumn(0).setMaxWidth(0);
        table1.getColumnModel().getColumn(0).setMinWidth(0);
        table1.getColumnModel().getColumn(0).setPreferredWidth(0);
    }

    private void updateTable(){
        AttributeList temp = new AttributeList();
        temp.add(new AttributeID(""));
        temp.add(new AttributeName(""));
        temp.add(new AttributeDescription(""));
        temp.add(new AttributeLink(""));
        temp.add(new AttributeTheme(""));
        temp.add(new AttributeAccessType(""));

        try {
            LALtoModel(model, mgr.getSomeResources(temp));
            table1.setModel(model);

            hideIDColumn();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    private void getCardForTable() {
        if (table1.getSelectedRow() >= 0) {
            int ID = Integer.parseInt(table1.getValueAt(table1.getSelectedRow(), 0).toString());
            try {

                cardForm.setFieldsBySource(mgr.getCard(ID));
                cardForm.setArchive(mgr.getArchive(ID));
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    private void deleteSelectedRow(int row) {
        int ID = Integer.parseInt(table1.getValueAt(table1.getSelectedRow(), 0).toString());
        try {
            mgr.deleteSource(ID);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ((DefaultTableModel)table1.getModel()).removeRow(row);
    }

    public void additionConfirmed(){
        Source src = cardForm.getSource();

        // TODO: addToDataBase (src/attrList??)
        // updateTable();
    }

}

