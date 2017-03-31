import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by valeriewyns on 2017-03-31.
 */
public class FarmerSearch {
    private JButton searchButton;
    private JComboBox area;
    private JComboBox animalType;
    private JComboBox grainType;
    private JPanel background;

    public FarmerSearch() {
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JOptionPane.showMessageDialog(null, "And so here we would do something interesting");
                    }
                });
            }
        });
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("FarmerSearch");
        frame.setContentPane(new FarmerSearch().background);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
