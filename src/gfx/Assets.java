package gfx;

import java.awt.Font;
import java.awt.image.BufferedImage;

public class Assets {
    
    //finals
    private static final int WIDTH = 32, HEIGHT = 32;
    private static final int HUMANOID_SHEET_LENGTH = 8;
    private static final int HUMANOID_SHEET_WIDTH = 8;
    
    //fonts
    public static Font font28;

    //tiles
    public static BufferedImage dirt, grass, rock, victory;

    //statics
    public static BufferedImage tree, boulder;

    //items
    public static BufferedImage wood, stone;

    //menus
    public static BufferedImage victoryScreen;

    public static BufferedImage inventoryScreen;

    //effects
    public static BufferedImage[] hitEffect;

    //buttons
    public static BufferedImage[] startButton;
    public static BufferedImage startButtonMouseOver;
    public static BufferedImage[] exitButton;
    public static BufferedImage exitButtonMouseOver;

    //player
    public static BufferedImage[][] player;
    
    //zombie
    public static BufferedImage[][] zombie;

    //effects
    public static BufferedImage swordLeft, swordRight, swordUp, swordDown;

    public static void init() {

        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("res/textures/sheet.png"));
        SpriteSheet tileSheet = new SpriteSheet(ImageLoader.loadImage("res/textures/tile_sheet.png"));
        SpriteSheet playerSheet = new SpriteSheet(ImageLoader.loadImage("res/textures/player_sheet.png"));
        SpriteSheet zombieSheet = new SpriteSheet(ImageLoader.loadImage("res/textures/zombie_sheet.png"));
        
        //fonts
        font28 = FontLoader.loadFont("res/fonts/slkscr.ttf", 28);

        //menus
        victoryScreen = ImageLoader.loadImage("res/textures/victory_screen.png");
        inventoryScreen = ImageLoader.loadImage("res/textures/inventoryScreen.png");

        //tiles
        grass = cropByGridPos(0, 0, tileSheet);
        dirt = cropByGridPos(1, 0, tileSheet);
        rock = cropByGridPos(2, 0, tileSheet);
        victory = cropByGridPos(0, 4, sheet);

        //statics
        tree = cropByGridPos(0, 1, 1, 2, sheet);
        boulder = cropByGridPos(1, 2, sheet);

        //misc
        swordLeft = cropByGridPos(0, 3, sheet);
        swordRight = cropByGridPos(1, 3, sheet);
        swordUp = cropByGridPos(2, 3, sheet);
        swordDown = cropByGridPos(3, 3, sheet);

        //items
        wood = cropByGridPos(1, 1, sheet);
        stone = cropByGridPos(2, 2, sheet);

        //buttons
        startButton = new BufferedImage[2];
        startButton[0] = cropByGridPos(1, 4, 2, 1, sheet);
        startButton[1] = cropByGridPos(1, 5, 2, 1, sheet);
        exitButton = new BufferedImage[2];
        exitButton[0] = cropByGridPos(3, 4, 2, 1, sheet);
        exitButton[1] = cropByGridPos(3, 5, 2, 1, sheet);

        //zombie
        zombie = new BufferedImage[HUMANOID_SHEET_LENGTH][HUMANOID_SHEET_WIDTH];
        for (int i = 0; i < zombie.length; i++) {
            for (int j = 0; j < zombie[0].length; j++) {
                zombie[i][j] = cropByGridPos(j, i, zombieSheet);
            }
        }

        //player
        player = new BufferedImage[HUMANOID_SHEET_LENGTH][HUMANOID_SHEET_WIDTH];
        for (int i = 0; i < player.length; i++) {
            for (int j = 0; j < player[0].length; j++) {
                player[i][j] = cropByGridPos(j, i, playerSheet);
            }
        }
        
        //effects
        hitEffect = new BufferedImage[3];
        hitEffect[0] = cropByGridPos(0, 5, sheet);
        hitEffect[1] = cropByGridPos(0, 6, sheet);
        hitEffect[2] = cropByGridPos(0, 7, sheet);

    }

    private static BufferedImage cropByGridPos(int xTile, int yTile, SpriteSheet sheet) {
    	return sheet.crop(WIDTH * xTile, HEIGHT * yTile, WIDTH, HEIGHT);
    }

    private static BufferedImage cropByGridPos(int xTile, int yTile, int xLength, int yLength, SpriteSheet sheet) {
    	return sheet.crop(WIDTH * xTile, HEIGHT * yTile, WIDTH * xLength, HEIGHT * yLength);
    }
}