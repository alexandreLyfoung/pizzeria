package Controlers;

import Entities.Ingredients;
import Entities.Pizza;
import Tools.ConnexionBDD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PizzaController {
    private Connection cnx;
    private PreparedStatement pst;
    private ResultSet rs;

    public PizzaController(){cnx = ConnexionBDD.getCnx();
    }
    public ArrayList<Pizza> getPizzas(){
        ArrayList<Pizza> pizzas = new ArrayList<>();
        try
        {
            pst = cnx.prepareStatement("SELECT pizza.IdPizza, pizza.nom, pizza.prix\n" +
                    "FROM pizza"
                    );
            rs = pst.executeQuery();
            while(rs.next())
            {
                Pizza p = new Pizza(rs.getInt("IdPizza"), rs.getString("nom"), rs.getDouble("prix"));
                pizzas.add(p);
            }
            for (Pizza p : pizzas)
            {
                pst = cnx.prepareStatement("select ingredients.IdIngredients, ingredients.nom\n" +
                        "from ingredients\n" +
                        "inner join contient on contient.IdIngredients=ingredients.IdIngredients\n" +
                        "Inner join pizza on pizza.IdPizza=contient.IdPizza\n" +
                        "Where pizza.IdPizza = ?;");
                pst.setInt(1,p.getIdPizza());
                rs = pst.executeQuery();
                while(rs.next())
                {
                    Ingredients ingredients = new Ingredients(rs.getInt("IdIngredients"), rs.getString("nom"));
                    p.addIngredients(ingredients);
                }
            }

        }catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        return  pizzas;
    }
}
