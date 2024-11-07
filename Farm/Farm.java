import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.lang.Math;

//The Farm is a class containing a Field and Animals.
//The Field is produced by setting WIDTH, and HEIGHT, and calling the growField() method with them
//Grow Rate determines the maximum number of plants that can grow per second. 
//The 'Goal' of the farm is to produce strange animals or as much cash as possible.
public class Farm
{ 
    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;
    public static final int GROW_RATE = 5;
    public static Field field;
    public static Animal[][] animalLocations = new Animal[WIDTH][HEIGHT];
    public static ArrayList<Animal> animalList = new ArrayList<Animal>();
    public static double cash = 0.00;
    public static boolean running = true;
    public static void main(String[] args){  
         try{ 
            //Reader and Scanner check for user input, and will delay updating while the user is typing. 
            Reader rdr = new InputStreamReader(System.in);
            Scanner s = new Scanner(System.in);
            //DecimalFormat is used to display dollar signs. 
            DecimalFormat df = new DecimalFormat("$"+"#,###.##");
            clearScreen();
            field = growFirstField(WIDTH, HEIGHT);
            //first Animals created with parameters: Species, Name, #Legs, Weight, Feralizes, unicode character display, and weight gain when eating. 
            animalList.add(new Animal( "Cow", "Foo", 4, 310.25, true, "\uD83D\uDC04",10));
            animalList.add(new Animal( "Cow", "Bar", 4, 210.25, true, "\uD83D\uDC04",10));
            animalList.add(new Animal( "Pig", "Baz", 4, 100, true, "\uD83D\uDC16",15));
            animalList.add(new Animal( "Chicken", "Little", 2, 25, true, "\uD83D\uDC16",15));
            animalList.add(new Animal( "Spider", "Peter", 8, 1.5, false, "\uD83D\uDD77",1));
            placeAnimals(animalList);
            field.getAnimals(animalLocations);   
            while(running){
                if (rdr.ready()) {
                    String input = s.next();
                    if(input.equals("exit")){
                        running = false;
                    }
                    else if(input.equals("sell")){
                        clearScreen();
                        System.out.println("Which animal would you like to sell?\n");
                        for(int a = 0; a < animalList.size(); a++){
                            animalList.get(a).setPrice();
                            System.out.println("Name:"+ animalList.get(a).getName() + "  Species:" + animalList.get(a).getSpecies() + "  Price:" + df.format(animalList.get(a).getPrice()) + "  Weight:" + animalList.get(a).getWeight() + "  Legs:" + animalList.get(a).getNumberOfLegs()  + " Gain:" + animalList.get(a).getGain());
                        }
                        System.out.println("Commands :  [name]  exit");
                        input = s.next();
                        for(int a = 0; a < animalList.size(); a++){
                            if(input.equals("exit")){
                                a+=animalList.size();
                            }
                            else if(input.equals(animalList.get(a).getName())){
                                System.out.println("Are you sure you want to sell " + animalList.get(a).getName() + "?");
                                System.out.println("Yes or No?");
                                input = s.next();
                                if(input.equals("Yes") || input.equals("yes") || input.equals("y")){
                                    cash+=animalList.get(a).getPrice();
                                    animalList.remove(a);
                                    placeAnimals(animalList);
                                    field.getAnimals(animalLocations);
                                }
                            }
                        }
                    }
                }
                else{
                    clearScreen();
                    field.display();
                    System.out.println("Cash: " + df.format(cash));
                    System.out.println("Commands: sell exit");
                    field.move();
                    generate();
                    field.grow(GROW_RATE);
                    Thread.sleep(1000);
                }
            }
        }
        catch (Exception e) {         
            // catching the exception
            System.out.println(e);
        }
    }
    //Pre: WIDTH and HEIGHT are initialized and put into the parameters.
    //Post: Fills the field with its initial characters. 
    public static Field growFirstField(int w, int h){
        return new Field(w,h);
    }
    //Pre: animals is a full ArrayList of animals.
    //Post: the 2d Array Animal List has animals randomly placed within. 
    //       If you cannot place each animal it requires some to be sold. 
    public static void placeAnimals(ArrayList<Animal> animals){
        int animalCount = animalList.size();
        if(animalCount < WIDTH*HEIGHT){
            animalCount = 0;
            animalLocations = new Animal[WIDTH][HEIGHT];
            while(animalCount < animalList.size()){
                int c = (int)(Math.random()*WIDTH);
                int r = (int) (Math.random()*HEIGHT);
                if(animalLocations[c][r]==null){
                    animalLocations[c][r] = animalList.get(animalCount);
                    animalCount++;
                }
            } 
        }
        else{
            while(animalList.size()>= WIDTH*HEIGHT){
                clearScreen();
                Scanner s = new Scanner(System.in);
                DecimalFormat df = new DecimalFormat("$"+"#,###.##");
                System.out.println("Error: Too many animals!");
                System.out.println("Which animal would you like to sell?\n");
                for(int a = 0; a < animalList.size(); a++){
                    animalList.get(a).setPrice();
                    System.out.println("Name:"+ animalList.get(a).getName() + "  Species:" + animalList.get(a).getSpecies() + "  Price:" + df.format(animalList.get(a).getPrice()) + "  Weight:" + animalList.get(a).getWeight() + "  Legs:" + animalList.get(a).getNumberOfLegs()  + " Gain:" + animalList.get(a).getGain());
                }
                System.out.println("Commands :  [name]  exit");
                String input = s.next();
                for(int a = 0; a < animalList.size(); a++){
                    if(input.equals("exit")){
                        a+=animalList.size();
                    }
                    else if(input.equals(animalList.get(a).getName())){
                        System.out.println("Are you sure you want to sell " + animalList.get(a).getName() + "?");
                        System.out.println("Yes or No?");
                        input = s.next();
                        if(input.equals("Yes") || input.equals("yes") || input.equals("y")){
                            cash+=animalList.get(a).getPrice();
                            animalList.remove(a);
                            placeAnimals(animalList);
                            field.getAnimals(animalLocations);
                        }
                    }
                }
            }
        }
    }
    //Pre: animalLocations contains animals and is not empty
    //Post: There is a chance for an animal generated by animalGen is added to the animalList. If two animals are adjecent the chance is 1:20.
    public static void generate(){
        for(int row = 0; row < WIDTH; row++){
            for(int col = 0; col < HEIGHT; col++){
                if(!(animalLocations[col][row]==null)){
                    int randBig = (int) (Math.random()*animalList.size()*5);
                    if(randBig == 1){
                        if(col!= 0 && !(animalLocations[col-1][row]==null)){
                            animalList.add(animalGen(animalLocations[col-1][row], animalLocations[col][row]));
                            placeAnimals(animalList);
                            field.getAnimals(animalLocations);
                        }
                        else if( col!= HEIGHT-1 && !(animalLocations[col+1][row]==null)){
                            animalList.add(animalGen(animalLocations[col+1][row], animalLocations[col][row]));
                            placeAnimals(animalList);
                            field.getAnimals(animalLocations);
                        }
                        else if( row!= 0 && !(animalLocations[col][row-1]==null)){
                            animalList.add(animalGen(animalLocations[col][row-1], animalLocations[col][row]));
                            placeAnimals(animalList);
                            field.getAnimals(animalLocations);
                        }
                        else if(row!= WIDTH-1 &&!(animalLocations[col][row+1]==null)){
                            animalList.add(animalGen(animalLocations[col][row+1], animalLocations[col][row]));
                            placeAnimals(animalList);
                            field.getAnimals(animalLocations);
                        }
                    }
                }
            }
        }
    }
    
