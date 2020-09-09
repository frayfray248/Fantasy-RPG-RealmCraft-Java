package realmcraft.worlds;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import realmcraft.Handler;
import states.GameState;
import states.State;

public class WayPoint {

    //worlds
    private int worldID;

    //ready
    private boolean ready;

    //pos and dimensions
    private int x;
    private int y;
    private Rectangle r;

    //handler
    Handler h;

    public WayPoint(Handler h, int worldID, int x, int y, int width, int height) {
        this.worldID = worldID;
        this.h = h;
        this.x = x;
        this.y = y;
        ready = true;
        r = new Rectangle(x, y, width, height);
    }

    public void tick() {
        if (r.contains(h.getWorld().getEntityManager().getPlayer().getX(),
                       h.getWorld().getEntityManager().getPlayer().getY())
                       && ready) {
                           System.out.println("you are in waypoint");
                           
                       h.setWorld(h.getWorldStorage().getWorld(worldID));
                       }
    }

    public void render(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect((int) (x) - (int) (h.getGameCamera().getxOffSet()), (int) (y) - (int) (h.getGameCamera().getyOffSet()), (int) (r.getWidth()), (int) (r.getHeight()));
    }
}