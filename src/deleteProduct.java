import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by valeriewyns on 2017-03-31.
 */
public class deleteProduct {
    private JPanel background;
    private JButton searchButton;
    private JTextArea results;
    private JTextField farmerID;
    private JTextField brokerID;
    private JTextField productID;
    private JTextField productUnits;

    public deleteProduct() {
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserInterface myInterface = new UserInterface();
                results.setText(myInterface.transactionUISearch(farmerID.getText(),  productID.getText()));
           }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("TransactionSearch");
        frame.setContentPane(new deleteProduct().background);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
