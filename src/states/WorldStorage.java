package states;

import realmcraft.Handler;
import realmcraft.worlds.World;

public class WorldStorage {

    private World[] worlds;
    private Handler h;

    public WorldStorage(Handler h) {
        this.h = h;
        worlds = new World[2];

        worlds[0] = new World(h, "res/worlds/world1.txt");
        worlds[1] = new World(h, "res/worlds/world2.txt");
    }

    public World getWorld(int index) {
        return worlds[index];
    }
}