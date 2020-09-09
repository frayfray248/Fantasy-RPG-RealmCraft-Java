package entities.creatures.nonplayers;

import java.awt.Graphics;
import gfx.Animation;
import gfx.AnimationSet;
import gfx.Assets;
import realmcraft.Handler;

public class Monster extends NPC {

    //statics
    private final int MOVE_TIMER = 30;
    private final int IDLE_TIMER = 30;

    //name
    private String name;

    //animations
    private Animation currentAnim;
    private AnimationSet animSet;

    public Monster(Handler handler, String name, float x, float y, int width, int height) {
        super(handler, x, y, width, height);

        //name
        this.name = name;
    

        //bounds
        bounds.x = 22;
        bounds.y = 37;
        bounds.width = 19;
        bounds.height = 19;

        animSet = new AnimationSet(Assets.zombie, "humanoid");
        currentAnim = animSet.idleDownRight;
        speed = speed / 4;
    }

	@Override
    public void tick() {
        //animations
        currentAnim.tick();
        //test code for movement
        // getMove();
        if (entityWithinRange(handler.getWorld().getEntityManager().getPlayer())) {
            attackEntity(handler.getWorld().getEntityManager().getPlayer());
        }
        else  {
            patrol();
        }
        move();
        moveAnimation();
        checkAttacks();
        
        xMove = 0;
        yMove = 0;
        attacking = false;
    }

    @Override
    public void render(Graphics g) {

        //drawing sprite
        g.drawImage(currentAnim.getCurrentFrame(), (int) (x - handler.getGameCamera().getxOffSet()),
        (int) (y - handler.getGameCamera().getyOffSet()), width, height, null);

        //drawing sword
        if (attacking && face == 'l') {
            g.drawImage(Assets.swordLeft, (int) (x - 20 - handler.getGameCamera().getxOffSet()),
            (int) (y - handler.getGameCamera().getyOffSet()), width, height, null);
        } else if (attacking && face == 'r') {
            g.drawImage(Assets.swordRight, (int) (x + 20 - handler.getGameCamera().getxOffSet()),
            (int) (y - handler.getGameCamera().getyOffSet()), width, height, null);
        } else if (attacking && face == 'd') {
            g.drawImage(Assets.swordDown, (int) (x - handler.getGameCamera().getxOffSet()),
            (int) (y + 20 - handler.getGameCamera().getyOffSet()), width, height, null);
        } else if (attacking && face == 'u') {
            g.drawImage(Assets.swordUp, (int) (x - handler.getGameCamera().getxOffSet()),
            (int) (y - 20 - handler.getGameCamera().getyOffSet()), width, height, null);
        } 
    }

    @Override
    public void die() {
        
    }

    private void moveAnimation() {
        // moveCounter++;
        if (xMove > 0 && yMove == 0) face = 'r';
        else if (xMove < 0 && yMove == 0) face = 'l';
        else if (yMove > 0 && xMove == 0) face = 'd';
        else if (yMove < 0 && xMove == 0) face = 'u';
        
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
}