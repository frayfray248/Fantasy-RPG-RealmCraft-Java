package gfx;

import java.awt.image.BufferedImage;
import java.util.Arrays;

public class AnimationSet {

    public Animation idleUp, idleUpRight, idleRight, idleDownRight, idleDown, idleDownLeft, idleLeft, idleUpLeft;
    public Animation movingUp, movingUpRight, movingRight, movingDownRight, movingDown, movingDownLeft, movingLeft, movingUpLeft;

    public AnimationSet(BufferedImage[][] images, String type) {
        if (type.equals("humanoid")) initHumanoid(images);
    }

    private void initHumanoid(BufferedImage[][] images) {
        idleDown = new Animation(Animation.IDLE_SPEED, Arrays.copyOfRange(images[0], 0, 2));
        movingDown = new Animation(Animation.MOVE_SPEED, Arrays.copyOfRange(images[0], 2, 7));
        idleDownRight = new Animation(Animation.IDLE_SPEED, Arrays.copyOfRange(images[1], 0, 2));
        movingDownRight = new Animation(Animation.MOVE_SPEED, Arrays.copyOfRange(images[1], 2, 7));
        idleRight = new Animation(Animation.IDLE_SPEED, Arrays.copyOfRange(images[2], 0, 2));
        movingRight = new Animation(Animation.MOVE_SPEED, Arrays.copyOfRange(images[2], 2, 7));
        idleUpRight = new Animation(Animation.IDLE_SPEED, Arrays.copyOfRange(images[3], 0, 2));
        movingUpRight = new Animation(Animation.MOVE_SPEED, Arrays.copyOfRange(images[3], 2, 7));
        idleUp = new Animation(Animation.IDLE_SPEED, Arrays.copyOfRange(images[4], 0, 2));
        movingUp = new Animation(Animation.MOVE_SPEED, Arrays.copyOfRange(images[4], 2, 7));
        idleUpLeft = new Animation(Animation.IDLE_SPEED, Arrays.copyOfRange(images[5], 0, 2));
        movingUpLeft = new Animation(Animation.MOVE_SPEED, Arrays.copyOfRange(images[5], 2, 7));
        idleLeft = new Animation(Animation.IDLE_SPEED, Arrays.copyOfRange(images[6], 0, 2));
        movingLeft = new Animation(Animation.MOVE_SPEED, Arrays.copyOfRange(images[6], 2, 7));
        idleDownLeft = new Animation(Animation.IDLE_SPEED, Arrays.copyOfRange(images[7], 0, 2));
        movingDownLeft = new Animation(Animation.MOVE_SPEED, Arrays.copyOfRange(images[7], 2, 7));
    }
}