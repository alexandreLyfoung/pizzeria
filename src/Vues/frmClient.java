package Vues;

import Controlers.ClientController;
import Entities.Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        txtNom.setText("");
        txtPrenom.setText("");

        btnConnexion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientController clientController = new ClientController();
                if (!txtNom.getText().isEmpty() && !txtPrenom.getText().isEmpty()) {
                    Client client = new Client();
                    if (clientController.IsClient(txtNom.getText(), txtPrenom.getText(), client))
                    {
                        FrmClientAccueil frmClientAccueil = new FrmClientAccueil(connexion, client);
                        frmClientAccueil.setVisible(true);
                    }
                }
            }
        });
    }
}
