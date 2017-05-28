package libSource;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by admin on 27/05/17.
 */
public class ChangeDirectoryForm {
    private JFrame frame = new JFrame("Change Dictionary Value");
    private JTextField DictionaryField;
    private JButton changeButton;
    private JPanel mainPanel;
    private int idOffline;
    private CardForm _parent;

    public ChangeDirectoryForm(CardForm parent) {
        _parent = parent;
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.pack();
        idOffline = -1;
        changeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //тут то, что происходит в момент применения изменений
                _parent.editDictionary(idOffline, DictionaryField.getText());
                frame.setVisible(false);
            }
        });
    }

    public void clearFields() {
        idOffline = -1;
        DictionaryField.setText("");
    }

    public void SetField(int id, String dict_value)
    {
        DictionaryField.setText(dict_value);
        idOffline = id;
    }

    public void show() {
        frame.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
