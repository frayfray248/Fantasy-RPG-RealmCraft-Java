package realmcraft.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import realmcraft.Game;

public class Tile {
    //STATICS

    public static final int TILE_WIDTH = 32 * Game.ZOOM_FACTOR, TILE_HEIGHT = 32 * Game.ZOOM_FACTOR;

    //creating, storing, and given ids to all tiles
    public static Tile[] tiles = new Tile[256];
    public static Tile grassTile = new GrassTile(0);
    public static Tile dirtTile = new DirtTile(1);
    public static Tile rockTile = new RockTile(2);
    public static Tile victoryTile = new VictoryTile(3);


    //END OF STATICS

    protected BufferedImage texture;
    protected final int id;

    //CONSTRUCTOR
    public Tile(BufferedImage texture, int id) {
        this.texture = texture;
        this.id = id;

        tiles[id] = this;
    }

    public boolean isSolid() {
        return false;
    }

    public boolean isVictoryTile() {
        return false;
    }

    public void tick() {

    }

    public void render(Graphics g, int x, int y) {
        g.drawImage(texture, x, y, TILE_WIDTH, TILE_HEIGHT, null);
    }

    //GETTERS SETTERS

    public int getId() {
        return id;
    }

}