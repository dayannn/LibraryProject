package libSource;

import libSource.Attributes.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by dayan on 07.04.2017.
 */

// SOME CHANGES

public class CardForm{
    private JDialog frame;
    private JPanel CardPanel;
    private JButton editButton;
    private JButton saveButton;
    private JButton discardButton;
    private JTabbedPane tabbedPane1;
    private JTextArea annotationTextArea;
    private JTextArea resourсeNameTextArea;
    private JTextArea resourсeOperatorTextArea;
    private JTextArea addressTextArea;
    private JTextArea subjectsTextArea;
    private JTextArea resourсeTypeTextArea;
    private JTextArea infoKindTextArea;
    private JTextArea resourсeVolumeTextArea;
    private JTextArea languageTextArea;
    private JTextArea timeTextArea;
    private JTextArea archiveInfoTextArea;
    private JPanel generalInfoTab;
    private JPanel subscriptionInfoTab;
    private JPanel subscriptionArchiveTab;
    private JPanel statisticsTab;
    private JPanel additionalInfoTab;
    private JTextArea providerTextArea;
    private JTextArea subscriptionModelTextArea;
    private JTextArea paymentMethodTextArea;
    private JTextArea contractTimeTextArea;
    private JTextArea documentPropsTextArea;
    private JTextArea subscriptionCostTextArea;
    private JTextArea accessModeTextArea;
    private JComboBox<String> testAccessComboBox;
    private JTextArea testAccessConclusionTextArea;
    private JTextArea accessTypeTextArea;
    private JComboBox<String> accessTypeComboBox;
    private JScrollPane accessTypeScrollPane;
    private JList<String> ArchiveList;
    private JTextArea archiveNameTextArea;
    private JTextArea archiveOperatorTextArea;
    private JComboBox<String> resourceTypeComboBox;
    private JTextArea resourceKindTextArea;
    private JComboBox<String> resourceKindComboBox;
    private JComboBox<String> infoKindComboBox;
    private JTextArea resourceStatusTextArea;
    private JComboBox<String> resourceStatusComboBox;
    private JComboBox<String> subscriptionModelComboBox;
    private JComboBox<String> paymentMethodComboBox;
    private JComboBox<String> accessModeComboBox;
    private JScrollPane resourсeTypeScrollPane;
    private JScrollPane resourceKindScrollPane;
    private JScrollPane infoKindScrollPane;
    private JScrollPane resourceStatusScrollPane;
    private JScrollPane accessModeScrollPane;
    private JScrollPane paymentMethodScrollPane;
    private JScrollPane subscriptionModelScrollPane;
    private JTextArea testAccessTextArea;
    private JScrollPane testAccessScrollPane;
    private JTextArea testTimeTextArea;
    private JScrollPane testTimeScrollPane;
    private JList<String> languageList;
    private JScrollPane languageListScrollPanel;
    private JScrollPane languageTextAreaScrollPanel;
    private JScrollPane subjectsTextAreaScrollPane;
    private JList<String> subjectsList;
    private JScrollPane subjectsListScrollPane;
    private JList<String> resourceOperatorList;
    private JScrollPane subjectsListSP;
    private JScrollPane languageListSP;
    private JScrollPane resourceOperatorListSP;
    private JScrollPane resourсeOperatorTextAreaSP;
    private JTextArea viewNumTextArea;
    private JScrollPane viewNumTextAreaScrollPane;
    private JLabel resourceNameLabel;
    private JLabel resourceOperatorLabel;
    private JLabel addressLabel;
    private JLabel annotationLabel;
    private JLabel subjectsLabel;
    private JLabel languageLabel;
    private JPanel exportTab;
    private JButton exportButton;
    private JPanel ModifyTab;
    private JComboBox<String> dictionaryComboBox;
    private JButton ChangeButton;
    private JButton AddButton;
    private JButton DeleteButton;
    private JTable DictionaryTable;
    private JTextArea archiveSubscriptionModelTextArea;
    private JTextArea archiveSubscriptionDurationTextArea;
    private JTextArea archiveSubscriptionPriceTextArea;
    private JRadioButton txtRadioButton;
    private JRadioButton xmlRadioButton;
    private Archive archive;
    private int curSrcID;
    private ChangeDirectoryForm dictForm;

    private DefaultTableModel tm = new DefaultTableModel();

    private Dictionary currentDictionaryToEdit;
    private Dictionary accessTypeDictionary;
    private Dictionary contentDictionary;
    private Dictionary kindDictionary;
    private Dictionary typeDictionary;
    private Dictionary themeDictionary;
    private Dictionary languageDictionary;
    private Dictionary operatorDictionary;
    private Dictionary subModelDictionary;
    private Dictionary paymentMethodDictionary;
    private Dictionary accessModeDictionary;
    private Dictionary testModeDictionary;
    private Dictionary statusDictionary;
    private DictionaryInfoProxy dictionaryInfoProxy;


