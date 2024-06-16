package Vues;

import javax.swing.*;

public class frmClient extends JFrame {
    private JPanel pnlroot;
    private JLabel jblTitle;
    private JTextField txtNom;
    private JTextField txtPrenom;
    private JLabel jblNom;
    private JLabel jblPrenom;
    private JButton btnConnexion;

    public frmClient(JFrame connexion) {
        this.setContentPane(pnlroot);
        this.pack();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }
}
