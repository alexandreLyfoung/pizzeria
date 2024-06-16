package Controlers;

import Entities.*;
import Tools.ConnexionBDD;


import java.sql.*;
import java.time.LocalDate;
import java.security.SecureRandom;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

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

    public int getBestClient(Client client) {
        int pizzaCommand = 0;
        try
        {
            pst = cnx.prepareStatement("Select commande.IdClient, Count(commande.IdClient) as MAXIMUM\n" +
                    "from commande\n" +
                    "group by commande.IdClient \n" +
                    "order by MAXIMUM desc ;");
            rs = pst.executeQuery();
            rs.next();
            int idClient = rs.getInt("IdClient");
            pizzaCommand = rs.getInt("MAXIMUM");
            pst = cnx.prepareStatement("SELECT client.IdClient, client.nom, client.prenom\n" +
                    "From client\n" +
                    "WHERE client.IdClient = ?");
            pst.setInt(1, idClient);
            rs = pst.executeQuery();
            rs.next();
            client.setNom(rs.getString("nom"));
            client.setPrenom(rs.getString("prenom"));
            rs.close();
            pst.close();

        }catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        return pizzaCommand;
    }

    public Livreur getWorstLivreur() {
        Livreur livreur = new Livreur();
        try
        {
            pst = cnx.prepareStatement("Select commande.IdLivreur, Count(commande.Temps_de_livraison) as retard\n" +
                    "from commande\n" +
                    "Where commande.Temps_de_livraison > \"00:30:00\"\n" +
                    "group by commande.IdLivreur\n" +
                    "order by retard desc ;");
            rs = pst.executeQuery();
            rs.next();
            livreur.setId(rs.getInt("IdLivreur"));
            pst = cnx.prepareStatement("Select livreur.nom, livreur.prenom\n" +
                    "from livreur\n" +
                    "Where livreur.IdLivreur = ?");
            pst.setInt(1, livreur.getId());
            rs = pst.executeQuery();
            rs.next();
            livreur.setNom(rs.getString("nom"));
            livreur.setPrenom(rs.getString("prenom"));
            rs.close();
            pst.close();
        }catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        return livreur;
    }

    public Vehicule getMostUsedVehicule()
    {
        Vehicule vehicule = new Vehicule();
        try
        {
            pst = cnx.prepareStatement("Select commande.IdVehicule, Count(commande.IdVehicule) as nbVehicule\n" +
                    "from commande\n" +
                    "group by commande.IdVehicule\n" +
                    "order by nbVehicule desc ;");
            rs = pst.executeQuery();
            rs.next();
            vehicule.setIdVehicule(rs.getInt("idVehicule"));
            pst = cnx.prepareStatement("Select vehicule.Type_de_vehicule, vehicule.Immatriculation\n" +
                    "from vehicule\n" +
                    "where vehicule.IdVehicule = ?;");
            pst.setInt(1, vehicule.getIdVehicule());
            rs = pst.executeQuery();
            rs.next();
            vehicule.setImmatriculation(rs.getString("Immatriculation"));
            vehicule.setType_de_vehicule(rs.getString("Type_de_vehicule"));
            rs.close();
            pst.close();
        }catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        return vehicule;
    }

    public Pizza getBestPizza() {
        Pizza pizza = new Pizza();
        try
        {
            pst = cnx.prepareStatement("Select commande.IdPizza, Count(commande.IdPizza) as nbPizza\n" +
                    "from commande\n" +
                    "group by commande.IdPizza \n" +
                    "order by nbPizza desc ;");
            rs = pst.executeQuery();
            rs.next();
            pizza.setIdPizza(rs.getInt("idPizza"));
            pst = cnx.prepareStatement("SELECT pizza.nom from pizza where pizza.IdPizza=?");
            pst.setInt(1, pizza.getIdPizza());
            rs = pst.executeQuery();
            rs.next();
            pizza.setNom(rs.getString("nom"));
            rs.close();
            pst.close();

        }catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        return pizza;
    }
    public Ingredients BestIngredients() {
        Ingredients ingredients = new Ingredients();
        try {
            pst = cnx.prepareStatement("Select contient.IdIngredients, Count(contient.IdPizza) as nbIngredient\n" +
                    "                    from contient\n" +
                    "                    inner join commande on contient.IdPizza=commande.IdPizza\n" +
                    "                    group by contient.IdIngredients \n" +
                    "                    order by nbIngredient desc ;");
            rs = pst.executeQuery();
            rs.next();
            ingredients.setId(rs.getInt("IdIngredients"));
            pst = cnx.prepareStatement("SELECT ingredients.Nom from ingredients where ingredients.IdIngredients=?");
            pst.setInt(1, ingredients.getId());
            rs = pst.executeQuery();
            rs.next();
            ingredients.setNom(rs.getString("nom"));
            rs.close();
            pst.close();

        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        return ingredients;
    }

    public ArrayList<Vehicule> getVehiculeNotUsed()
    {
        ArrayList<Vehicule> vehicules = new ArrayList<>();
        try
        {
            pst = cnx.prepareStatement("Select vehicule.IdVehicule, vehicule.Type_de_vehicule, vehicule.Immatriculation\n" +
                    "from vehicule\n" +
                    "where vehicule.IdVehicule Not IN (Select commande.IdVehicule\n" +
                    "from commande);");
            rs = pst.executeQuery();
            while(rs.next())
            {
                Vehicule vehicule = new Vehicule(rs.getInt(1), rs.getString(2), rs.getString(3));
                vehicules.add(vehicule);
            }
            rs.close();
            pst.close();
        }catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        return vehicules;
    }

    public ArrayList<Commande> getAllOrder()
    {
        ArrayList<Commande> commandes = new ArrayList<>();
        try{
            pst = cnx.prepareStatement("SELECT commande.IdCommande from commande");
            rs = pst.executeQuery();
            while(rs.next())
            {
                Commande commande = new Commande(rs.getInt(1));
                commandes.add(commande);
            }
        }catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        return commandes;
    }
    public ArrayList<String> getFicheCommande()
    {
        ArrayList<String> ficheCommande = new ArrayList<>();
        try
        {
            pst = cnx.prepareStatement("Select commande.Temps_de_livraison, commande.Date_de_la_commande, commande.IdClient, commande.IdLivreur, commande.IdPizza, commande.IdVehicule\n" +
                    "from commande");
            rs = pst.executeQuery();
            rs.next();
            String temps = rs.getString("Temps_de_livraison");
            String dateCommande = rs.getString("Date_de_la_commande");
            int idPizza = rs.getInt("IdPizza");
            int idVehicule = rs.getInt("IdVehicule");
            int idClient = rs.getInt("IdClient");
            int idLivreur = rs.getInt("IdLivreur");

            ficheCommande.add(temps);
            ficheCommande.add(dateCommande);

            pst = cnx.prepareStatement("Select pizza.nom from pizza where pizza.IdPizza=?");
            pst.setInt(1, idPizza);
            rs = pst.executeQuery();
            rs.next();
            String nomPizza = rs.getString("nom");
            ficheCommande.add(nomPizza);

            pst = cnx.prepareStatement("Select vehicule.Type_de_vehicule from vehicule where vehicule.IdVehicule=?");
            pst.setInt(1, idVehicule);
            rs = pst.executeQuery();
            rs.next();
            String typeDeVehicule = rs.getString("Type_de_vehicule");
            ficheCommande.add(typeDeVehicule);

            pst = cnx.prepareStatement("Select client.nom, client.prenom from client where client.IdClient=?");
            pst.setInt(1, idClient);
            rs = pst.executeQuery();
            rs.next();
            String nomClient = rs.getString("nom");
            String prenomClient = rs.getString("prenom");
            ficheCommande.add(nomClient);
            ficheCommande.add(prenomClient);

            pst = cnx.prepareStatement("Select livreur.Nom, livreur.Prenom from livreur where livreur.IdLivreur=?");
            pst.setInt(1, idLivreur);
            rs = pst.executeQuery();
            rs.next();
            String livreurNom = rs.getString("Nom");
            String livreurPrenom = rs.getString("Prenom");
            ficheCommande.add(livreurNom);
            ficheCommande.add(livreurPrenom);

            rs.close();
            pst.close();


        }catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        return ficheCommande;
    }

    public HashMap<Integer, Client> averageOrder()
    {
        HashMap<Integer, Client> clients = new HashMap<>();
        try
        {
            pst = cnx.prepareStatement("SELECT count(commande.IdCommande) As NbCommande, client.nom, client.prenom\n" +
                    "from commande\n" +
                    "inner join Client on client.IdClient=commande.IdClient\n" +
                    "Group by commande.IdClient;");
            rs = pst.executeQuery();
            while (rs.next())
            {
                Client client = new Client(rs.getString(2), rs.getString(3));
                int nbCommande = rs.getInt(1);
                clients.put(nbCommande, client);
            }
            rs.close();
            pst.close();
        }catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        return clients;
    }
}
