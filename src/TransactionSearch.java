import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by valeriewyns on 2017-03-31.
 */
public class TransactionSearch {
    private JPanel background;
    private JButton searchButton;
    private JComboBox searchByDate;
    private JComboBox searchByProduct;
    private JComboBox searchByArea;
    private JComboBox searchByFarmer;
    private JTextArea results;

    public TransactionSearch() {
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "And so here we would do something interesting");
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("TransactionSearch");
        frame.setContentPane(new TransactionSearch().background);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
