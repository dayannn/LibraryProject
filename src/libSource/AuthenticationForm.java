package libSource;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by admin on 24/05/17.
 */
public class AuthenticationForm {
    private JFrame frame = new JFrame("Authentication");
    private JTextField textField1;
    private JButton enterButton;
    private JTextField textField2;
    private JPanel panel1;
    private JPanel panelmain;
    private JLabel labelerror;

    private ChangeUser cu;

    public AuthenticationForm(ChangeUser cu) {
        labelerror.setText(" ");
        frame.setContentPane(panelmain);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);


        //frame.setMinimumSize();
        frame.pack();
        //this.frame = frame;
        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (textField1.getText().equals("admin") && textField2.getText().equals("12345"))
                {
                    //labelerror.setVisible(false);
                    //textField1.setText("");
                    //textField2.setText("");

                    cu.SetAdmin(true);
                    frame.setVisible(false);

                }
                else
                {
                    labelerror.setText("Неправильный пароль!");
                    textField1.setText("");
                    textField2.setText("");
                }

            }
        });
    }

    public void show() {
        labelerror.setText(" ");
        frame.setVisible(true);
    }
}
