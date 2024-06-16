package Vues;

import Controlers.CommandeController;
import Entities.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class FrmAdmin extends JFrame {
    private JPanel pnlroot;
    private JLabel jblTitle;
    private JLabel lblBestClient;
    private JLabel lblWorstDeliveryGuy;
    private JLabel lblMostVehiculeUsed;
    private JLabel lblPizzaMostWanted;
    private JLabel lblBestIngredients;
    private JLabel lblLessVehiculeUsed;
    private JButton btnSeeComand;
    private JTextArea txtAreaNbOrder;

    public FrmAdmin() {
        this.setContentPane(pnlroot);
        this.pack();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        CommandeController commandeController = new CommandeController();
        Client client = new Client();
        int nbBestClient = commandeController.getBestClient(client);
        lblBestClient.setText(client.getNom()+ " "+ client.getPrenom() + ": " + nbBestClient);
        Livreur worstLivreur = commandeController.getWorstLivreur();
        lblWorstDeliveryGuy.setText("Plus mauvais livreur: "+worstLivreur.getNom()+ " "+ worstLivreur.getPrenom());
        Pizza bestPizza = commandeController.getBestPizza();
        lblPizzaMostWanted.setText("Meilleure pizza : "+ bestPizza.getNom());
        Ingredients ingredients = commandeController.BestIngredients();
        lblBestIngredients.setText("Meilleur ingredient : "+ingredients.getNom());
        Vehicule mostUsedVehicule = commandeController.getMostUsedVehicule();
        lblMostVehiculeUsed.setText("Vehicule le plus utilisé: " + mostUsedVehicule.getType_de_vehicule()+ " "+ mostUsedVehicule.getImmatriculation());
        ArrayList<Vehicule> listVehicules = commandeController.getVehiculeNotUsed();
        String vehiculeNotUsed = "";
        for(Vehicule vehicule : listVehicules){
            vehiculeNotUsed = vehiculeNotUsed + vehicule.getType_de_vehicule() + " " + vehicule.getImmatriculation()+ "\n";
        }
        lblLessVehiculeUsed.setText("Vehicule non utilisée: "+vehiculeNotUsed);
        HashMap<Integer, Client> clients = commandeController.averageOrder();
        String averageOrder = "";
        for(Integer c : clients.keySet()){
            averageOrder = averageOrder + clients.get(c).getNom() + " " + clients.get(c).getPrenom() + " a commandé : " + c.toString() + " " + "\n";
        }
        txtAreaNbOrder.setText(averageOrder);

        btnSeeComand.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrmCommande frmCommande = new FrmCommande();
                frmCommande.setVisible(true);
                frmCommande.toFront();
                FrmAdmin.this.dispose();
            }
        });

    }
}
