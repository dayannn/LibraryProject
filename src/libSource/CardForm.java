package libSource;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

/**
 * Created by dayan on 07.04.2017.
 */
public class CardForm {
    private JFrame frame = new JFrame("CardForm");
    private JPanel CardPanel;
    private JButton editButton;
    private JButton saveButton;
    private JButton discardButton;
    private JTabbedPane tabbedPane1;
    private JTextArea annotationTextArea;
    private JTextArea resourseNameTextArea;
    private JTextArea resourseOperatorTextArea;
    private JTextArea addressTextArea;
    private JTextArea subjectsTextArea;
    private JTextArea resourseTypeTextArea;
    private JTextArea infoKindTextArea;
    private JTextArea resourseVolumeTextArea;
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
    private JComboBox testAccessComboBox;
    private JTextField testTimeTextField;
    private JTextArea testAccessConclusionTextArea;
    private JTextArea accessTypeTextArea;
    private JTextField testAccessTextField;
    private JComboBox accessTypeComboBox;
    private JScrollPane accessTypeScrollPanel;
    private JList ArchiveList;
    private JTextArea archiveNameTextArea;
    private JTextArea changeDescrTextArea;
    private Archive archive;
    private boolean IsAdmin;

    public boolean isAdmin() {
        return IsAdmin;
    }

    public void setAdmin(boolean admin) {
        IsAdmin = admin;
        if (IsAdmin) {
            setFieldsEditable();
            saveButton.setVisible(true);
            discardButton.setVisible(true);
            editButton.setVisible(false);
        }

        else {
            setFieldsUneditable();
            saveButton.setVisible(false);
            discardButton.setVisible(false);
            editButton.setVisible(true);setFieldsEditable();
            saveButton.setVisible(true);
            discardButton.setVisible(true);
            editButton.setVisible(false);
        }

    }


    private java.util.List<JTextArea> textAreasList = new ArrayList<>();
        // говнокод? :/ не, заебись
    private void fillTextAreasList(){
        textAreasList.add(resourseNameTextArea);
        textAreasList.add(annotationTextArea);
        textAreasList.add(subjectsTextArea);
        textAreasList.add(addressTextArea);
        textAreasList.add(accessTypeTextArea);
        textAreasList.add(resourseTypeTextArea);
        textAreasList.add(infoKindTextArea);

        textAreasList.add(languageTextArea);

        textAreasList.add(resourseOperatorTextArea);

        textAreasList.add(resourseVolumeTextArea);
        textAreasList.add(timeTextArea);
        textAreasList.add(archiveInfoTextArea);
        textAreasList.add(providerTextArea);
        textAreasList.add(subscriptionModelTextArea);
        textAreasList.add(paymentMethodTextArea);
        textAreasList.add(contractTimeTextArea);
        textAreasList.add(documentPropsTextArea);
        textAreasList.add(subscriptionCostTextArea);
        textAreasList.add(accessModeTextArea);
        textAreasList.add(testAccessConclusionTextArea);
    }

    private void setFieldsUneditable(){
        for (JTextArea area : textAreasList
             ) {
            area.setEditable(false);
            area.setBackground(Color.lightGray);
            testAccessComboBox.setVisible(false);
            testAccessTextField.setVisible(true);
            testTimeTextField.setEditable(false);
            testTimeTextField.setBackground(Color.lightGray);
            accessTypeScrollPanel.setVisible(true);
            accessTypeComboBox.setVisible(false);
        }

    }

    private void setFieldsEditable(){
        for (JTextArea area : textAreasList
                ) {
            area.setEditable(true);
            area.setBackground(Color.white);
            testAccessTextField.setVisible(false);
            testAccessComboBox.setVisible(true);
            testTimeTextField.setEditable(true);
            testTimeTextField.setBackground(Color.white);
            accessTypeScrollPanel.setVisible(false);
            accessTypeComboBox.setVisible(true);
        }
    }

    private boolean fieldsAreCorrect(){

        /*
        HERE TO BE CHECKING
         */
        return true;
    }

    public CardForm() {
        saveButton.setVisible(false);
        discardButton.setVisible(false);
        frame.setSize(650, 550);
       // frame.setResizable(false);
        frame.setContentPane(CardPanel);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);


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
                if (!fieldsAreCorrect())
                    return;
                setFieldsUneditable();
                editButton.setVisible(true);
                discardButton.setVisible(false);
                saveButton.setVisible(false);
            }
        });

        discardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setFieldsUneditable();
                editButton.setVisible(true);
                saveButton.setVisible(false);
                discardButton.setVisible(false);
            }
        });

        ArchiveList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                // отобразить iтый архив
                int ID = ArchiveList.getSelectedIndex();
                archiveNameTextArea.setText(archive.getArchiveRecords().get(ID).getAttributeList().get(0).getAttributeValue());
                changeDescrTextArea.setText(archive.getArchiveRecords().get(ID).getChg_dscr());
            }
        });
    }

    public void setFieldsBySource(BaseSource src) {
        for (int i = 0; i < src.getLength(); i++) {
            textAreasList.get(i).setText(src.getAttribute(i).getAttributeValue());
        }
    }
    public void setArchive(Archive arch) {
        archive = arch;
        DefaultListModel listModel = new DefaultListModel();
        archiveNameTextArea.removeAll();
        changeDescrTextArea.removeAll();

        //TODO: Возможно, переделать список дат под табличку
        for (int i = 0; i < arch.getArchiveRecords().size(); i++) {
            listModel.addElement(arch.getArchiveRecords().get(i).getDate());
        }

        ArchiveList.setModel(listModel);//arch.getArchiveRecords().get(i).getDate(
    }

    public void show() {
        frame.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
