package libSource;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

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
                String login = textField1.getText();
                String pass = textField2.getText();
                try {
                    int role = cu.getMw1().getMgr().getRoleForPair(login, pass);
                    if (role == 1) {
                        //labelerror.setVisible(false);
                        textField1.setText("");
                        textField2.setText("");

                        cu.SetAdmin(true);
                        frame.setVisible(false);

                    } else {
                            labelerror.setText("Неправильный пароль!");
                            textField1.setText("");
                            textField2.setText("");
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    public void show() {
        labelerror.setText(" ");
        frame.setVisible(true);
    }
}
