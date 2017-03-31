import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by valeriewyns on 2017-03-31.
 */
public class Transaction {
    private JButton searchButton;
    private JComboBox area;
    private JComboBox farmName;
    private JComboBox animal;
    private JComboBox units;
    private JComboBox grain;
    private JComboBox kg;
    private JTextArea price;
    private JPanel background;

    public Transaction() {
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "And so here we would do something interesting");
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Transaction");
        frame.setContentPane(new Transaction().background);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
