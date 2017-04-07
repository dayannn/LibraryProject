package libSource;

import javax.swing.*;
import java.awt.event.*;
import java.util.Iterator;

/**
 * Created by admin on 07/04/17.
 */
public class mainwindow {
    private JPanel panel1;
    private JButton changeUserButton;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTable table1;
    private JButton openCardButton;

    public mainwindow() {
        openCardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CardForm();

            }
        });
    }

    public static void main(String[] args)
    {
        //  Attributes test
        AttributeLink attributeLink = new AttributeLink("l");
        AttributeAccessType attributeAccessType = new AttributeAccessType("at");
        AttributeDescription attributeDescription = new AttributeDescription("d");
        AttributeName attributeName = new AttributeName("n");
        AttributeTheme attributeTheme = new AttributeTheme("th");
        AttributeType attributeType = new AttributeType("t");

        //  Attribute list test
        AttributeList attributeList = new AttributeList();
        attributeList.add(attributeAccessType);
        attributeList.add(attributeLink);
        attributeList.add(attributeDescription);
        attributeList.add(attributeName);
        attributeList.add(attributeTheme);
        attributeList.add(attributeType);

        Iterator<BaseAttribute> itr = attributeList.getIterator();
        while(itr.hasNext())
        {
            System.out.println(itr.next().getAttributeName());
        }

        attributeList.setAttributeValue(3, "1111");
        attributeList.setAttributeValue("Description", "dddddd");
        attributeList.set(0, attributeName);
        attributeList.set(0, attributeAccessType);
        attributeList.add(attributeName);
        attributeList.remove(attributeList.size() - 1);

        //  Source test
        System.out.println("\n");
        Source source = new Source(attributeList);
        System.out.println(source.getAccessType());
        System.out.println(source.getName());
        System.out.println(source.getType());
        System.out.println(source.getDescription());
        System.out.println(source.getLink());
        System.out.println(source.getTheme());

        JFrame frame = new JFrame("mainwindow");
        frame.setContentPane(new mainwindow().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        int a = 0;  // это что?
        a++;
    }


}
