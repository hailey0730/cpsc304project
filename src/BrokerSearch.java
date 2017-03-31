import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by valeriewyns on 2017-03-31.
 */
public class BrokerSearch {
    private JPanel background;
    private JButton searchButton;
    private JTextArea results;
    private JTextField province;
    private JTextField product;
    private JTextField price;
    private JRadioButton max;
    private JRadioButton min;

    public BrokerSearch() {
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserInterface myInterface = new UserInterface();
                Boolean maxMin = true;
                myInterface.brokerUISearch(province.getText(), product.getText(), price.getText(), maxMin);
                JOptionPane.showMessageDialog(null, "And so here we would do something interesting");
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("BrokerSearch");
        frame.setContentPane(new BrokerSearch().background);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
