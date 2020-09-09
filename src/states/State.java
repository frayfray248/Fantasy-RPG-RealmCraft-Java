package states;

import java.awt.Graphics;
import realmcraft.Game;
import realmcraft.Handler;

public abstract class State {

    //STATICS
    private static State currentState = null;

    public static void setState(State state) {
        currentState = state;
    }

    public static State getState() {
        return currentState;
    }
    //END OF STATICS

    //handler
    protected Handler handler;
    
    public State(Handler handler) {
        this.handler = handler;
    }

    public abstract void tick();
    public abstract void render(Graphics g);
}