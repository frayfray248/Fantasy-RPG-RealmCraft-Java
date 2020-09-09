package entities.statics;

import java.awt.Graphics;

import gfx.Assets;
import items.Item;
import realmcraft.Handler;
import realmcraft.tiles.Tile;

public class Boulder extends StaticEntity {

    public Boulder(Handler handler, float x, float y) {
        super(handler, x, y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);

        bounds.x = 10;
        bounds.y = (int) (height / 1.5f);
        bounds.width = width - 20;
        bounds.height = (int) (height - height / 1.5);
        health = 1;

    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.boulder, (int) (x - handler.getGameCamera().getxOffSet()), 
        (int) (y - handler.getGameCamera().getyOffSet()), width, height, null);

        //g.fillRect((int) (x + bounds.x - handler.getGameCamera().getxOffSet()), 
        //(int) (y + bounds.y - handler.getGameCamera().getyOffSet()), bounds.width, bounds.height);
    }

    @Override
    public void die() {
        handler.getWorld().getItemManager().addItem(Item.stoneItem.createNew((int) x, (int) y));
    }

}
