package libSource;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StatsForm {
    private JFrame frame = new JFrame("Статистика");
    private JPanel mainPanel;
    private JComboBox comboBox1;
    private JTable table1;
    private JButton ShowStatsButton;
    private JPanel StatsPanel;
    private JTextField monthTextField;
    private JTextField yearTextField;
    private JScrollPane table1_sp;

    private mainwindow _parent;

    public StatsForm(mainwindow parent) {
        _parent = parent;

        frame.setContentPane(mainPanel);
        frame.setSize(300, 350);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);



        ShowStatsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try  {
                    table1.setModel(parent.getMgr().getStatsByDate(Integer.parseInt(yearTextField.getText()), Integer.parseInt(monthTextField.getText())));
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public void show() {
        frame.setVisible(true);
    }

}
