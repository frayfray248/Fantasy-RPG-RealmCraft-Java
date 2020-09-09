package entities.creatures;

import java.awt.Graphics;
import java.awt.Rectangle;

import entities.Entity;
import gfx.Animation;
import gfx.Assets;
import realmcraft.Game;
import realmcraft.Handler;
import realmcraft.tiles.Tile;

public abstract class Creature extends Entity {

    //STATICS
    public static final float DEFAULT_SPEED = 4f * (Game.ZOOM_FACTOR - 1);
    public static final int DEFAULT_CREATURE_WIDTH = Tile.TILE_WIDTH;
    public static final int DEFAULT_CREATURE_HEIGHT = Tile.TILE_HEIGHT;
    public static final int DEFAULT_ATTACK_RANGE = 20;
    public static final int DEFAULT_SIGHT = 3 * 64;

    //CLASS
    protected float speed;
    protected float xMove, yMove;
    protected int atkRange;
    protected boolean attacking = false;
    protected int sight;
    protected Animation hitAnim;

    //FACING
    protected char face;
    
    //ATTACKS
    protected long lastAttackTimer, attackCoolDown = 1000, attackTimer = attackCoolDown;

    public Creature(Handler handler, float x, float y, int width, int height) {
        super(handler, x, y, width, height);
        hitAnim = new Animation(100, Assets.hitEffect);
        sight = DEFAULT_SIGHT;
        speed = DEFAULT_SPEED;
        xMove = 0;
        yMove = 0;
    }

    protected void checkAttacks() {
        //ATTACK TIMERS
        attackTimer += System.currentTimeMillis() - lastAttackTimer;
        lastAttackTimer = System.currentTimeMillis();
        if (attackTimer < attackCoolDown) return;

        //CREATING ATTACK RANGE (RECTANGLE)
        Rectangle attackRange = new Rectangle();
        attackRange.width = DEFAULT_ATTACK_RANGE;
        attackRange.height = DEFAULT_ATTACK_RANGE;

        //RESETTING TIMER
        attackTimer = 0;

        //adjusting attack range
        if (attacking) {
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

        //search for entity within attack range
        for (Entity e : handler.getWorld().getEntityManager().getEntities()) {
            if (e.equals(this)) continue;
            if (e.getCollisionBounds(0, 0).intersects(attackRange)) {
                e.hurt(1); // damage
                return;
            }
        }
    }

    protected void renderHitEffect(Graphics g) {
        g.drawImage(hitAnim.getCurrentFrame(), (int) (x - handler.getGameCamera().getxOffSet()),
        (int) (y - handler.getGameCamera().getyOffSet()), width, height, null);
    }

    //////////////////////////////////
    ///////////MOVEMENT///////////////
    //////////////////////////////////

    public void move() {
        if (!checkEntityCollisions(xMove, 0f)) moveX();
        if (!checkEntityCollisions(0f, yMove)) moveY();
    }

    private void moveX() {
        if(xMove > 0){//Moving right
            //tile coord of the tile being moved into to the right
            int tx = (int) (x + xMove + bounds.x + bounds.width) / Tile.TILE_WIDTH;
            
            //bounds right side collision detection
			if(!collisionWithTile(tx, (int) (y + bounds.y) / Tile.TILE_HEIGHT) &&
					!collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILE_HEIGHT)){
				x += xMove;
			}else{
				x = tx * Tile.TILE_WIDTH - bounds.x - bounds.width - 1;
			}
			
        }else if(xMove < 0){//Moving left
            //tile coord of the tile being moved into to the left
			int tx = (int) (x + xMove + bounds.x) / Tile.TILE_WIDTH;
            
            //bounds left side collision detection
			if(!collisionWithTile(tx, (int) (y + bounds.y) / Tile.TILE_HEIGHT) &&
					!collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILE_HEIGHT)){
				x += xMove;
			}else{
				x = tx * Tile.TILE_WIDTH + Tile.TILE_WIDTH - bounds.x;
			}
			
		}
    }

    private void moveY() {
        if(yMove < 0){//Up
            //tile coord of the tile being moved into above
			int ty = (int) (y + yMove + bounds.y) / Tile.TILE_HEIGHT;
            
            //bounds up side collision detection
			if(!collisionWithTile((int) (x + bounds.x) / Tile.TILE_WIDTH, ty) &&
					!collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILE_WIDTH, ty)){
				y += yMove;
			}else{
				y = ty * Tile.TILE_HEIGHT + Tile.TILE_HEIGHT - bounds.y;
			}
			
        }else if(yMove > 0){//Down
            //tile coord of the tile being moved into below
			int ty = (int) (y + yMove + bounds.y + bounds.height) / Tile.TILE_HEIGHT;
            
            //bounds down side collision detection
			if(!collisionWithTile((int) (x + bounds.x) / Tile.TILE_WIDTH, ty) &&
					!collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILE_WIDTH, ty)){
				y += yMove;
			}else{
				y = ty * Tile.TILE_HEIGHT - bounds.y - bounds.height - 1;
			}
			
		}
    }

    //tile collisions
    protected boolean collisionWithTile(int x, int y) {
        return handler.getWorld().getTile(x, y).isSolid();
    }
}