package Tools;

import Entities.Ingredients;
import Entities.Pizza;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class ModelJTable extends AbstractTableModel {
    private String[] nomsColonnes;
    private Object[][] rows;

    @Override
    public String getColumnName(int column) {
        return nomsColonnes[column];
    }

    @Override
    public int getRowCount() {
        return rows.length;
    }

    @Override
    public int getColumnCount() {
        return nomsColonnes.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return rows[rowIndex][columnIndex];
    }

    public void loadPizza(ArrayList<Pizza> pizzas){
        this.nomsColonnes= new String[]{"Num Pizza", "Nom Pizza", "Prix", "Ingredients"};
        this.rows = new Object[pizzas.size()][4];
        int i = 0;
        for(Pizza p: pizzas) {
            rows[i][0] = p.getIdPizza();
            rows[i][1] = p.getNom();
            rows[i][2] = p.getPrix();
            String listIngredients = "";
            for (Ingredients ingredients : p.getListIngredients())
            {
                listIngredients = listIngredients + ingredients.getNom() + ", ";
            }
            rows[i][3] = listIngredients;
            i++;
        }
        fireTableChanged(null);

    }


}
