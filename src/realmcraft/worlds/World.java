package realmcraft.worlds;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import entities.EntityManager;
import entities.creatures.Player;
import entities.creatures.nonplayers.Zombie;
import entities.statics.Boulder;
import entities.statics.Tree;
import items.ItemManager;
import realmcraft.Handler;
import realmcraft.tiles.Tile;
import realmcraft.utils.Utils;
import states.GameState;
import states.State;

public class World {

    private Handler handler;
    private int widthT;
    private int heightT;
    private int spawnX, spawnY; //tile coord
    private int[][] mapTiles;
    //entities
    private EntityManager entityManager;
    //items
    private ItemManager itemManager;

    //waypoints
    private WayPoint wp;

    //CONSTRUCTOR
    public World(Handler handler, String path) {
        this.handler = handler;
        
        entityManager = new EntityManager(handler, new Player(handler, 64, 64));
        itemManager = new ItemManager(handler);
        loadWorld(path);

         
        
        wp = new WayPoint(this.handler, 1, 19*64, 3*64, 1*64, 2*64);

        entityManager.getPlayer().setX(spawnX * Tile.TILE_WIDTH);
        entityManager.getPlayer().setY(spawnY * Tile.TILE_HEIGHT);

        entityManager.addEntity(new Tree(handler, 128, 128));
        entityManager.addEntity(new Tree(handler, 128, 228));
        entityManager.addEntity(new Tree(handler, 128, 328));
        entityManager.addEntity(new Boulder(handler, 6 * 64, 6 * 64));
        
        entityManager.addEntity(new Zombie(handler, 192, 192, 64, 64));
        entityManager.addEntity(new Zombie(handler, 192, 292, 64, 64));
        entityManager.addEntity(new Zombie(handler, 192, 392, 64, 64));
        entityManager.addEntity(new Zombie(handler, 192, 492, 64, 64));
        entityManager.addEntity(new Zombie(handler, 192, 592, 64, 64));

        
    }

    public void tick() {
        itemManager.tick();
        entityManager.tick();
        wp.tick();
    }

    public void render(Graphics g) {
        int xStart = (int) Math.max(0, handler.getGameCamera().getxOffSet() / Tile.TILE_WIDTH);
		int xEnd = (int) Math.min(widthT, (handler.getGameCamera().getxOffSet() + handler.getWidth()) / Tile.TILE_WIDTH + 1);
		int yStart = (int) Math.max(0, handler.getGameCamera().getyOffSet() / Tile.TILE_HEIGHT);
		int yEnd = (int) Math.min(heightT, (handler.getGameCamera().getyOffSet() + handler.getHeight()) / Tile.TILE_HEIGHT + 1);
		
		for(int y = yStart;y < yEnd;y++){
			for(int x = xStart;x < xEnd;x++){
				getTile(x, y).render(g, (int) (x * Tile.TILE_WIDTH - handler.getGameCamera().getxOffSet()),
						(int) (y * Tile.TILE_HEIGHT - handler.getGameCamera().getyOffSet()));
			}
        }
        //items
        itemManager.render(g);
        //entities
        entityManager.render(g);
        //wp.render(g);
    }

    public Tile getTile(int x, int y) {
        
        //default if invalid xy
        if (x < 0 || y < 0 || x >= widthT || y >= heightT) return Tile.grassTile;

        Tile t = Tile.tiles[mapTiles[x][y]];

        //default if null
        if(t == null) return Tile.dirtTile;

        return t;
    }

    public void loadWorld(String path) {
        String file = Utils.loadFileAsString(path);
        String[] tokens = file.split("\\s+");
        widthT = Utils.parseInt(tokens[0]);
        heightT = Utils.parseInt(tokens[1]);
        spawnX = Utils.parseInt(tokens[2]);
        spawnY = Utils.parseInt(tokens[3]);

        mapTiles = new int[widthT][heightT];
        for (int y = 0; y < heightT; y++) {
            for (int x = 0; x < widthT; x++) {
                mapTiles[x][y] = Utils.parseInt(tokens[(x + y * widthT + 4)]);
            }
        }
    }

    //GETTERS SETTERS

    public int getWidth() {
        return widthT;
    }

    public int getHeight() {
        return heightT;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

   
    public Handler getHandler() {
        return handler;
    }

 
    public void setHandler(Handler handler) {
        this.handler = handler;
    }

 
    public ItemManager getItemManager() {
        return itemManager;
    }


    public void setItemManager(ItemManager itemManager) {
        this.itemManager = itemManager;
    }
}