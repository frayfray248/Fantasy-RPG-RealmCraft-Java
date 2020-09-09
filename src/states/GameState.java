package states;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import realmcraft.Game;
import realmcraft.Handler;
import realmcraft.worlds.World;

public class GameState extends State {

    //statics
    
    private World world;

    
    public GameState(Handler handler) {
        super(handler);
    }

    @Override
    public void tick() {
        if (!handler.getWorld().equals(world)) world = handler.getWorld();
        world.tick();
        
    }

    @Override
    public void render(Graphics g) {
        world.render(g);
    }
}