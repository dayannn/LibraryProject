package libSource;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import libSource.mainwindow;

/**
 * Created by admin on 06/05/17.
 */
public class ChangeUser {

    private JFrame frame = new JFrame("ChangeUser");
    private JButton войтиButton;
    private JRadioButton администраторRadioButton;
    private JRadioButton операторRadioButton;
    private JPanel Userpanel;
    private boolean IsAdmin = false;
    private AuthenticationForm auth;

    public mainwindow getMw1() {
        return mw1;
    }

    private mainwindow mw1;

    public ChangeUser(mainwindow mw) {
        mw1 = mw;
        auth = new AuthenticationForm(this );
        frame.setContentPane(Userpanel);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        //frame.setSize(100,100);
        frame.pack();
        войтиButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!администраторRadioButton.isSelected()) {
                    mw.setUserRole(false);
                    frame.setVisible(false);
                }
                if(администраторRadioButton.isSelected())
                {
                    auth.show();
                }
            }
        });
    }

    public void SetAdmin(boolean temp) {
        IsAdmin = temp;
        mw1.setUserRole(IsAdmin);
        frame.setVisible(false);
    }

    public boolean IsAdmin() {
        return IsAdmin;
    }

    public void show() {
        frame.setVisible(true);
    }


}
