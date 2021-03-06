package libSource;
import  libSource.Attributes.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Timer;

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

    private boolean deletedResources;


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
    private HelpForm helpForm;
    private JScrollPane jp;
    private JButton StatsButton;
    private JButton showDeletedResourcesButton;

    public DataBaseWorker getMgr() {
        return mgr;
    }

    public HelpForm getHelpForm() {
        return helpForm;
    }

    public void setMgr(DataBaseWorker mgr) {
        this.mgr = mgr;
    }

    private DataBaseWorker mgr;
    private ChangeUser chgUser;

    public CardForm getCardForm() {
        return cardForm;
    }

    private CardForm cardForm;
    private StatsForm statsForm;
    private boolean UserRole = false;
    private boolean wasloaded = false;
    private JPopupMenu tableDeletePopupMenu;
    private JPopupMenu tableRestorePopupMenu;
    private DefaultTableModel model;
    private JMenuBar menuBar;

    public mainwindow() {
        deletedResources = false;
        table1.setDefaultEditor(Object.class, null);
        setTitle("Система паспортизации электронных ресурсов удаленного доступа");
        menuBar = new JMenuBar();
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMyMenuBar();
        setPreferredSize(new Dimension(800, 700));
        pack();
        setVisible(true);


        statsForm = new StatsForm(this);
        model = new DefaultTableModel();
        chgUser = new ChangeUser(this);
        cardForm = new CardForm(this);
        helpForm = new HelpForm();
        changeUserButton.setText("Сменить режим доступа (опер.)");
        table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tableDeletePopupMenu = new JPopupMenu();
        tableRestorePopupMenu = new JPopupMenu();


        setUpPopupMenu();
        try {
            mgr = new DataBaseWorker();

            cardForm.setDictionariesInfo(mgr.getDictInfo());
            this.updateDictionaries();
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
                if (table1.getSelectedRowCount() == 0)
                    return;
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
                if (!wasloaded)
                    setArchivationTimer();
                wasloaded = true;
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

                    int rowIndex = table1.getSelectedRow();
                    if (rowIndex < 0)
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
                if (wasloaded) {
                    AttributeList lst = new AttributeList();

                    AttributeName atn = new AttributeName("");
                    AttributeLink atl = new AttributeLink("");
                    AttributeTheme ath = new AttributeTheme("");
                    AttributeDescription atd = new AttributeDescription("");
                    AttributeAccessType atat = new AttributeAccessType("");

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

                    if (lst.size() > 0) {
                        try {
                            LALtoModel(model, mgr.extendedSearch(lstOut, lst));
                            table1.setModel(model);
                            hideIDColumn();
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    } else {
                        //JDialog d = new JDialog()
                    }
                }
            }
        });
        simpleSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (wasloaded) {
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
                cardForm.setAdmin(UserRole);
                cardForm.show(CardMode.ADDDITION);
            }
        });
        StatsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                statsForm.show();

            }
        });
        showDeletedResourcesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTableDeleted();
            }
        });
    }

    public JTable getTable1() {
        return table1;
    }

    public void updateDictionaries() throws SQLException {
        cardForm.setAccessTypeDictionary(mgr.getDictionary("access_type"));
        cardForm.setInfoKindDictionary(mgr.getDictionary("content"));
        cardForm.setKindDictionary(mgr.getDictionary("kind"));
        cardForm.setTypeDictionary(mgr.getDictionary("type"));
        cardForm.setThemeDictionary(mgr.getDictionary("theme"));
        cardForm.setLanguageDictionary(mgr.getDictionary("language"));
        cardForm.setOperatorDictionary(mgr.getDictionary("operator"));
        cardForm.setSubModelDictionary(mgr.getDictionary("subscription_model"));
        cardForm.setPaymentMethodDictionary(mgr.getDictionary("pay_type"));
        cardForm.setAccessModeDictionary(mgr.getDictionary("access_mode"));
        cardForm.setTestModeDictionary(mgr.getDictionary("test_mode"));
        cardForm.setStatusDictionary(mgr.getDictionary("status"));
    }

    public static void main(String[] args) {
        new mainwindow();
    }

    public void setUserRole(boolean role) {
        UserRole = role;
        if (role)
            changeUserButton.setText("Сменить режим доступа (админ.)");
        else
            changeUserButton.setText("Сменить режим доступа (опер.)");
        cardForm.setAdmin(role);
    }

    private void setMyMenuBar(){
        setJMenuBar(menuBar);
        JMenu aboutMenu = new  JMenu("О программе");
        JMenuItem aboutMenuItem = new JMenuItem("О программе");
        aboutMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"Программа создана силами студентов МГТУ им. Баумана, группа ИУ7-63 в 2017 году", "О программе", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        JMenuItem helpMenuItem = new JMenuItem("Справка");
        helpMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //helpForm.setVisible(true);
            }
        });
        aboutMenu.add(helpMenuItem);
        aboutMenu.add(aboutMenuItem);
        menuBar.add(aboutMenu);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    private void setUpPopupMenu()
    {
        JMenuItem restoreItem = new JMenuItem("Восстановить запись");
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

        restoreItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int r = table1.getSelectedRow();
                if (r < 0 || r > table1.getColumnCount())
                    return;
                    restoreSelectedRow(r);
            }
        });

        tableDeletePopupMenu.add(deleteItem);
        tableRestorePopupMenu.add(restoreItem);
    }

    private void setArchivationTimer(){
        String filename = "./Docs/lastArch.txt";
        final File file = new File(filename);
        if (!file.exists()) {
            try {
                file.createNewFile();
                FileWriter writer = new FileWriter(file);
                String s = Objects.toString(System.currentTimeMillis());
                writer.write(s);
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String content = "";
        try {
            content = new Scanner(file).useDelimiter("\\Z").next(); // read String from file
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Long lastArchTime = Long.parseLong(content);
        if (lastArchTime <= 0)
        {
            try {
                FileWriter writer = new FileWriter(file);
                String s = Objects.toString(System.currentTimeMillis());
                writer.write(s);
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        long interval = 15811200000L; // half a year
        //long interval = 15811L; // 15s
        Date timeToRun = new Date(lastArchTime + interval);
        if (System.currentTimeMillis() > lastArchTime  + interval) {
            archivateAllPlanned();
            return;
        }
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                archivateAllPlanned();
            }
        }, timeToRun);
    }

    private void archivateAllPlanned(){
        try {
            //System.out.println("Planned archivation");
            //table1.setBackground(Color.red);
            for (int i = 0; i < table1.getRowCount(); i++) {
                int ID = Integer.parseInt(table1.getValueAt(i, 0).toString());
                mgr.addToArchiveSourceByID("Planned every-half year addition", ID);
            }
            String filename = "./Docs/lastArch.txt";
            final File file = new File(filename);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                FileWriter writer = new FileWriter(file);
                String s = Objects.toString(System.currentTimeMillis());
                writer.write(s);
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void doPop(MouseEvent e) {
        if (table1.getSelectedRowCount() == 0) {
            return;
        }
        if (deletedResources) {
            tableRestorePopupMenu.setVisible(true);
            tableRestorePopupMenu.show(e.getComponent(), e.getX(), e.getY());
        } else {
            tableDeletePopupMenu.setVisible(true);
            tableDeletePopupMenu.show(e.getComponent(), e.getX(), e.getY());
        }
    }

    private void hidePop() {
        if (deletedResources) {
            tableRestorePopupMenu.setVisible(false);
        } else {
            tableDeletePopupMenu.setVisible(false);
        }
    }

    //private
    public void LALtoModel(DefaultTableModel tm, List<AttributeList> LAL) {
        tm.setColumnCount(0);
        tm.setRowCount(0);
        tm.addColumn("ID(you can't see me)");
        tm.addColumn("Имя");
        tm.addColumn("Аннотация");
        tm.addColumn("Сетевой адрес");
        tm.addColumn("Тематика");
        tm.addColumn("Режим доступа");

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

    // private
    public void hideIDColumn() {
        table1.getColumnModel().getColumn(0).setResizable(false);
        table1.getColumnModel().getColumn(0).setMaxWidth(0);
        table1.getColumnModel().getColumn(0).setMinWidth(0);
        table1.getColumnModel().getColumn(0).setPreferredWidth(0);
    }

    public void updateTable(){
        deletedResources = false;
        AttributeList temp = new AttributeList();
        temp.add(new AttributeID(""));
        temp.add(new AttributeName(""));
        temp.add(new AttributeDescription(""));
        temp.add(new AttributeLink(""));
        temp.add(new AttributeTheme(""));
        temp.add(new AttributeAccessType(""));
        temp.add(new AttributeDeleted("0"));

        try {
            LALtoModel(model, mgr.getSomeResources(temp));

            table1.setModel(model);
            hideIDColumn();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public void updateTableDeleted(){
        deletedResources = true;
        AttributeList temp = new AttributeList();
        temp.add(new AttributeID(""));
        temp.add(new AttributeName(""));
        temp.add(new AttributeDescription(""));
        temp.add(new AttributeLink(""));
        temp.add(new AttributeTheme(""));
        temp.add(new AttributeAccessType(""));
        temp.add(new AttributeDeleted("1"));

        try {
            LALtoModel(model, mgr.getSomeResources(temp));

            table1.setModel(model);
            hideIDColumn();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public void getCardForTable() {
        if (table1.getSelectedRow() >= 0) {
            int ID = Integer.parseInt(table1.getValueAt(table1.getSelectedRow(), 0).toString());
            try {
                cardForm.setCurSrcID(ID);

                cardForm.setFieldsBySource(mgr.getCard(ID));
                cardForm.setFieldsBySourceIndexes(mgr.getCardIndexes(ID));

                cardForm.setArchive(mgr.getArchive(ID));
                cardForm.setStatistics(mgr.getStats(ID));
                cardForm.setStatTableColWidth();
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

    private void restoreSelectedRow(int row){
        int ID = Integer.parseInt(table1.getValueAt(table1.getSelectedRow(), 0).toString());
        try {
            mgr.restoreSource(ID);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ((DefaultTableModel)table1.getModel()).removeRow(row);
    }

    public void additionConfirmed(){
        Source src = cardForm.getSource();

        try {
            mgr.addSource(src);
        } catch (Exception e) {
            e.printStackTrace();
        }

        updateTable();
    }

    public void editionConfirmed(){
        Source src = cardForm.getSource();

        try {
            mgr.updateSource(cardForm.getCurSrcID(), src);
        } catch (Exception e) {
            e.printStackTrace();
        }

        updateTable();
    }

    public void editionCancelled(){
        try {
            int ID = cardForm.getCurSrcID();
            cardForm.setFieldsBySource(mgr.getCard(ID)); // загружаем неизменённую карточку
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public void addValueInDictionary(String table_name, String value) {
        try {
            mgr.addDictionaryValue(table_name, value);
            cardForm.setAccessTypeDictionary(mgr.getDictionary("access_type"));
            cardForm.setInfoKindDictionary(mgr.getDictionary("content"));
            cardForm.setKindDictionary(mgr.getDictionary("kind"));
            cardForm.setTypeDictionary(mgr.getDictionary("type"));
            cardForm.setThemeDictionary(mgr.getDictionary("theme"));
            cardForm.setLanguageDictionary(mgr.getDictionary("language"));
            cardForm.setOperatorDictionary(mgr.getDictionary("operator"));
            cardForm.setSubModelDictionary(mgr.getDictionary("subscription_model"));
            cardForm.setPaymentMethodDictionary(mgr.getDictionary("pay_type"));
            cardForm.setAccessModeDictionary(mgr.getDictionary("access_mode"));
            cardForm.setTestModeDictionary(mgr.getDictionary("test_mode"));
            cardForm.setStatusDictionary(mgr.getDictionary("status"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void editValueInDictionary(String table_name, String value, int id) {
        try {
            mgr.editDictionaryValue(table_name, value, id);
            updateDictionaries();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteValueFromDictionary(String table_name, int id) {
        try {
            mgr.delDictValue(table_name, id);
            updateDictionaries();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
