package entities.creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import entities.Entity;
import gfx.Animation;
import gfx.AnimationSet;
import gfx.Assets;
import inventory.Inventory;
import realmcraft.Handler;
import realmcraft.tiles.Tile;
import states.State;

public class Player extends Creature {

    //animations
    private AnimationSet animSet;
    private Animation currentAnim;

    //inventory
    private Inventory inventory;

    public Player(Handler handler, float x, float y) {
        super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);

        //bounds
        bounds.x = 22;
        bounds.y = 37;
        bounds.width = 19;
        bounds.height = 19;

        //animation
        animSet = new AnimationSet(Assets.player, "humanoid");
        currentAnim = animSet.idleDownLeft;
        face = 'd';

        //inventory
        inventory = new Inventory(handler);

        maxHealth = 10;
        health = 10;
    }

    @Override
    public void tick() {
        //animations
        currentAnim.tick();
        //input
        getInput();
        //movment
        move();
        //attacks
        checkAttacks();
        handler.getGameCamera().centerOnEntity(this);
        //victory
        checkForVictory();
        //inventory
        inventory.tick();
        if (hitAnim.getCycles() > 0) hitAnim.tick();
    }

    @Override
    public void render(Graphics g) {

        g.drawImage(currentAnim.getCurrentFrame(), (int) (x - handler.getGameCamera().getxOffSet()),
        (int) (y - handler.getGameCamera().getyOffSet()), width, height, null);

        if (handler.getKeyManager().space && face == 'l') {
            g.drawImage(Assets.swordLeft, (int) (x - 20 - handler.getGameCamera().getxOffSet()),
            (int) (y - handler.getGameCamera().getyOffSet()), width, height, null);
        } else if (handler.getKeyManager().space && face == 'r') {
            g.drawImage(Assets.swordRight, (int) (x + 20 - handler.getGameCamera().getxOffSet()),
            (int) (y - handler.getGameCamera().getyOffSet()), width, height, null);
        } else if (handler.getKeyManager().space && face == 'd') {
            g.drawImage(Assets.swordDown, (int) (x - handler.getGameCamera().getxOffSet()),
            (int) (y + 20 - handler.getGameCamera().getyOffSet()), width, height, null);
        } else if (handler.getKeyManager().space && face == 'u') {
            g.drawImage(Assets.swordUp, (int) (x - handler.getGameCamera().getxOffSet()),
            (int) (y - 20 - handler.getGameCamera().getyOffSet()), width, height, null);
        } 
        renderHealthBar(g);

        inventory.render(g);

        if (hitAnim.getCycles() > 0) g.drawImage(hitAnim.getCurrentFrame(), (int) (x - handler.getGameCamera().getxOffSet()),
        (int) (y - handler.getGameCamera().getyOffSet()), width, height, null);

        //g.fillRect((int) (x + bounds.x - handler.getGameCamera().getxOffSet()), 
        //(int) (y + bounds.y - handler.getGameCamera().getyOffSet()), bounds.width, bounds.height);
    }

    public void postRender(Graphics g) {
        inventory.render(g);
    }

    @Override
    protected void checkAttacks() {
        //attack timers
        attackTimer += System.currentTimeMillis() - lastAttackTimer;
        lastAttackTimer = System.currentTimeMillis();
        if (attackTimer < attackCoolDown) return;

        //attack range
        Rectangle attackRange = new Rectangle();
        attackRange.width = DEFAULT_ATTACK_RANGE;
        attackRange.height = DEFAULT_ATTACK_RANGE;

        attackTimer = 0;

        if (handler.getKeyManager().space) {
            hitAnim.setCycles(1);
            switch (face) {
                case 'l': 
                attackRange.x = (int) (x + bounds.x - attackRange.width);
                attackRange.y = (int) (y + bounds.y);    
                break;
                case 'r':
                attackRange.x = (int) (x + bounds.x + attackRange.width);
                attackRange.y = (int) (y + bounds.y);
                break;
                case 'd':
                attackRange.x = (int) (x + bounds.x);
                attackRange.y = (int) (y + bounds.y + bounds.height);
                break;
                case 'u':
                attackRange.x = (int) (x + bounds.x);
                attackRange.y = (int) (y + bounds.y - attackRange.height);
                break;
            }
        }

        for (Entity e : handler.getWorld().getEntityManager().getEntities()) {
            if (e.equals(this)) continue;
            if (e.getCollisionBounds(0, 0).intersects(attackRange)) {
                e.hurt(1);
                return;
            }
        }
    }

    private void checkForVictory() {
        if (handler.getWorld().getTile((int) x / Tile.TILE_WIDTH, (int) y / Tile.TILE_HEIGHT).isVictoryTile()) {
            handler.getGame().victory();
        }
    }

    @Override
    public void die() {
        System.exit(1);
    }

    //sets the xMove and yMove variables to a value depending on which key is being pressed.
    private void getInput() {

    	xMove = 0;
        yMove = 0;

        if (handler.getKeyManager().up) {
            yMove = -DEFAULT_SPEED;    
            face = 'u';
        }
        if (handler.getKeyManager().down) {
            yMove = DEFAULT_SPEED;
            face = 'd';
        }
        if (handler.getKeyManager().left) {
            xMove = -DEFAULT_SPEED;
            face = 'l';
        }
        if (handler.getKeyManager().right) {
            xMove = DEFAULT_SPEED;
            face = 'r';
        }

        
        if (xMove == 0 && yMove > 0) currentAnim = animSet.movingDown;
        else if (xMove > 0 && yMove > 0) currentAnim = animSet.movingDownRight;
        else if (xMove > 0 && yMove == 0) currentAnim = animSet.movingRight;
        else if (xMove > 0 && yMove < 0) currentAnim = animSet.movingUpRight;
        else if (xMove == 0 && yMove < 0) currentAnim = animSet.movingUp;
        else if (xMove < 0 && yMove < 0) currentAnim = animSet.movingUpLeft;
        else if (xMove < 0 && yMove == 0) currentAnim = animSet.movingLeft;
        else if (xMove < 0 && yMove > 0) currentAnim = animSet.movingDownLeft;
        else if (xMove == 0 && yMove == 0) {
            if (currentAnim == animSet.movingDown) currentAnim = animSet.idleDown;
            else if (currentAnim == animSet.movingDownRight) currentAnim = animSet.idleDownRight;
            else if (currentAnim.equals(animSet.movingRight)) currentAnim = animSet.idleRight;
            else if (currentAnim.equals(animSet.movingUpRight)) currentAnim = animSet.idleUpRight;
            else if (currentAnim.equals(animSet.movingUp)) currentAnim = animSet.idleUp;
            else if (currentAnim.equals(animSet.idleUpLeft)) currentAnim = animSet.idleUpLeft;
            else if (currentAnim.equals(animSet.movingLeft)) currentAnim = animSet.idleLeft;
            else if (currentAnim.equals(animSet.idleDownLeft)) currentAnim = animSet.idleDownLeft;
        }
        
    }

    //getters setters

    public Inventory getInventory() {
        return inventory;
    }


    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
}