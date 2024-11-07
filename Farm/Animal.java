//Authors: Mason Dellutri & Gavin Mandel & Alex Rothenberg & Nick Papillo & Leo Tobvis
import java.util.ArrayList;
public class Animal
{
    private double price; 
    private String species;
    private String name; 
    private int numberOfLegs;
    private double weight;
    private int gain;
    private boolean fertilizer;
    private String display;
    public Animal()
    {
        price = 0.0;
        species = "";
        name = "";
        numberOfLegs = 0;
        weight = 0.0;
        fertilizer = false;
        display = "?";
        gain = 10;
    }
    //Animals created with parameters: Species, Name, #Legs, Weight, Feralizes, unicode character display, and weight gain when eating. 
    public Animal(String s, String n, int l, double w, boolean f, String d, int g)
    {
        price = 0.0;
        species = s;
        name = n;
        numberOfLegs = l;
        weight = w;
        gain = 10;
        fertilizer = f;
        display = d;
        gain = g;
    } 
    public void setPrice(){
        price = numberOfLegs * weight;
    }
    public double getPrice(){
        return price;
    }
    public String getName(){
        return name;
    }
    public String getSpecies(){
        return species;
    }
    public String display(){
        return display;
    }
    public int getNumberOfLegs(){
        return numberOfLegs;
    }
    public boolean fertilizes(){
        return fertilizer;
    }
    public double getWeight(){
        return weight;
    }
    public void eatGrass(){
        weight+=gain;
    }
    public Animal genes(){
        return this;
    }
    public int getGain(){
        return gain;
    }
}
