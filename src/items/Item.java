package items;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import gfx.Assets;
import realmcraft.Handler;

public class Item {

	//statics

	public static final int ITEMWIDTH = 64, ITEMHEIGHT = 64;

	//handler

	public static Item[] items = new Item[256];
	public static Item woodItem = new Item(Assets.wood, "Wood", 0);
	public static Item stoneItem = new Item(Assets.stone, "Stone", 1);

	//class

	protected BufferedImage texture;
	protected String name;
	protected int id;
	protected Handler handler;

	protected Rectangle bounds;

	protected int x, y, count;
	protected boolean pickedUp = false;

	public Item(BufferedImage texture, String name, int id) {
		this.texture = texture;
		this.name = name;
		this.id = id;
		count = 1;
		

		bounds = new Rectangle(x, y, ITEMHEIGHT, ITEMWIDTH);

		items[id] = this;
	}

	public void tick() {
		if (handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0f, 0f).intersects(bounds)) {
			pickedUp = true;
			handler.getWorld().getEntityManager().getPlayer().getInventory().addItem(this);
		}
	}

	public void render(Graphics g) {
		if (handler == null) return;
		render(g, (int) (x - handler.getGameCamera().getxOffSet()), 
				  (int) (y - handler.getGameCamera().getyOffSet()));
	}

	public void render(Graphics g, int x, int y) {
		g.drawImage(texture, x, y, ITEMWIDTH, ITEMHEIGHT, null);
	}

	public Item createNew(int count) {
		Item i = new Item(texture, name, id);
		i.setPickedUp(true);
		i.setCount(count);
		return i;
	}

	public Item createNew(int x, int y) {
		Item i = new Item(texture, name, id);
		i.setPosition(x, y);
		return i;
	}

	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
		bounds.x = x;
		bounds.y = y;
	}

	//getters setters

	public BufferedImage getTexture() {
		return texture;
	}

	public void setTexture(BufferedImage texture) {
		this.texture = texture;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * @return the handler
	 */
	public Handler getHandler() {
		return handler;
	}
	
	/**
	 * @param handler the handler to set
	 */
	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	/**
	 * @return the pickedUp
	 */
	public boolean isPickedUp() {
		return pickedUp;
	}

	/**
	 * @param pickedUp the pickedUp to set
	 */
	public void setPickedUp(boolean pickedUp) {
		this.pickedUp = pickedUp;
	}

}