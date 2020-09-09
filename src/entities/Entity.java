package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import realmcraft.Handler;
import realmcraft.tiles.Tile;

/* abstract class for every non-tile thing */

public abstract class Entity {

    //statics
    public static final int DEFAULT_HEALTH = 3;

    //class
    protected Handler handler;
    protected float x, y;
    protected int width, height;
    protected boolean active = true;
    protected Rectangle bounds;
    protected int maxHealth;
    protected int health;
    protected Rectangle healthBar;

    //CONSTRUCTOR
    public Entity(Handler handler, float x, float y, int width, int height) {
        this.handler = handler;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        maxHealth = DEFAULT_HEALTH;
        health = maxHealth;

        bounds = new Rectangle(0, 0, width, height);
    }

    public abstract void tick();

    public abstract void render(Graphics g);

    public abstract void die();

    public void hurt(int amt) {
        health -= amt;
        if (health <= 0) {
            active = false;
            die();
        }
        
    }

    //////////////////////////////////
    ///////////COLLISIONS/////////////
    //////////////////////////////////

    public boolean checkEntityCollisions(float xOffset, float yOffset) {
        for(Entity e : handler.getWorld().getEntityManager().getEntities()) {
            if(e.equals(this)) continue;
            if(e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset))) {
                return true;
            }
        }
        return false;
    }

    public Rectangle getCollisionBounds(float xOffset, float yOffset) {
        return new Rectangle((int) (x + bounds.x + xOffset), (int) (y + bounds.y + yOffset),
            bounds.width, bounds.height);
    }


    //health bar rendering
    //horendous! Fix it!
    protected void renderHealthBar(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect((int) (x - handler.getGameCamera().getxOffSet()) - 2 , 
                    (int) (y - handler.getGameCamera().getyOffSet()) - 2, 
                    Tile.TILE_WIDTH + 4, (Tile.TILE_HEIGHT / 8) + 4);
        g.setColor(Color.GREEN);
        if (health >= maxHealth) {
            g.fillRect((int) (x - handler.getGameCamera().getxOffSet()), 
                    (int) (y - handler.getGameCamera().getyOffSet()), 
                    Tile.TILE_WIDTH, Tile.TILE_HEIGHT / 8);
        }
        else {
            g.setColor(Color.RED);
            g.fillRect((int) (x - handler.getGameCamera().getxOffSet()), 
                    (int) (y - handler.getGameCamera().getyOffSet()), 
                    Tile.TILE_WIDTH, 
                    Tile.TILE_HEIGHT / 8);
            float damage = (float) (maxHealth) - (float) (health);
            float healthPerc = damage / (float) (maxHealth);
            float rec = (float) (Tile.TILE_WIDTH) * healthPerc;
            g.setColor(Color.GREEN);
            g.fillRect((int) (x - handler.getGameCamera().getxOffSet()), 
                    (int) (y - handler.getGameCamera().getyOffSet()), 
                    Tile.TILE_WIDTH - (int) (rec), 
                    Tile.TILE_HEIGHT / 8);
                    System.out.println("rec = " + rec);
        }
        
        
        
    }

    //GETTERS SETTERS

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}