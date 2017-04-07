package libSource;

import javax.swing.*;

/**
 * Created by dayan on 07.04.2017.
 */
public class CardForm {
    private JFrame frame = new JFrame("CardForm");
    private JPanel CardPanel;
    private JFormattedTextField TJFormattedTextField;
    private JFormattedTextField vkComTJFormattedTextField;
    private JFormattedTextField newsMemesFormattedTextField;
    private JFormattedTextField formerTwitterJournalFormattedTextField;
    private JFormattedTextField freeFormattedTextField;


    public CardForm() {
        frame.setSize(500, 200);
        frame.setResizable(false);
        frame.setContentPane(CardPanel);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setVisible(true);
    }
}
