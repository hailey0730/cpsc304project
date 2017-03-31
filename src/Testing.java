import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by valeriewyns on 2017-03-31.
 */
public class Testing {
    private JPanel background;
    private JButton searchButton;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JComboBox comboBox3;

    public Testing() {
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "And so here we would do something interesting");
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Testing");
        frame.setContentPane(new Testing().background);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