    public int getCurSrcID() {
        return curSrcID;
    }

    public void setCurSrcID(int curSrcID) {
        this.curSrcID = curSrcID;
    }

    private boolean IsAdmin;
    private CardMode mode;
    private mainwindow _parent; // that's not very good, I suppose?

    public boolean isAdmin() {
        return IsAdmin;
    }


    public void setAdmin(boolean admin) {
        IsAdmin = admin;
        //tabbedPane1.remove(ModifyTab);
        if (IsAdmin) {
            // admin job
            tabbedPane1.add(ModifyTab);
            ModifyTab.setName("Изменение");
        }
        else {
            tabbedPane1.remove(ModifyTab);
            // librarian job
        }

    }

    private java.util.List<JTextArea> textAreasList = new ArrayList<>();
    // говнокод? :/ не, заебись
    private void fillTextAreasList(){
        textAreasList.add(resourсeNameTextArea);
        textAreasList.add(annotationTextArea);
        textAreasList.add(addressTextArea);

        textAreasList.add(accessTypeTextArea);
        textAreasList.add(resourсeTypeTextArea);
        textAreasList.add(infoKindTextArea);

        textAreasList.add(resourceKindTextArea);
        textAreasList.add(subjectsTextArea);
        textAreasList.add(languageTextArea);
        textAreasList.add(resourсeOperatorTextArea);
        textAreasList.add(timeTextArea);
        textAreasList.add(paymentMethodTextArea);
        textAreasList.add(contractTimeTextArea);
        textAreasList.add(resourceStatusTextArea);
        textAreasList.add(resourсeVolumeTextArea);
        textAreasList.add(subscriptionCostTextArea);
        textAreasList.add(testAccessTextArea);
        textAreasList.add(accessModeTextArea);
        textAreasList.add(subscriptionModelTextArea);

        textAreasList.add(providerTextArea);
        textAreasList.add(documentPropsTextArea);


        textAreasList.add(testAccessConclusionTextArea);
        textAreasList.add(testTimeTextArea);
    }

    private void setFieldsUneditable(){
        for (JTextArea area : textAreasList
                ) {
            area.setEditable(false);
            area.setBackground(Color.lightGray);
        }
        testAccessComboBox.setVisible(false);
        testAccessScrollPane.setVisible(true);
        accessTypeScrollPane.setVisible(true);
        accessTypeComboBox.setVisible(false);
        resourceTypeComboBox.setVisible(false);
        resourсeTypeScrollPane.setVisible(true);
        infoKindComboBox.setVisible(false);
        infoKindScrollPane.setVisible(true);
        resourceKindComboBox.setVisible(false);
        resourceKindScrollPane.setVisible(true);
        resourceStatusComboBox.setVisible(false);
        resourceStatusScrollPane.setVisible(true);
        subscriptionModelComboBox.setVisible(false);
        subscriptionModelScrollPane.setVisible(true);
        paymentMethodComboBox.setVisible(false);
        paymentMethodScrollPane.setVisible(true);
        accessModeComboBox.setVisible(false);
        accessModeScrollPane.setVisible(true);
        languageList.setVisible(false);
        languageTextAreaScrollPanel.setVisible(true);
        subjectsList.setVisible(false);
        subjectsTextAreaScrollPane.setVisible(true);
        resourceOperatorList.setVisible(false);
        resourсeOperatorTextAreaSP.setVisible(true);

        subjectsListSP.setVisible(false);
        languageListSP.setVisible(false);
        resourceOperatorListSP.setVisible(false);

        resourceNameLabel.setText("Название ресурса");
        addressLabel.setText("Сетевой адрес");
        annotationLabel.setText("Аннотация");
        resourceOperatorLabel.setText("Оператор ресурса");
        subjectsLabel.setText("Тематика");
        languageLabel.setText("Язык документов ресурса");
    }

