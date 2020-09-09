package inventory;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import gfx.Assets;
import gfx.Text;
import items.Item;
import realmcraft.Game;
import realmcraft.Handler;
import realmcraft.tiles.Tile;

public class Inventory {

    //finals
    final int invenEdgeToFirstSlotX = 29 * Game.ZOOM_FACTOR;
    final int invenEdgeToFirstSlotY = 93 * Game.ZOOM_FACTOR;
    final int SLOT_SIZE = 34 * Game.ZOOM_FACTOR;

    //class
    private Handler handler;
    private boolean active = false;
    private Item[][] inventoryItems;
    private Item selectedItem;
    private Item clickAndDragItem;
    private int width;
    private int height;
    private int invSelectedItemNameXpos;
    private int invSelectedItemNameYpos;
    private int firstSlotX;
    private int firstSlotY;
    private int rows = 5;
    private int columns = 4;
    private int x;
    private int y;

    public Inventory(Handler handler) {
        this.handler = handler;
        //inventory arr list
        inventoryItems = new Item[rows][columns];
        //measurements
        width = Assets.inventoryScreen.getWidth() * Game.ZOOM_FACTOR;
        height = Assets.inventoryScreen.getHeight() * Game.ZOOM_FACTOR;
        firstSlotX = ((handler.getWidth() - width) / 2) + invenEdgeToFirstSlotX;
        firstSlotY = ((handler.getHeight() - height) / 2) + invenEdgeToFirstSlotY;
        //selected item name pos
        invSelectedItemNameXpos = x + (width / 2);
        invSelectedItemNameYpos = y + (height / 5);
        //inventory screen pos
        x = (handler.getWidth() - width) / 2;
        y = (handler.getHeight() - width) / 2;

        //starting items (tests)
        addItem(Item.woodItem.createNew(5));
    }

    public void tick() {
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_E)) active = !active;
        if (!active) return;
        updateSelectedItem();
        updateClickAndDrag();
    }

    public void render(Graphics g) {

        //checking for active
        if(!active) return;
        
        //drawing inventory screen
        else g.drawImage(Assets.inventoryScreen, x, y, width, height, null);
        
        //drawing selected item text
        if (selectedItem != null) Text.drawString(g, selectedItem.getName(), 
                                                  invSelectedItemNameXpos, 
                                                  invSelectedItemNameYpos, 
                                                  false, Color.WHITE, Assets.font28);
        
        //drawing inventory items to inventory grid
        for(int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (inventoryItems[i][j] != null) {
                    //item image
                    g.drawImage(inventoryItems[i][j].getTexture(), 
                    firstSlotX + (j * SLOT_SIZE), 
                    firstSlotY + (i * SLOT_SIZE), 
                    SLOT_SIZE, SLOT_SIZE, null); 
                    //item count number
                    Text.drawString(g, "" + inventoryItems[i][j].getCount(), 
                    firstSlotX + (j * SLOT_SIZE) + 10, 
                    firstSlotY + (i * SLOT_SIZE) + 12, 
                    true, Color.BLACK, Assets.font28);
                }
            }  
        }  
        
        //drawing clickAndDrag item
        if (clickAndDragItem != null)  {
            g.drawImage(clickAndDragItem.getTexture(), 
            handler.getMouseManager().getMouseDragX(),                                         
            handler.getMouseManager().getMouseDragY(), 
            SLOT_SIZE, SLOT_SIZE, null);
        }
    }

    //inventory methods
    
    public void addItem(Item item) {
        for(int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                //adding an item if slot is empty
                if(inventoryItems[i][j] == null) {
                    inventoryItems[i][j] = item;
                    return;
                }
                //adding item count to exisitng item
                else if(inventoryItems[i][j] != null && inventoryItems[i][j].getId() == item.getId()) {
                    inventoryItems[i][j].setCount(inventoryItems[i][j].getCount() + item.getCount());
                    return;
                }
            } 
        }
    }

    public void dropItem(Item item) {
        //setting item status and pos
        item.setPickedUp(false);
        item.setPosition((int) handler.getWorld().getEntityManager().getPlayer().getX() + Tile.TILE_WIDTH,
        (int) handler.getWorld().getEntityManager().getPlayer().getY());
        //adding it the the world's item manager
        handler.getWorld().getItemManager().addItem(item);

        //removes item from inventory
        for(int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if(inventoryItems[i][j] != null && inventoryItems[i][j].equals(item)) {
                    inventoryItems[i][j] = null;
                    return;
                }
            }
        }
    }

    //method for updating the display of the selected item. If the user clicks on a different inventory grid image,
    //then the selected item will change to that item.
    public void updateSelectedItem() {
        if (!handler.getMouseManager().isLeftPressed()) {
            return;
        }
        Rectangle itemBounds;
        Point mousePoint = new Point(handler.getMouseManager().getMouseX(),
        handler.getMouseManager().getMouseY());
        for(int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (inventoryItems[i][j] == null) continue;
                else {
                    itemBounds = new Rectangle(126 + (j * 68), 190 + (i * 68), 64, 64);
                    if (itemBounds.contains(mousePoint)) {
                        selectedItem = inventoryItems[i][j];
                        return;
                    }
                }
            }  
        }
    }

    public void updateClickAndDrag() {
        if (clickAndDragItem != null && !handler.getMouseManager().isLeftPressed() && 
        (handler.getMouseManager().getMouseDragX() < 64 || handler.getMouseManager().getMouseDragX() > 576)) {
            System.out.println("you dropped an item");
            dropItem(clickAndDragItem);
            clickAndDragItem = null;
            return;
        }
        else if (!handler.getMouseManager().isLeftPressed()) {
            clickAndDragItem = null;
            return;
        } 
        if (clickAndDragItem == null) {
            Rectangle itemBounds;
            Point mousePoint = new Point(handler.getMouseManager().getMouseX(),
            handler.getMouseManager().getMouseY());
            for(int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    if (inventoryItems[i][j] == null) {
                        continue;
                    }
                    else {
                        itemBounds = new Rectangle(126 + (j * 68), 190 + (i * 68), 64, 64);
                        if (itemBounds.contains(mousePoint)) {
                            clickAndDragItem = inventoryItems[i][j];
                            return;
                        }
                    }
                }  
            }
        }

        //&& (handler.getMouseManager().getMouseDragX() < 64 || handler.getMouseManager().getMouseDragX() > 576)) 

    }

    //getters setters

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    



}