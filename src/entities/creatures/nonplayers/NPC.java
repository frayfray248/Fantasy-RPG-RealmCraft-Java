package entities.creatures.nonplayers;

import java.util.Random;

import entities.Entity;
import entities.creatures.Creature;
import realmcraft.Handler;

public abstract class NPC extends Creature {

    //behavior vars
    protected int moveCounter = 0;
    protected int directionNum = 0;

    //random
    protected Random rand = new Random();

    public NPC (Handler handler, float x, float y, int width, int height) {
        super(handler, x, y, width, height);
    };

    protected boolean entityWithinRange(Entity e) {
        if (Math.abs(x - e.getX()) < sight && 
        Math.abs(y - e.getY()) < sight) {
            return true;
        }
        return false;
    }


    //////////////////////////////////
    ///////////BEHAVIOR METHODS///////
    //////////////////////////////////

    protected void followEntity(Entity e) {
        xMove = 0;
        yMove = 0;
        if ( x < e.getX()) xMove = speed;
        else if ( x > e.getX()) xMove -= speed;

        if ( y < e.getY()) yMove = speed;
        else if ( y > e.getY()) yMove -= speed;
    }

    protected void attackEntity(Entity e) {
        attacking = true;
        followEntity(e);
    }

    protected void patrol() {
        xMove = 0;
        yMove = 0;
        
        if (moveCounter > 60) {
            moveCounter = 0;
            directionNum = rand.nextInt(5);
        }

         switch (directionNum) {
            case 0: xMove = speed;
            break;
            case 1: xMove -= speed;
            break;
            case 2: yMove = speed;
            break;
            case 3: yMove = speed;
            break;
            case 4: break;
        }
        moveCounter++;
    }

}