    //Pre: Animal one and Animal two contain two Animals
    //Post: Returns an Animal with attributes based on the parents attributes
    public static Animal animalGen(Animal one, Animal two){
        Animal parentOne = one.genes();
        Animal parentTwo = two.genes();
        Scanner s = new Scanner(System.in);
        double max = parentOne.getPrice()*2;
        double min = parentTwo.getPrice()/2;
        double range = max - min + 1;
        double rand = (Math.random() * range) + min;
        double price = rand; 
        String newName;
        if(parentOne.getSpecies().equals(parentTwo.getSpecies())){
            newName = parentOne.getSpecies();
        }
        else{
            if(parentOne.getSpecies().length() >2 && parentTwo.getSpecies().length() > 2){
                newName = parentOne.getSpecies().substring(0,2) + parentTwo.getSpecies().substring(1);
            }
            else{
                newName = parentOne.getSpecies().concat(parentTwo.getSpecies());
            }
        }
        String species = newName;
        clearScreen();
        int either = (int) Math.round(Math.random());
        String display;
        if(either == 0){
            display = parentOne.display();
        }
        else{
            display = parentTwo.display();
        }
        System.out.println("A new " + species + " has been created!");
        System.out.println("What will you name this new " + species + "? " + display); 
        String name = s.next(); 
        max = parentOne.getNumberOfLegs()*2;
        min = parentTwo.getNumberOfLegs()/2;
        range = max - min + 1;
        rand = (Math.random() * range) + min;
        int numberOfLegs =(int) rand;
        max = parentOne.getWeight()*2;
        min = parentTwo.getWeight()/2;
        range = max - min + 1;
        rand = (Math.random() * range) + min;
        double weight = rand;
        boolean fertilizer = parentTwo.fertilizes() && parentOne.fertilizes();
        max = parentOne.getGain()*2;
        min = parentTwo.getGain()/2;
        range = max - min + 1;
        rand = (Math.random() * range) + min;
        int gain = (int)rand; 
        Animal baby = new Animal(species, name, numberOfLegs, weight, fertilizer, display, gain);
        baby.setPrice();
        return baby;
    }
    //Pre: field is grown and the field.getAnimals method has been called. 
    //Post: prints a representation of the field and the animals
    public static void displayFarm(){
        field.display();
    }
    //Pre: n/a
    //Post: Terminal is cleared
    public static void clearScreen(){
        try{
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        }
        catch(Exception e){
            System.out.println("An error has occured");
        }
    }
}