    private void setFieldsEditable(){
        for (JTextArea area : textAreasList
                ) {
            area.setEditable(true);
            area.setBackground(Color.white);
        }
        resourceOperatorList.setBackground(Color.white);
        subjectsList.setBackground(Color.white);
        languageList.setBackground(Color.white);
        testAccessScrollPane.setVisible(false);
        testAccessComboBox.setVisible(true);
        accessTypeScrollPane.setVisible(false);
        accessTypeComboBox.setVisible(true);
        resourceTypeComboBox.setVisible(true);
        resourсeTypeScrollPane.setVisible(false);
        infoKindComboBox.setVisible(true);
        infoKindScrollPane.setVisible(false);
        resourceKindComboBox.setVisible(true);
        resourceKindScrollPane.setVisible(false);
        resourceStatusComboBox.setVisible(true);
        resourceStatusScrollPane.setVisible(false);
        subscriptionModelComboBox.setVisible(true);
        subscriptionModelScrollPane.setVisible(false);
        paymentMethodComboBox.setVisible(true);
        paymentMethodScrollPane.setVisible(false);
        accessModeComboBox.setVisible(true);
        accessModeScrollPane.setVisible(false);
        languageList.setVisible(true);
        languageTextAreaScrollPanel.setVisible(false);
        subjectsList.setVisible(true);
        subjectsTextAreaScrollPane.setVisible(false);
        resourceOperatorList.setVisible(true);
        resourсeOperatorTextAreaSP.setVisible(false);

        subjectsListSP.setVisible(true);
        languageListSP.setVisible(true);
        resourceOperatorListSP.setVisible(true);

        resourceNameLabel.setText("Название ресурса *");
        addressLabel.setText("Сетевой адрес *");
        annotationLabel.setText("Аннотация *");
        resourceOperatorLabel.setText("Оператор ресурса *");
        subjectsLabel.setText("Тематика *");
        languageLabel.setText("Язык документов ресурса *");
    }

    private void clearFields(){
        for (JTextArea area : textAreasList){
            area.setText("");
        }
    }

    private boolean fieldsAreCorrect(){
        resetFieldsColor();
        boolean res = true;
        if (resourсeNameTextArea.getText().trim().isEmpty())
        {
            res = false;
            resourсeNameTextArea.setBackground(Color.pink);
        }
        if (addressTextArea.getText().trim().isEmpty())
        {
            res = false;
            addressTextArea.setBackground(Color.pink);
        }
        if (annotationTextArea.getText().trim().isEmpty())
        {
            res = false;
            annotationTextArea.setBackground(Color.pink);
        }
        if (resourceOperatorList.isSelectionEmpty())
        {
            res = false;
            resourceOperatorList.setBackground(Color.pink);
        }
        if (subjectsList.isSelectionEmpty())
        {
            res = false;
            subjectsList.setBackground(Color.pink);
        }
        if (languageList.isSelectionEmpty())
        {
            res = false;
            languageList.setBackground(Color.pink);
        }

        return res;
    }

    private void resetFieldsColor() {
        resourсeNameTextArea.setBackground(Color.white);
        addressTextArea.setBackground(Color.white);
        annotationTextArea.setBackground(Color.white);
        resourceOperatorList.setBackground(Color.white);
        subjectsList.setBackground(Color.white);
        languageList.setBackground(Color.white);
    }


    public CardForm(mainwindow parent) {
        dictForm = new ChangeDirectoryForm(this);
        curSrcID = 0;
        frame = new JDialog(_parent, "Паспорт ресурса", true);
        _parent = parent;
        saveButton.setVisible(false);
        discardButton.setVisible(false);
        frame.setSize(650, 650);
        // frame.setResizable(false);
        frame.setContentPane(CardPanel);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        ButtonGroup exportGroup = new ButtonGroup();
        exportGroup.add(txtRadioButton);
        exportGroup.add(xmlRadioButton);
        txtRadioButton.setSelected(true);
        setScrollPanes();

        languageList.setCellRenderer(new CheckboxListCellRenderer());
        subjectsList.setCellRenderer(new CheckboxListCellRenderer());
        resourceOperatorList.setCellRenderer(new CheckboxListCellRenderer());

        languageList.setSelectionModel(new DefaultListSelectionModel()  {
            @Override
            public void setSelectionInterval(int index0, int index1) {
                if(super.isSelectedIndex(index0)) {
                    super.removeSelectionInterval(index0, index1);
                } else {
                    super.addSelectionInterval(index0, index1);
                }
            }
        });
        subjectsList.setSelectionModel(new DefaultListSelectionModel()  {
            @Override
            public void setSelectionInterval(int index0, int index1) {
                if(super.isSelectedIndex(index0)) {
                    super.removeSelectionInterval(index0, index1);
                } else {
                    super.addSelectionInterval(index0, index1);
                }
            }
        });
        resourceOperatorList.setSelectionModel(new DefaultListSelectionModel()  {
            @Override
            public void setSelectionInterval(int index0, int index1) {
                if(super.isSelectedIndex(index0)) {
                    super.removeSelectionInterval(index0, index1);
                } else {
                    super.addSelectionInterval(index0, index1);
                }
            }
        });

        fillTextAreasList();
        setFieldsUneditable();

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setFieldsEditable();
                saveButton.setVisible(true);
                discardButton.setVisible(true);
                editButton.setVisible(false);
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!fieldsAreCorrect()) {
                    JOptionPane.showMessageDialog(frame, "Заполните все значимые поля, отмеченные символом (*), перед сохранением");
                    return;
                }

