import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by valeriewyns on 2017-03-31.
 */
public class FarmerSearch {
    private JButton searchButton;
    private JPanel background;
    private JTextField province;
    private JTextField animalType;
    private JTextField grainType;
    private JTextArea results;

    private Connection con = null;

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
    public void searchByProvince() {
        try {
            Statement stmt = con.createStatement();
            StringBuffer statement = new StringBuffer("select distinct province from farmland");
            ResultSet rs = stmt.executeQuery(statement.toString());
            while (rs.next()) {
                System.out.println(rs.getString(1));
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("FarmerSearch");
        frame.setContentPane(new FarmerSearch().background);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
