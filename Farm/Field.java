import java.lang.Math;
public class Field 
{
    private int width;
    private int height;
    public char[][] grass;
    public Animal[][] animals;
    Field(int tw, int th){
        width = tw;
        height = th;
        grass = new char[width][height];
        animals = new Animal[width][height];
        for(int row = 0; row < height; row++){
            for(int col = 0; col < width; col++){
                int rand = (int) (Math.random()*4);
                if(rand==3){
                    grass[col][row] = '¥'; 
                }
                else if(rand==2){
                    grass[col][row] = 'Y'; 
                }
                else if(rand==1){
                    grass[col][row] = 'v'; 
                }
                else if(rand==0){
                    grass[col][row] = ','; 
                }
            } 
        }
    }
    void getAnimals(Animal[][] f){
        animals = f;
    }
    void display(){
        for(int row = 0; row < width; row++){
            for(int col = 0; col < height; col++){
                if(animals[col][row]==null){
                        System.out.print("[" + grass[col][row] + "]");
                }
                else{
                    System.out.print("["+animals[col][row].display()+"]");
                }
            }
            System.out.println();
        }
    }
    void move(){
        boolean[][] skip = new boolean[width][height];
        for(int row = 0; row < width; row++){
            for(int col = 0; col < height; col++){
                skip[col][row]= false;
            }
        }
        for(int row = 0; row < width; row++){
            for(int col = 0; col < height; col++){
                if(!(animals[col][row]==null)){
                    int rand = (int) (Math.random()*4);
                    if(col!= 0 && rand == 0 && animals[col-1][row]==null && !skip[col-1][row]){
                        graze(col, row);
                        animals[col-1][row] = animals[col][row];
                        animals[col][row] = null;
                        skip[col-1][row]=true;
                    }
                    else if(col!= height-1 && rand == 1 && animals[col+1][row]==null && !skip[col+1][row]){
                        graze(col, row);
                        animals[col+1][row] = animals[col][row];
                        animals[col][row] = null;
                        skip[col+1][row]=true;
                    }
                    else if(row!= 0 && rand == 2 && animals[col][row-1]==null && !skip[col][row-1]){
                        graze(col, row);
                        animals[col][row-1] = animals[col][row];
                        animals[col][row] = null;
                        skip[col][row-1]=true;
                    }
                    else if(row!= width-1 && rand == 3 && animals[col][row+1]==null && !skip[col][row+1]){
                        graze(col, row);
                        animals[col][row+1] = animals[col][row];
                        animals[col][row] = null;
                        skip[col][row+1]=true;
                    }
                }
            }
        }
    }
    public void grow(int n){
        int c = (int)(Math.random()*width);
        int r = (int)(Math.random()*height);
        int count = 0;
        while (count < n){
            if(grass[c][r] == 'v'){
                grass[c][r]= 'Y';
            }
            if(grass[c][r] == 'Y'){
                grass[c][r]= '¥';
            }
            count++;
        }
    }
    public void graze(int col, int row){
        if(grass[col][row]== ',' && animals[col][row].fertilizes()){
            grass[col][row] = 'v';
        }
        else if(grass[col][row]== '¥'){
            grass[col][row] = ',';
            animals[col][row].eatGrass();
        }
    }
}