                setFieldsUneditable();
                editButton.setVisible(true);
                discardButton.setVisible(false);
                saveButton.setVisible(false);
                if (mode == CardMode.ADDDITION) {
                    _parent.additionConfirmed();
                } else {
                    _parent.editionConfirmed();
                }


                frame.setVisible(false);
            }
        });

        discardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setFieldsUneditable();
                editButton.setVisible(true);
                saveButton.setVisible(false);
                discardButton.setVisible(false);
                if (mode == CardMode.ADDDITION)
                    frame.setVisible(false);
                else
                    parent.editionCancelled();
            }
        });

        ArchiveList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                // отобразить i-тый архив
                int ID = ArchiveList.getSelectedIndex();
                archiveNameTextArea.setText(archive.getArchiveRecords().get(ID).getAttributeList().get(0).getAttributeValue());
                archiveOperatorTextArea.setText(archive.getArchiveRecords().get(ID).getAttributeList().get(1).getAttributeValue());
                archiveSubscriptionModelTextArea.setText(archive.getArchiveRecords().get(ID).getAttributeList().get(2).getAttributeValue());
                archiveSubscriptionPriceTextArea.setText(archive.getArchiveRecords().get(ID).getAttributeList().get(3).getAttributeValue());
                archiveSubscriptionDurationTextArea.setText(archive.getArchiveRecords().get(ID).getAttributeList().get(4).getAttributeValue());
                archiveInfoTextArea.setText(archive.getArchiveRecords().get(ID).getChg_dscr());
            }
        });

        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (saveButton.isVisible())
                {
                    JOptionPane.showMessageDialog(frame, "Пожалуйста, сохраните изменения перед экспортом");
                    return;
                }
                if (txtRadioButton.isSelected())
                    exportToTextFile();
                else
                    exportToXmlFile();
                //exportToTextFile();
            }
        });

        ChangeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SetChangeDictionaryForm();
                dictForm.show();
            }
        });

        AddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dictForm.clearFields();
                dictForm.show();
            }
        });


        dictionaryComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (dictionaryComboBox.getSelectedIndex() >= 0) {

                    tm.setColumnCount(0);
                    String table_name = dictionaryInfoProxy.getTableNameByIdx(dictionaryComboBox.getSelectedIndex());
                    tm.addColumn("Индекс словаря");
                    tm.addColumn("Значения словаря");

                    try {
                        updateTable(table_name);

                        DictionaryTable.setModel(tm);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        DeleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _parent.deleteValueFromDictionary(currentDictionaryToEdit.getTable_name(), Integer.parseInt(DictionaryTable.getValueAt(DictionaryTable.getSelectedRow(), 0).toString()));

                try {
                    updateTable(currentDictionaryToEdit.getTable_name());
                    DictionaryTable.setModel(tm);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    private void exportToTextFile() {
        JFileChooser fileChooser = new JFileChooser();

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String content = "";

            content += "Имя ресурса : " + resourсeNameTextArea.getText();
            content += "\nОператор ресурса : " + resourсeOperatorTextArea.getText();
            content += "\nСетевой адрес : " + addressTextArea.getText();
            content += "\nТематика : " + subjectsTextArea.getText();
            content += "\nТип ресурса : " + resourсeTypeTextArea.getText();
            content += "\nВид ресурса : " + resourceKindTextArea.getText();
            content += "\nПрирода информации контента : " + infoKindTextArea.getText();
            content += "\nОбъем ресурса : " + resourсeVolumeTextArea.getText();
            content += "\nЯзык документов ресурса : " + languageTextArea.getText();
            content += "\nХронологический охват : " + timeTextArea.getText();
            content += "\nВид доступа : " + accessTypeTextArea.getText();
            content += "\nАннотация : " + annotationTextArea.getText();
            content += "\nСтатус ресурса : " + resourceStatusTextArea.getText();
            content += "\nМодель подписки : " + subscriptionModelTextArea.getText();
            content += "\nПоставщик : " + providerTextArea.getText();
            content += "\nСпособ оплаты : " + paymentMethodTextArea.getText();
            content += "\nСрок действия договора : " + contractTimeTextArea.getText();
            content += "\nРеквизиты договора : " + documentPropsTextArea.getText();
            content += "\nСтоимость подписки : " + subscriptionCostTextArea.getText();
            content += "\nРежим доступа : " + accessModeTextArea.getText();
            content += "\nКоличество просмотров в день : " + viewNumTextArea.getText();
            content += "\nТестовый доступ : " + testAccessTextArea.getText();
            content += "\nСроки проведения : " + testTimeTextArea.getText();
            content += "\nЗаключение по тестовому доступу : " + testAccessConclusionTextArea.getText();

            FileWriter fw = null;
            try {
                String name = fileToSave.getAbsolutePath();
                if (!name.endsWith(".txt"))
                    name += ".txt";
                fw = new FileWriter(name);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(content);
                bw.close();
                fw.close();
            } catch (IOException e1) {
                JOptionPane.showMessageDialog(frame,"Error while writing to file");
            }
            //System.out.println("Save as file: " + fileToSave.getAbsolutePath());
        }
    }

    private void exportToXmlFile() {
        JFileChooser fileChooser = new JFileChooser();

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            XMLStreamWriter xsw = null;

            try {
                String name = fileToSave.getAbsolutePath();
                if (!name.endsWith(".xml"))
                    name += ".xml";
                FileOutputStream fop = new FileOutputStream(name);

                xsw = XMLOutputFactory.newInstance()
                        .createXMLStreamWriter(fop);
                xsw.writeStartDocument();
                xsw.writeCharacters("\n");
                xsw.writeStartElement("Карточка");
                xsw.writeCharacters("\n");
                writeSingleXmlNode(xsw, "Имя_ресурса", resourсeNameTextArea.getText());
                writeSingleXmlNode(xsw, "Сетевой_адрес", addressTextArea.getText());
                writeSingleXmlNode(xsw, "Тематика", subjectsTextArea.getText());
                writeSingleXmlNode(xsw, "Тип_ресурса", resourсeTypeTextArea.getText());
                writeSingleXmlNode(xsw, "Вид_ресурса", resourceKindTextArea.getText());
                writeSingleXmlNode(xsw, "Природа_информации_ресурса", infoKindTextArea.getText());
                writeSingleXmlNode(xsw, "Объем_ресурса", resourсeVolumeTextArea.getText());
                writeSingleXmlNode(xsw, "Язык_документов_ресурса", languageTextArea.getText());
                writeSingleXmlNode(xsw, "Хронологический_охват", timeTextArea.getText());
                writeSingleXmlNode(xsw, "Вид_доступа", accessTypeTextArea.getText());
                writeSingleXmlNode(xsw, "Аннотация", annotationTextArea.getText());
                writeSingleXmlNode(xsw, "Статус_ресурса", resourceStatusTextArea.getText());
                writeSingleXmlNode(xsw, "Модель_подписки", subscriptionModelTextArea.getText());
                writeSingleXmlNode(xsw, "Поставщик", providerTextArea.getText());
                writeSingleXmlNode(xsw, "Способ_оплаты", paymentMethodTextArea.getText());
                writeSingleXmlNode(xsw, "Срок_действия_договора", contractTimeTextArea.getText());
                writeSingleXmlNode(xsw, "Реквизиты_договора", documentPropsTextArea.getText());
                writeSingleXmlNode(xsw, "Стоимость_подписки", subscriptionCostTextArea.getText());
                writeSingleXmlNode(xsw, "Режим_доступа", accessModeTextArea.getText());
                writeSingleXmlNode(xsw, "Количество_просмотров_в_день", viewNumTextArea.getText());
                writeSingleXmlNode(xsw, "Тестовый_доступ", testAccessTextArea.getText());
                writeSingleXmlNode(xsw, "Сроки_проведения", testTimeTextArea.getText());
                writeSingleXmlNode(xsw, "Заключение_по_тестовому_доступу", testAccessConclusionTextArea.getText());
                xsw.writeEndElement();
                xsw.writeEndDocument();
                fop.close();
                xsw.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (XMLStreamException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void writeSingleXmlNode(XMLStreamWriter xsw, String tag, String text){
        try {
            xsw.writeCharacters("\t");
            xsw.writeStartElement(tag);
            xsw.writeCharacters(text);
            xsw.writeEndElement();
            xsw.writeCharacters("\n");
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }


    private void updateTable(String table_name) throws SQLException {
        currentDictionaryToEdit = _parent.getMgr().getDictionary(table_name);
        tm.setRowCount(0);
        for (int i = 0; i < currentDictionaryToEdit.getSize(); i++) {

            Vector<String> data = new Vector<>();
            data.addElement(String.valueOf(currentDictionaryToEdit.getDBIdxByIdx(i)));
            data.addElement(currentDictionaryToEdit.getDictValueByIdx(i));
            tm.addRow(data);
        }
    }

    public void setDictionariesInfo(DictionaryInfoProxy dictionariesInfo) {
        dictionaryInfoProxy = dictionariesInfo;
        for (int i = 0; i < dictionariesInfo.getSize(); i++) {
            dictionaryComboBox.addItem(dictionariesInfo.getDescriptionByIdx(i));
        }
    }

    // Заполнение всех текстовых полей и комбобококосов
    public void setFieldsBySource(BaseSource src) {
        for (int i = 0; i < src.getLength(); i++) {
            textAreasList.get(i).setText(src.getAttribute(i).getAttributeValue());
        }
    }

    public void setFieldsBySourceIndexes(BaseSource src) {
        // TODO: ВОЗМОЖНО инициализация текстовых полей

        // TODO: Инициализация полей комбобоксов


        resourceKindComboBox.setSelectedIndex(kindDictionary.getIdxByDBIdx(Integer.parseInt(src.getAttributeByName("kind_value").getAttributeValue())));
        resourceTypeComboBox.setSelectedIndex(typeDictionary.getIdxByDBIdx(Integer.parseInt(src.getAttributeByName("type_value").getAttributeValue())));
        infoKindComboBox.setSelectedIndex(contentDictionary.getIdxByDBIdx(Integer.parseInt(src.getAttributeByName("content_value").getAttributeValue())));
        accessTypeComboBox.setSelectedIndex(accessTypeDictionary.getIdxByDBIdx(Integer.parseInt(src.getAttributeByName("access_type_value").getAttributeValue())));
        resourceStatusComboBox.setSelectedIndex(statusDictionary.getIdxByDBIdx(Integer.parseInt(src.getAttributeByName("status_value").getAttributeValue())));
        subscriptionModelComboBox.setSelectedIndex(subModelDictionary.getIdxByDBIdx(Integer.parseInt(src.getAttributeByName("subscription_model_value").getAttributeValue())));
        paymentMethodComboBox.setSelectedIndex(paymentMethodDictionary.getIdxByDBIdx(Integer.parseInt(src.getAttributeByName("pay_type_value").getAttributeValue())));
        accessModeComboBox.setSelectedIndex(accessModeDictionary.getIdxByDBIdx(Integer.parseInt(src.getAttributeByName("access_mode_value").getAttributeValue())));
        testAccessComboBox.setSelectedIndex(testModeDictionary.getIdxByDBIdx(Integer.parseInt(src.getAttributeByName("resource_test_mode").getAttributeValue())));

        // Получаем расширенные значения
        languageList.setSelectedIndices(parseToIntSelectedIndexes(languageDictionary, src.getAttributeByName("language_value").getValues()));
        resourceOperatorList.setSelectedIndices(parseToIntSelectedIndexes(operatorDictionary, src.getAttributeByName("operator_value").getValues()));
        subjectsList.setSelectedIndices(parseToIntSelectedIndexes(themeDictionary, src.getAttributeByName("theme_value").getValues()));
    }

    public void setArchive(Archive arch) {
        archive = arch;
        DefaultListModel<String> listModel = new DefaultListModel<String>();
        archiveNameTextArea.setText("");
        archiveOperatorTextArea.setText("");
        archiveInfoTextArea.setText("");
        archiveSubscriptionDurationTextArea.setText("");
        archiveSubscriptionModelTextArea.setText("");
        archiveSubscriptionPriceTextArea.setText("");

        //TODO: Возможно, переделать список дат под табличку
        for (int i = 0; i < arch.getArchiveRecords().size(); i++) {
            listModel.addElement(arch.getArchiveRecords().get(i).getDate());
        }

        ArchiveList.setModel(listModel);
    }

    public void show(CardMode cardMode) {
        setMode(cardMode);
        frame.setVisible(false);  // This strange thing is necessary to change modality correctly.
                                  // (it's correctly changed when you hide and show the frame again)
                                  // Without it if you open a card and then click 'Добавить ресурс'
                                  // the CardForm won't be modal

        frame.setVisible(true);
    }

    public void setMode(CardMode cardMode)
    {
        mode = cardMode;
        if (mode == CardMode.ADDDITION)
        {
            saveButton.setVisible(true);
            discardButton.setVisible(true);
            editButton.setVisible(false);
            setFieldsEditable();
            clearFields();
            frame.setModal(true);
        }
        else
        {
            saveButton.setVisible(false);
            discardButton.setVisible(false);
            editButton.setVisible(true);
            setFieldsUneditable();
            frame.setModal(false);
        }
    }

    private void setScrollPanes()
    {
        JScrollPane scrollp1 = new JScrollPane();
        JScrollPane scrollp2 = new JScrollPane();
        JScrollPane scrollp3 = new JScrollPane();
        JScrollPane scrollp4 = new JScrollPane();
        JScrollPane scrollp5 = new JScrollPane();
        JScrollPane scrollp6 = new JScrollPane();
        tabbedPane1.add(scrollp1, "Общие сведения");
        tabbedPane1.add(scrollp2, "Подписка");
        tabbedPane1.add(scrollp3, "Архив подписки");
        tabbedPane1.add(scrollp4, "Статистика использования");
        tabbedPane1.add(scrollp5, "Дополнительные сведения");
        tabbedPane1.add(scrollp6, "Экспорт");
        scrollp1.setViewportView(generalInfoTab);
        scrollp2.setViewportView(subscriptionInfoTab);
        scrollp3.setViewportView(subscriptionArchiveTab);
        scrollp4.setViewportView(statisticsTab);
        scrollp5.setViewportView(additionalInfoTab);
        scrollp6.setViewportView(exportTab);

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public Source getSource(){
        Source res = new Source();

        // Получаем текстовые значения
        for(int i = 0; i < res.getList().size(); i++) {
            String value = "1";
            if (!textAreasList.get(i).getText().isEmpty()) {
                res.getAttribute(i).setAttributeValue(textAreasList.get(i).getText());
            }
        }

        // Получаем расширенные значения
        AttributeLanguage al = new AttributeLanguage("");
        al.setValues(parseSelectedIndexes(languageDictionary, languageList));

        AttributeOperator ao = new AttributeOperator("");
        ao.setValues(parseSelectedIndexes(operatorDictionary, resourceOperatorList));

        AttributeTheme at = new AttributeTheme("");
        at.setValues(parseSelectedIndexes(themeDictionary, subjectsList));

        res.setAttribute(al);
        res.setAttribute(ao);
        res.setAttribute(at);

        // Получаем значения комбобоксов
        AttributeType attributeType = new AttributeType("");
        attributeType.setAttributeValue(String.valueOf(typeDictionary.getDBIdxByIdx(resourceTypeComboBox.getSelectedIndex())));
        res.setAttribute(attributeType);

        AttributeKind attributeKind = new AttributeKind("");
        attributeKind.setAttributeValue(String.valueOf(kindDictionary.getDBIdxByIdx(resourceKindComboBox.getSelectedIndex())));
        res.setAttribute(attributeKind);

        AttributeContent attributeContent = new AttributeContent("");
        attributeContent.setAttributeValue(String.valueOf(contentDictionary.getDBIdxByIdx(infoKindComboBox.getSelectedIndex())));
        res.setAttribute(attributeContent);

        AttributeAccessType attributeAccessType = new AttributeAccessType("");
        attributeAccessType.setAttributeValue(String.valueOf(accessTypeDictionary.getDBIdxByIdx(accessTypeComboBox.getSelectedIndex())));
        res.setAttribute(attributeAccessType);

        AttributeStatus attributeStatus = new AttributeStatus("");
        attributeStatus.setAttributeValue(String.valueOf(statusDictionary.getDBIdxByIdx(resourceStatusComboBox.getSelectedIndex())));
        res.setAttribute(attributeStatus);

        AttributePayType attributePayType = new AttributePayType("");
        attributePayType.setAttributeValue(String.valueOf(paymentMethodDictionary.getDBIdxByIdx(paymentMethodComboBox.getSelectedIndex())));
        res.setAttribute(attributePayType);

        AttributeSubscriptionType attributeSubscriptionType = new AttributeSubscriptionType("");
        attributeSubscriptionType.setAttributeValue(String.valueOf(subModelDictionary.getDBIdxByIdx(subscriptionModelComboBox.getSelectedIndex())));
        res.setAttribute(attributeSubscriptionType);

        AttributeAccessMode attributeAccessMode = new AttributeAccessMode("");
        attributeAccessMode.setAttributeValue(String.valueOf(accessModeDictionary.getDBIdxByIdx(accessModeComboBox.getSelectedIndex())));
        res.setAttribute(attributeAccessMode);

        AttributeTestAccess attributeTestAccess = new AttributeTestAccess("");
        attributeTestAccess.setAttributeValue(String.valueOf(testModeDictionary.getDBIdxByIdx(testAccessComboBox.getSelectedIndex())));
        res.setAttribute(attributeTestAccess);

        return res;
    }

    public DefaultListModel<String> parseSelectedIndexes(Dictionary dict, JList list) {

        // ID | Key | Value
        // 0  | 54  | Porn
        DefaultListModel<String> result = new DefaultListModel<>();
        result.clear();

        int [] indexes = list.getSelectedIndices();  // 0 1 2 3....
        for (int i = 0; i < indexes.length; i++) {
            result.addElement(String.valueOf(dict.getDBIdxByIdx(indexes[i])));
        }
        return result;
    }

    public int [] parseToIntSelectedIndexes(Dictionary dict, DefaultListModel<String> defaultListModel) {

        int [] indexes = new int [defaultListModel.size()];
        for (int i = 0; i < defaultListModel.size(); i++) {
            indexes[i] = dict.getIdxByDBIdx(Integer.parseInt(defaultListModel.elementAt(i)));
        }
        return indexes;
    }

    // Расширенные словари
    public void setThemeDictionary(Dictionary dictionary) {
        themeDictionary = dictionary;

        DefaultListModel<String> listModel = new DefaultListModel<>();
        for(int i = 0; i < dictionary.getSize(); i++)
            listModel.addElement(dictionary.getDictValueByIdx(i));

        subjectsList.setModel(listModel);
    }

    public void setLanguageDictionary(Dictionary dictionary) {
        languageDictionary = dictionary;

        DefaultListModel<String> listModel = new DefaultListModel<>();
        for(int i = 0; i < dictionary.getSize(); i++)
            listModel.addElement(dictionary.getDictValueByIdx(i));
        languageList.setModel(listModel);
    }

    public void setOperatorDictionary(Dictionary dictionary) {
        operatorDictionary = dictionary;

        DefaultListModel<String> listModel = new DefaultListModel<>();
        for(int i = 0; i < dictionary.getSize(); i++)
            listModel.addElement(dictionary.getDictValueByIdx(i));
        resourceOperatorList.setModel(listModel);
    }

    // обычные словари комбобоксов
    public void setTypeDictionary(Dictionary dictionary) {
        typeDictionary = dictionary;

        for(int i = 0; i< dictionary.getSize(); i++)
            resourceTypeComboBox.addItem(dictionary.getDictValueByIdx(i));
    }

    public void setKindDictionary(Dictionary dictionary) {
        kindDictionary = dictionary;

        for(int i = 0; i < dictionary.getSize(); i++)
            resourceKindComboBox.addItem(dictionary.getDictValueByIdx(i));
    }

    public void setInfoKindDictionary(Dictionary dictionary) {
        contentDictionary = dictionary;

        for(int i = 0; i < dictionary.getSize(); i++)
            infoKindComboBox.addItem(dictionary.getDictValueByIdx(i));
    }

    public void setAccessTypeDictionary(Dictionary dictionary) {
        accessTypeDictionary = dictionary;

        for(int i = 0; i< dictionary.getSize();i++) {
            accessTypeComboBox.addItem(dictionary.getDictValueByIdx(i));
        }
    }
    public void setSubModelDictionary(Dictionary dictionary) {
        subModelDictionary = dictionary;

        for(int i = 0; i< dictionary.getSize();i++) {
            subscriptionModelComboBox.addItem(dictionary.getDictValueByIdx(i));
        }
    }
    public void setPaymentMethodDictionary(Dictionary dictionary) {
        paymentMethodDictionary = dictionary;

        for(int i = 0; i< dictionary.getSize();i++) {
            paymentMethodComboBox.addItem(dictionary.getDictValueByIdx(i));
        }
    }
    public void setAccessModeDictionary(Dictionary dictionary) {
        accessModeDictionary = dictionary;

        for(int i = 0; i< dictionary.getSize();i++) {
            accessModeComboBox.addItem(dictionary.getDictValueByIdx(i));
        }
    }

    public void setTestModeDictionary(Dictionary dictionary) {
        testModeDictionary = dictionary;

        for(int i = 0; i< dictionary.getSize();i++) {
            testAccessComboBox.addItem(dictionary.getDictValueByIdx(i));
        }
    }
    public void setStatusDictionary(Dictionary dictionary){
        statusDictionary = dictionary;

        for (int i = 0; i < dictionary.getSize(); i++){
            resourceStatusComboBox.addItem(dictionary.getDictValueByIdx(i));
        }
    }

    public void SetChangeDictionaryForm() {
        int ID = Integer.parseInt(DictionaryTable.getValueAt(DictionaryTable.getSelectedRow(), 0).toString());
        String dict_value  = DictionaryTable.getValueAt(DictionaryTable.getSelectedRow(), 1).toString();
        dictForm.SetField(ID, dict_value);
    }

    public void editDictionary(int id, String dict_value) {

        if (id >= 0) {
            // Изменение


            _parent.editValueInDictionary(currentDictionaryToEdit.getTable_name(), dict_value,
                    Integer.parseInt(DictionaryTable.getValueAt(DictionaryTable.getSelectedRow(), 0).toString()));

            try {
                updateTable(currentDictionaryToEdit.getTable_name());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Добавление
            _parent.addValueInDictionary(currentDictionaryToEdit.getTable_name(), dict_value);
            try {
                updateTable(currentDictionaryToEdit.getTable_name());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
