package states;

import java.awt.Graphics;

import gfx.Assets;
import realmcraft.Game;
import realmcraft.Handler;
import realmcraft.worlds.World;

public class VictoryState extends State {
    
    public VictoryState(Handler handler) {
        super(handler);
    }

    @Override
    public void tick() {
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.victoryScreen, 0, 0, null);
    }
}