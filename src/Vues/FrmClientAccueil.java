package Vues;

import Controlers.ClientController;
import Controlers.CommandeController;
import Controlers.PizzaController;
import Entities.Client;
import Entities.Pizza;
import Tools.ModelJTable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class FrmClientAccueil extends JFrame{
    private JPanel pnlRoot;
    private JLabel jblTitle;
    private JButton commanderButton;
    private JTable tblPizza;
    private JLabel jblClient;
    private JLabel jblSolde;
    private JComboBox cbxTaille;

    private ArrayList<LocalTime> listTime = new ArrayList<>();
    private int setTime = 0;
    private ModelJTable mdl;

    private ArrayList<Pizza> listPizza;

    public FrmClientAccueil(JFrame connexion, Client client) {
        this.setContentPane(pnlRoot);
        this.pack();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        ClientController clientController = new ClientController();
        jblClient.setText(client.getNom() +" "+ client.getPrenom());
        clientController.getSolde(client);
        clientController.updateNbPizza(client);
        jblSolde.setText("Solde: "+ client.getSolde());

        LocalTime localTime = LocalTime.of(0,10);
        LocalTime localTime1 = LocalTime.of(0,20);
        LocalTime localTime2 = LocalTime.of(0,31);
        LocalTime localTime3 = LocalTime.of(0,40);
        listTime.add(localTime);
        listTime.add(localTime1);
        listTime.add(localTime2);
        listTime.add(localTime3);

        PizzaController pizzaController = new PizzaController();
        this.mdl =new ModelJTable();
        listPizza = pizzaController.getPizzas();
        mdl.loadPizza(listPizza);
        this.tblPizza.setModel(mdl);



        commanderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tblPizza.getSelectedRow() != -1) {
                    double prix = Double.parseDouble(tblPizza.getValueAt(tblPizza.getSelectedRow(), 2).toString());
                    if (client.getSolde() >= prix) {
                        client.setNbPizza(client.getNbPizza() + 1);
                        LocalDate currentDate = LocalDate.now();
                        if (setTime >= 4) {
                            setTime = 0;
                        }
                        LocalTime tempsLivraison = listTime.get(setTime);
                        int idTaille;
                        if (cbxTaille.getSelectedItem().toString().equals("naine")) {
                            idTaille = 1;
                        }
                        else if (cbxTaille.getSelectedItem().toString().equals("humaine")) {
                            idTaille = 2;
                        }else {
                            idTaille = 3;
                        }
                        int facturation = 1;
                        if (tempsLivraison.compareTo(LocalTime.of(0,30)) >= 0 || (client.getNbPizza()%10) == 0) {
                            facturation = 0;
                        }
                        else {
                            double value = client.getSolde();
                            if (idTaille == 1) {
                                value = value - (listPizza.get(tblPizza.getSelectedRow()).getPrix() * 2/3);
                                JOptionPane.showMessageDialog(null, value);
                                client.setSolde(value);
                            } else if (idTaille == 2) {
                                client.setSolde(client.getSolde() - listPizza.get(tblPizza.getSelectedRow()).getPrix());
                            }
                            else {
                                client.setSolde(client.getSolde() - (listPizza.get(tblPizza.getSelectedRow()).getPrix()) * 4/3);
                            }
                            clientController.facturationPizza(client);
                        }
                        CommandeController commandeController = new CommandeController();
                        clientController.addNbPizza(client);
                        commandeController.addCommande(client, listPizza.get(tblPizza.getSelectedRow()), currentDate, tempsLivraison, idTaille, facturation);
                        JOptionPane.showMessageDialog(null, "Votre commande a été abouti", "Pizza commandée", JOptionPane.OK_OPTION);
                        jblSolde.setText("Solde: "+ client.getSolde());
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Votre solde est insuffisant il faut le remplir", "Solde insuffisant", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }
}
