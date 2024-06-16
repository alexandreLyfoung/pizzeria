package Controlers;

import Entities.Client;
import Entities.Pizza;
import Tools.ConnexionBDD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientController {
    private Connection cnx;
    private PreparedStatement pst;
    private ResultSet rs;

    public ClientController() { cnx = ConnexionBDD.getCnx();
    }

    public boolean IsClient(String nom, String prenom, Client client) {
        boolean ok = false;
        try {
            pst = cnx.prepareStatement("SELECT client.IdClient, client.nom, client.prenom\n" +
                    "FROM client\n" +
                    "WHERE client.nom=? AND client.prenom=?");
            pst.setString(1,nom);
            pst.setString(2,prenom);
            rs=pst.executeQuery();
            if(!rs.next())
            {
                return false;
            }
            else {
                client.setId(rs.getInt("IdClient"));
                client.setNom(rs.getString("nom"));
                client.setPrenom(rs.getString("prenom"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    public void getSolde(Client client) {
        try
        {
            pst = cnx.prepareStatement("SELECT client.solde\n" +
                    "FROM client\n" +
                    "WHERE client.nom=? AND client.prenom=?");
            pst.setString(1, client.getNom());
            pst.setString(2, client.getPrenom());
            rs = pst.executeQuery();
            rs.next();
            client.setSolde(rs.getDouble("solde"));

        }catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void addNbPizza(Client client) {
        try
        {
            pst = cnx.prepareStatement("update client\n" +
                    "set nbPizzaCommande = ?\n" +
                    "Where client.IdClient = ?;");
            pst.setInt(1, client.getNbPizza());
            pst.setInt(2, client.getId());
            pst.executeUpdate();
        }catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
    public void updateNbPizza(Client client) {
        try
        {
            pst = cnx.prepareStatement("Select Count(commande.IdClient) AS NbPizza\n" +
                    "from commande\n" +
                    "where commande.IdClient = ?;");
            pst.setInt(1, client.getId());
            rs = pst.executeQuery();
            rs.next();
            int nbPizza = rs.getInt("NbPizza");
            client.setNbPizza(nbPizza);
            pst = cnx.prepareStatement("update client\n" +
                    "set nbPizzaCommande = ?\n" +
                    "Where client.IdClient = ?;");
            pst.setInt(1, nbPizza);
            pst.setInt(2, client.getId());
            pst.executeUpdate();
        }catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void getNbPizza(Client client) {
        try{
            pst = cnx.prepareStatement("SELECT client.nbPizzaCommande\n" +
                    "FROM client\n" +
                    "WHERE client.nom=? AND client.prenom=?");
            pst.setString(1, client.getNom());
            pst.setString(2, client.getPrenom());
            rs = pst.executeQuery();
            rs.next();
            client.setNbPizza(rs.getInt("nbPizzaCommande"));
        }catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
    public void facturationPizza(Client client) {
        try {
            pst = cnx.prepareStatement("Update client\n" +
                    "Set client.solde = ?\n" +
                    "Where client.IdClient = ?");
            pst.setDouble(1,client.getSolde());
            pst.setInt(2,client.getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
