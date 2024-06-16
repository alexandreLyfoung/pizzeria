package Vues;

import Controlers.CommandeController;
import Entities.Commande;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FrmCommande extends JFrame{
    private JLabel lblTitle;
    private JPanel pnlroot;
    private JComboBox cbxNumerosCommande;
    private JButton btnVoirFiche;

    public FrmCommande(){
        this.setContentPane(pnlroot);
        this.pack();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        CommandeController commandeController = new CommandeController();
        ArrayList<Commande> listOrder = commandeController.getAllOrder();
        for (Commande commande : listOrder) {
            cbxNumerosCommande.addItem(commande.getId());
        }
        btnVoirFiche.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = (int) cbxNumerosCommande.getSelectedItem();
                ArrayList<String> fiche = new ArrayList<>();
                fiche = commandeController.getFicheCommande();
                JOptionPane.showMessageDialog(null, "Temps de livraison : "+ fiche.get(0)+"\n"+
                        "Date de la commande : " + fiche.get(1) + "\n"+
                        "Nom pizza : "+ fiche.get(2) + "\n" +
                        "Type de vehicule : "+ fiche.get(3)+ "\n"+
                        "Client : "+ fiche.get(4) + " " + fiche.get(5)+"\n"+
                        "Livreur : "+ fiche.get(6) + " " + fiche.get(7)+"\n");
            }
        });
    }
}
