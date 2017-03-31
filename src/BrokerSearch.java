import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by valeriewyns on 2017-03-31.
 */
public class BrokerSearch {
    private JPanel background;
    private JButton searchButton;
    private JComboBox area;
    private JComboBox farmName;
    private JComboBox animal;
    private JComboBox grain;
    private JComboBox price;
    private JTextArea results;

    public BrokerSearch() {
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
