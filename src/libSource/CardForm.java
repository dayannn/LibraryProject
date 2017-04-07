package libSource;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by dayan on 07.04.2017.
 */
public class CardForm {
    private JFrame frame = new JFrame("CardForm");
    private JPanel CardPanel;
    private JFormattedTextField nameFormattedTextField;
    private JFormattedTextField sourceFormattedTextField;
    private JFormattedTextField themesFormattedTextField;
    private JFormattedTextField descriptionFormattedTextField;
    private JFormattedTextField accessTypeFormattedTextField;
    private JButton editButton;
    private JButton saveButton;
    private JButton discardButton;


    private void setFieldsUneditable(){
        nameFormattedTextField.setEditable(false);
        sourceFormattedTextField.setEditable(false);
        themesFormattedTextField.setEditable(false);
        descriptionFormattedTextField.setEditable(false);
        accessTypeFormattedTextField.setEditable(false);
    }

    private void setFieldsEditable(){
        nameFormattedTextField.setEditable(true);
        sourceFormattedTextField.setEditable(true);
        themesFormattedTextField.setEditable(true);
        descriptionFormattedTextField.setEditable(true);
        accessTypeFormattedTextField.setEditable(true);
    }

    private  void returnFieldsColor(){
        nameFormattedTextField.setBackground(Color.lightGray);
        sourceFormattedTextField.setBackground(Color.lightGray);
        themesFormattedTextField.setBackground(Color.lightGray);
        descriptionFormattedTextField.setBackground(Color.lightGray);
        accessTypeFormattedTextField.setBackground(Color.lightGray);
    }

    private boolean fieldsAreCorrect(){     // TODO: make in lamda-function style?
        boolean res = true;
        if (nameFormattedTextField.getText().trim().isEmpty()) {
            nameFormattedTextField.setBackground(Color.pink);
            res = false;
        }

        if (sourceFormattedTextField.getText().trim().isEmpty()) {
            sourceFormattedTextField.setBackground(Color.pink);
            res = false;
        }

        if (themesFormattedTextField.getText().trim().isEmpty()) {
            themesFormattedTextField.setBackground(Color.pink);
            res = false;
        }

        if (descriptionFormattedTextField.getText().trim().isEmpty()) {
            descriptionFormattedTextField.setBackground(Color.pink);
            res = false;
        }

        return res;
    }

    public CardForm() {
        saveButton.hide();      // TODO: replace deprecated methods
        discardButton.hide();
        frame.setSize(500, 300);
        frame.setResizable(false);
        frame.setContentPane(CardPanel);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setVisible(true);


        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setFieldsEditable();
                saveButton.show();
                discardButton.show();
                editButton.hide();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnFieldsColor();
                if (!fieldsAreCorrect())
                    return;
                setFieldsUneditable();
                editButton.show();
            }
        });

        discardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnFieldsColor();
                setFieldsUneditable();
                editButton.show();
            }
        });
    }
}
