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
    private JRadioButton читательRadioButton;
    private JPanel Userpanel;
    private boolean IsAdmin;

    private mainwindow mw;

    public ChangeUser(mainwindow mw) {
        frame.setContentPane(Userpanel);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.pack();
        войтиButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IsAdmin = администраторRadioButton.isSelected();
                mw.setUserRole(IsAdmin);
            }
        });
    }

    public boolean IsAdmin() {
        return IsAdmin;
    }

    public void show() {
        frame.setVisible(true);
    }


}
