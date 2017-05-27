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
    private JButton Changebutton;
    private JPanel mainpanel;
    private int idofline;

    public ChangeDirectoryForm() {
        frame.setContentPane(mainpanel);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.pack();
        Changebutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //тут то, что происходит в момент применения изменений

            }
        });
    }

    public void Setfield(int idvalue, String temp)
    {
        DictionaryField.setText(temp);
        idofline = idvalue;
    }

    public void show() {
        frame.setVisible(true);
    }
}
