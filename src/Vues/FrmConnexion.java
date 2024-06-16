package Vues;

import Tools.ConnexionBDD;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class FrmConnexion extends JFrame {
    private JButton btnClient;
    private JButton btnAdmin;
    private JLabel jlbTitle;
    private JPanel pnlroot;
    private ConnexionBDD cnx;

    public JFrame getFrmConnexion() {
        return this;
    }

    public  FrmConnexion() throws SQLException , ClassNotFoundException{
        this.setTitle("Connexion");
        this.setContentPane(pnlroot);
        this.pack();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.cnx = new ConnexionBDD();


        btnClient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frmClient frmClient = new frmClient(getFrmConnexion());
                frmClient.setVisible(true);
                frmClient.toFront();
            }
        });
    }
}
