package Controlers;

import Entities.Client;
import Entities.Pizza;
import Tools.ConnexionBDD;


import java.sql.*;
import java.time.LocalDate;
import java.security.SecureRandom;
import java.time.LocalTime;

public class CommandeController {
    private Connection cnx;
    private PreparedStatement pst;
    private ResultSet rs;

    public CommandeController() {cnx = ConnexionBDD.getCnx();
    }

    public boolean addCommande(Client client, Pizza pizza, LocalDate today, LocalTime tempsLivraison, int idTaille, int facturation) {
        SecureRandom random = new SecureRandom();
        int idLivreur = random.nextInt(4) + 1;
        int idVehicule = random.nextInt(13) + 1;
        boolean Ok = false;
        try{
            pst = cnx.prepareStatement("INSERT INTO Commande(temps_de_livraison, date_de_la_commande, facturation, IdClient, IdLivreur, IdPizza, IdVehicule, IdTaille) \n" +
                    "VALUE(?,?,?,?,?,?,?,?)");
            pst.setTime(1, Time.valueOf(tempsLivraison));
            pst.setDate(2, Date.valueOf(today));
            pst.setInt(3, facturation);
            pst.setInt(4, client.getId());
            pst.setInt(5, idLivreur);
            pst.setInt(6, pizza.getIdPizza());
            pst.setInt(7, idVehicule);
            pst.setInt(8, idTaille);
            if (pst.executeUpdate() > 0)
            {
                Ok = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Ok;
    }
}
