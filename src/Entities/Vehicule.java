package Entities;

public class Vehicule
{
    private int idVehicule;
    private String Type_de_vehicule;
    private String immatriculation;

    public Vehicule(){};

    public Vehicule(int idVehicule, String Type_de_vehicule, String immatriculation)
    {
        this.idVehicule = idVehicule;
        this.Type_de_vehicule = Type_de_vehicule;
        this.immatriculation = immatriculation;
    }

    public int getIdVehicule() {
        return idVehicule;
    }

    public void setIdVehicule(int idVehicule) {
        this.idVehicule = idVehicule;
    }

    public String getType_de_vehicule() {
        return Type_de_vehicule;
    }

    public void setType_de_vehicule(String type_de_vehicule) {
        Type_de_vehicule = type_de_vehicule;
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }
}
