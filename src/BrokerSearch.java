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
    //private JTextField price;
    private JRadioButton max;
    private JRadioButton min;

    public BrokerSearch() {
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserInterface myInterface = new UserInterface();
                Boolean checkMax = max.isSelected();
                Boolean checkMin = min.isSelected();
                String text =myInterface.brokerUISearch(province.getText(), product.getText(), checkMax, checkMin);
                if (text == "") {
                    JOptionPane.showMessageDialog(null,"Error! Please check ONE checkbox!");
                }
                results.setText(myInterface.brokerUISearch(province.getText(), product.getText(), checkMax, checkMin));

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
