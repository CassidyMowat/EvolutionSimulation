package assignment22;

import assignment22.SpeciesEvo.Direction;
import assignment22.SpeciesEvo.Type;
import java.util.Random;
import java.awt.*;

/**
 *
 * @author Cassidy Mowat
 */
public class Creature {
    
    private Integer xLoc;
    private Integer yLoc;
    
    private int width = SpeciesEvo.SIZE;
    private int height = SpeciesEvo.SIZE;
    
    private int step = 1;
    
    private Integer energy = 100;
    
    private Double[] chromosone = new Double[13];
    
    public Creature() {
        Random rnd = new Random();
        this.xLoc = (rnd.nextInt(SpeciesEvo.arraySize) + 1) - 1;
        this.yLoc = (rnd.nextInt(SpeciesEvo.arraySize) + 1) - 1;
        
        for (int i = 0; i <= 12; i++){
            chromosone[i] = rnd.nextDouble();
        }
    }
    
    public Creature(Creature creatureOne, Creature creatureTwo){
        Random rnd = new Random();
        this.xLoc = (rnd.nextInt(SpeciesEvo.arraySize) + 1) - 1;
        this.yLoc = (rnd.nextInt(SpeciesEvo.arraySize) + 1) - 1;
        
        for(int i = 0; i < 5; i++){
            chromosone[i] = SpeciesEvo.bestCreatureOne.getChromosone()[i];
        }
        for(int i = 5; i <= 12; i++){
            chromosone[i] = SpeciesEvo.bestCreatureTwo.getChromosone()[i];
        }
        
        if(rnd.nextDouble() <= 0.2){
            chromosone[rnd.nextInt(12)] = rnd.nextDouble();
        }
    }
    
    public void display(Graphics g){
    g.setColor(new Color(255, 255, 255));
    g.fillOval (xLoc * SpeciesEvo.Display_OFFSET, yLoc * SpeciesEvo.Display_OFFSET, width, height);
  }

    public Double[] getChromosone() {
        return chromosone;
    }
    
    public Integer getxLoc() {
        return xLoc;
    }

    public void setxLoc(Integer xLoc) {
        this.xLoc = xLoc;
    }

    public Integer getyLoc() {
        return yLoc;
    }

    public void setyLoc(Integer yLoc) {
        this.yLoc = yLoc;
    }

    public Integer getEnergy() {
        return energy;
    }

    public void setEnergy(Integer energy) {
        this.energy = energy;
    }
    
    public Integer actionSelect(){
        
        if(this.getEnergy() <= 0){
            return 8;
        }
        if(SpeciesEvo.monsterLoc[this.getxLoc()][this.getyLoc()] == 1){
            return 8;
        }
        
        Double[] functions = new Double[6];
        Double[] weights = new Double[6];
        
        for (int i = 0; i <= 5; i++) {
            functions[i] = 0.0;
        }
        
        for (int i = 0; i <= 5; i++) {
            weights[i] = chromosone[i + 7];
        }
        
        for(int i = 0; i <= 5; i++){
            if(i == 0 && mushroomPresent()){
                functions[i] = 1.0;
            } else if(i == 1 && strawbPresent()){
                functions[i] = 1.0;
            } else if(i == 2 && (!nearest(Type.MUSHROOM).equals(Direction.NULL))){
                functions[i] = 1.0;
            } else if(i == 3 && (!nearest(Type.STRAWBERRY).equals(Direction.NULL))){
                functions[i] = 1.0;
            } else if(i == 4 && (!nearest(Type.CREATURE).equals(Direction.NULL))){
                functions[i] = 1.0;
            } else if(i == 5 &&(!nearest(Type.MONSTER).equals(Direction.NULL))){
                functions[i] = 1.0;
            }
        }
        
        Double test = 0.0;
        for (int i = 0; i <= 5; i++) {
            test += functions[i];
        }
        
        if(test == 0.0){
            return 7;
        }
        
        for (int i = 0; i <= 5; i++) {
            if(functions[i] == 1.0){
                functions[i] = weights[i];
            }
        }
        
        
        Integer index = 0;
        Double maxVal = 0.0;
        for (int i = 0; i <= 5; i++) {
            if(functions[i] > maxVal){
                index = i;
            }
        }
        return index;
    }
    
    public void actionResult(Integer index){
        
        if (index == 0) {
            if (chromosone[index] <= 0.5){
                eatMush();
            }
        }
        if (index == 1){
            if (chromosone[index] <= 0.5){
                eatStraw();
            }
        }
        
        if(index == 2){
            if (chromosone[index] <= 0.25){
                move(nearest(Type.MUSHROOM));
            }else if(chromosone[index] > 0.25 && chromosone[index] < 0.5){
                move(flipDirection(nearest(Type.MUSHROOM)));
            }else if(chromosone[index] > 0.5){
                moveRandom();
            }
        }
        
        if(index == 3){
            if (chromosone[index] <= 0.25){
                move(nearest(Type.STRAWBERRY));
            }else if((chromosone[index] > 0.25) && (chromosone[index] < 0.5)){
                move(flipDirection(nearest(Type.STRAWBERRY)));
            }else if(chromosone[index] > 0.5){
                moveRandom();
            }
        }
        
        if(index == 4){
            if (chromosone[index] <= 0.25){
                move(nearest(Type.CREATURE));
            }else if((chromosone[index] > 0.25) && (chromosone[index] < 0.5)){
                move(flipDirection(nearest(Type.CREATURE)));
            }else if(chromosone[index] > 0.5){
                moveRandom();
            }
        }
        
        if(index == 5){
            if (chromosone[index] <= 0.25){
                move(nearest(Type.MONSTER));
            }else if((chromosone[index] > 0.25) && (chromosone[index] < 0.5)){
                move(flipDirection(nearest(Type.MONSTER)));
            }else if(chromosone[index] > 0.5){
                moveRandom();
            }
        }
        
        if (index == 7) {
            if (chromosone[index] <= 0.2) {
                moveRandom();
            } else if ((chromosone[index] > 0.2) && (chromosone[index] <= 0.4)) {
                move(Direction.NORTH);
            } else if ((chromosone[index] > 0.4) && (chromosone[index] <= 0.6)) {
                move(Direction.EAST);
            } else if ((chromosone[index] > 0.6) && (chromosone[index] <= 0.8)) {
                move(Direction.SOUTH);
            } else {
                move(Direction.WEST);
            }
        }
        
        if (index == 8){
            kill();
        }
    }
    
