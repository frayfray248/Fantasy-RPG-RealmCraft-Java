package gfx;

import java.awt.image.BufferedImage;

public class Animation {

    //statics
    static final int IDLE_SPEED = 500;
    static final int MOVE_SPEED = 100;


    private int speed, index;
    private long lastTime, timer;
    private BufferedImage[] frames;
    private int cycles;

    public Animation(int speed, BufferedImage[] frames) {
        this.speed = speed;
        this.frames = frames;
        index = 0;
        timer = 0;
        cycles = 0;
        lastTime = System.currentTimeMillis();
    }

    public void tick() {
        timer += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();

        if (timer > speed) {
            index++;
            timer = 0;
            if(index >= frames.length) {
                index = 0;
                if (cycles > 0) cycles--;
            }
        }
    }

    public BufferedImage getCurrentFrame() {
        return frames[index];
    }

    public void reset() {
        index = 0;
    }

    //getters setters

    public void setCycles(int cycles) {
        this.cycles = cycles;
    }

    public int getCycles() {
        return cycles;
    }
}
