package Controlers;

import Entities.Client;
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
            pst = cnx.prepareStatement("SELECT client.nom, client.prenom\n" +
                    "FROM client\n" +
                    "WHERE client.nom=? AND client.prenom=?");
            pst.setString(1,nom);
            pst.setString(2,prenom);
            if(!rs.next())
            {
                return false;
            }
            else {
                client.setNom(rs.getString("nom"));
                client.setPrenom(rs.getString("prenom"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return true;
    }
}
