package assignment22;

import assignment22.SpeciesEvo.Direction;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

/**
 *
 * @author Cassidy Mowat
 */
public class Monster {
    
    private Integer xLoc;
    private Integer yLoc;
    
    private int width = SpeciesEvo.SIZE;
    private int height = SpeciesEvo.SIZE;
    
    private int step = 1;

    public Monster() {
        Random rnd = new Random();
        this.xLoc = (rnd.nextInt(SpeciesEvo.arraySize) + 1) - 1;
        this.yLoc = (rnd.nextInt(SpeciesEvo.arraySize) + 1) - 1;
        
        while(SpeciesEvo.creatureLoc[this.xLoc][this.yLoc] == 1){
            this.xLoc = (rnd.nextInt(SpeciesEvo.arraySize) + 1) - 1;
            this.yLoc = (rnd.nextInt(SpeciesEvo.arraySize) + 1) - 1;
        }
    }    
    
    public Monster(Integer xLoc, Integer yLoc) {
        this.xLoc = xLoc;
        this.yLoc = yLoc;
    }

    public void display(Graphics g){
    g.setColor(new Color(0, 0, 0));
    g.fillOval (xLoc * SpeciesEvo.Display_OFFSET, yLoc * SpeciesEvo.Display_OFFSET, width, height);
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
    
    public void performAction(){
        if(nearestCreature() != Direction.NULL){
            move(nearestCreature());
        } else {
            moveRandom();
        }
    }
    
    public void move(Direction direction){
        
        SpeciesEvo.monsterLoc[this.getxLoc()][this.getyLoc()] = 0;
        
        switch (direction) {
            case NORTH:
                if(this.getyLoc() > 0){
                    this.setyLoc(this.getyLoc() - step);
                    SpeciesEvo.monsterLoc[this.getxLoc()][this.getyLoc()] = 1;
                }   break;
            case SOUTH:
                if(this.getyLoc() < SpeciesEvo.arraySize - 1){
                    this.setyLoc(this.getyLoc() + step);
                    SpeciesEvo.monsterLoc[this.getxLoc()][this.getyLoc()] = 1;
                }   break;
            case EAST:
                if(this.getxLoc() < SpeciesEvo.arraySize - 1){
                    this.setxLoc(this.getxLoc() + step);
                    SpeciesEvo.monsterLoc[this.getxLoc()][this.getyLoc()] = 1;
                }   break;
            case WEST:
                if(this.getxLoc() > 0){
                    this.setxLoc(this.getxLoc() - step);
                    SpeciesEvo.monsterLoc[this.getxLoc()][this.getyLoc()] = 1;
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
    
    public Direction nearestCreature(){
        
        for (int row = this.getxLoc() - 3; row <= this.getxLoc() + 3; row++) {
            for (int col = this.getyLoc() - 3; col <= this.getyLoc() + 3; col++) {
                if (this.getxLoc() >= 3 && this.getxLoc() <= SpeciesEvo.arraySize - 4) {
                    if (this.getyLoc() >= 3 && this.getyLoc() <= SpeciesEvo.arraySize - 4) {
                        if (SpeciesEvo.creatureLoc[row][col] == 1) {
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
    
}
