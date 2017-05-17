package libSource;
import  libSource.Attributes.*;

import javax.swing.*;
import javax.swing.event.AncestorListener;
import javax.swing.event.RowSorterEvent;
import javax.swing.event.RowSorterListener;
import javax.swing.plaf.synth.SynthEditorPaneUI;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import java.util.List;


/**
 * Created by admin on 07/04/17.
 */
public class mainwindow {
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
    private JButton простойПоискButton;
    private JButton удалитьРесурсButton;
    private JButton добавитьРесурсButton;
    private JButton getbutton;
    private JButton расширенныйПоискButton;
    private JScrollPane jp;
    private DataBaseWorker mgr;
    private ChangeUser chgUser;
    private CardForm cardForm;
    private boolean UserRole;


    public mainwindow() {
        DefaultTableModel model = new DefaultTableModel();
        chgUser = new ChangeUser(this);
        cardForm = new CardForm();
        changeUserButton.setText("Change user role (now User)");

        try {
            mgr = new DataBaseWorker();
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
                int ID = Integer.parseInt(table1.getValueAt(table1.getSelectedRow(), 0).toString());
                getCardForTable();

                cardForm.show();
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
        });
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                getCardForTable();
            }
        });
        расширенныйПоискButton.addActionListener(new ActionListener() {
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
        простойПоискButton.addActionListener(new ActionListener() {
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
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("mainwindow");
        frame.setContentPane(new mainwindow().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void setUserRole(boolean role) {
        UserRole = role;
        if (role)
            changeUserButton.setText("Change user role (now Admin)");
        else
            changeUserButton.setText("Change user role (now User)");
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
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

}