    public boolean strawbPresent(){
        return SpeciesEvo.strawberryLoc[this.getxLoc()][this.getyLoc()] == 1;
    }
    
    public boolean mushroomPresent(){
        return SpeciesEvo.mushroomLoc[this.getxLoc()][this.getyLoc()] == 1;
    }
    
    public Direction nearest(Type type){
        Integer[][] searchArray = new Integer[SpeciesEvo.arraySize][SpeciesEvo.arraySize];
        switch (type) {
            case CREATURE:
                searchArray = SpeciesEvo.creatureLoc;
                break;
            case MONSTER:
                searchArray = SpeciesEvo.monsterLoc;
                break;
            case MUSHROOM:
                searchArray = SpeciesEvo.mushroomLoc;
                break;
            case STRAWBERRY:
                searchArray = SpeciesEvo.strawberryLoc;
                break;
            default:
                break;
        }
        
        
        for (int row = this.getxLoc() - 3; row <= this.getxLoc() + 3; row++) {
            for (int col = this.getyLoc() - 3; col <= this.getyLoc() + 3; col++) {
                if (this.getxLoc() >= 3 && this.getxLoc() <= SpeciesEvo.arraySize - 4) {
                    if (this.getyLoc() >= 3 && this.getyLoc() <= SpeciesEvo.arraySize - 4) {
                        if (searchArray[row][col] == 1) {
                            if (col == (this.getyLoc() + 1)) {
                                return Direction.NORTH;
                            } else if (col == (this.getyLoc() - 1)) {
                                return Direction.SOUTH;
                            } else if (row == (this.getxLoc() + 1)) {
                                return Direction.EAST;
                            } else if (row == (this.getxLoc() - 1)) {
                                return Direction.WEST;
                            }
                        }
                    }
                }
            }
        }
        return Direction.NULL;

    }
    
    public void move(Direction direction){
        
        SpeciesEvo.creatureLoc[this.getxLoc()][this.getyLoc()] = 0;
        
        switch (direction) {
            case NORTH:
                if(this.getyLoc() > 0){
                    this.setyLoc(this.getyLoc() - step);
                    SpeciesEvo.creatureLoc[this.getxLoc()][this.getyLoc()] = 1;
                }   break;
            case SOUTH:
                if(this.getyLoc() < SpeciesEvo.arraySize - 1){
                    this.setyLoc(this.getyLoc() + step);
                    SpeciesEvo.creatureLoc[this.getxLoc()][this.getyLoc()] = 1;
                }   break;
            case EAST:
                if(this.getxLoc() < SpeciesEvo.arraySize - 1){
                    this.setxLoc(this.getxLoc() + step);
                    SpeciesEvo.creatureLoc[this.getxLoc()][this.getyLoc()] = 1;
                }   break;
            case WEST:
                if(this.getxLoc() > 0){
                    this.setxLoc(this.getxLoc() - step);
                    SpeciesEvo.creatureLoc[this.getxLoc()][this.getyLoc()] = 1;
                }   break;
            default:
                break;
        }
    }
    
    public void moveRandom(){
        Random rnd = new Random();
        Double x = rnd.nextDouble();
        
        if (x < 0.25){
            move(Direction.NORTH);
        } else if ((x > 0.25) && (x < 0.5)) {
            move(Direction.SOUTH);
        } else if ((x > 0.5) && (x < 0.75)) {
            move(Direction.EAST);
        } else {
            move(Direction.WEST);
        }
    }
    
    public Direction flipDirection(Direction direction){
        switch (direction) {
            case NORTH:
                return Direction.SOUTH;
            case SOUTH:
                return Direction.NORTH;
            case EAST:
                return Direction.WEST;
            case WEST:
                return Direction.EAST;
            default:
                return Direction.NULL; 
        }
    }
    
    public void eatStraw(){
            if(this.getEnergy() + SpeciesEvo.ENERGY_RESTORE > SpeciesEvo.MAX_ENERGY){
                this.setEnergy(SpeciesEvo.MAX_ENERGY);
            } else {
                this.setEnergy(this.getEnergy() + SpeciesEvo.ENERGY_RESTORE);
            }
            SpeciesEvo.strawberryLoc[this.getxLoc()][this.getyLoc()] = 0;
    }
    
    public void eatMush(){
            SpeciesEvo.mushroomLoc[this.getxLoc()][this.getyLoc()] = 0;
            kill();
    }
    
    public void kill(){
        this.setEnergy(0);
        SpeciesEvo.creatureLoc[this.getxLoc()][this.getyLoc()] = 0;
        this.width = 0;
        this.height = 0;
    }
}
