package realmcraft;

import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.WindowEvent;

import display.Display;
import gfx.Assets;
import gfx.GameCamera;
import realmcraft.input.KeyManager;
import realmcraft.input.MouseManager;
import realmcraft.tiles.Tile;
import states.State;
import states.VictoryState;
import states.WorldStorage;
import states.GameState;
import states.MenuState;

public class Game implements Runnable {

    //statics
    static public final int ZOOM_FACTOR = 2;
    
    //display
    private Display display;
    private int windowWidth, windowHeight;
    public String title;

    //graphics
    private BufferStrategy buffStrat;
    private final int NUMBUFFSTRAT = 3;
    private Graphics g;

    //camera
    private GameCamera gameCamera;

    //handler
    private Handler handler;

    //input
    private KeyManager keyManager;
    private MouseManager mouseManager;

    //states
    public State gameState;
    private WorldStorage worldStorage;
    public State menuState;
    private State victoryState;


    //thread
    private boolean running = false;
    private Thread thread;

    //CONSTRUCTOR
    public Game(String title, int windowWidth, int windowHeight) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.title = title;
        keyManager = new KeyManager();
        mouseManager = new MouseManager();
    }

    /* creates display */
    private void init() {
        display = new Display(title, windowWidth, windowHeight);
        display.getFrame().addKeyListener(keyManager);
        display.getFrame().addMouseListener(mouseManager);
        display.getFrame().addMouseMotionListener(mouseManager);
        display.getCanvas().addMouseListener(mouseManager);
        display.getCanvas().addMouseMotionListener(mouseManager);

        Assets.init();

        handler = new Handler(this);
        gameCamera = new GameCamera(handler, 0, 0);

        //creating states
        gameState = new GameState(handler);
        
        victoryState = new VictoryState(handler);
        menuState = new MenuState(handler);
        State.setState(menuState);

        //setting up worlds
        worldStorage = new WorldStorage(handler);
        handler.setWorldStorage(worldStorage);
        handler.setWorld(worldStorage.getWorld(0));
    }





    /////////////////////////////////////////////////
    ///////////////TICK AND RENDER///////////////////
    /////////////////////////////////////////////////

    private void tick() {
        //input
        keyManager.tick();
        mouseManager.tick();

        //states
        if (State.getState() != null) State.getState().tick();
    }

    private void render() {

        //buffer strategy
        buffStrat = display.getCanvas().getBufferStrategy();
        if (buffStrat == null) {
            display.getCanvas().createBufferStrategy(NUMBUFFSTRAT);
            return;
        }

        g = buffStrat.getDrawGraphics();
        g.clearRect(0, 0, windowWidth, windowHeight);

        //drawing

        if (State.getState() != null) State.getState().render(g);

        //end drawing

        buffStrat.show();
        g.dispose();
    }







    /////////////////////////////////////////////////
    /////////RUN, START, STOP, AND GAME LOOP/////////
    /////////////////////////////////////////////////

    //RUN GAME
    public void run() {
        init(); 								//initialization

        //game loop time regulation variables
        int fps = 60;                           //frames per second
        double timePerTick = 1000000000 / fps;  //1 second in nano seconds divided by the fps
                                                //This is the maximum time each game loop will take 
                                                //to maintain the fps
        double delta = 0;                       //        
        long now;                               //
        long lastTime = System.nanoTime();      //used to store the current computer time in nano seconds
        long timer = 0;                         //timer used 
        int ticks = 0;

        
        while(running) {

            now = System.nanoTime(); //storing current computer time
            
            //adding to the delta the amount of time that has passed since this line
            //of code was executed divided by the timerPerTick
            delta += (now - lastTime) / timePerTick;  
            timer += now - lastTime;    //adds to the timer variable, the amount of time since this code was executed
            lastTime = now; //updates the lastTime

            //if the delta is greater than one (meaning if the amount of time passed since
            //the last time the game ticked and rendered is greater than the maximum time allowed
            //for every tick), the game will tick and render.
            if(delta >= 1) {
                tick();
                render();
                ticks++;    //counts the ticks
                delta--;    //decrements the delta
            }

            //timer used to calculate and print the FPS
            if(timer >= 1000000000){
                System.out.println("Ticks and Frames: " + ticks);
                ticks = 0;
                timer = 0;
            }
        }

        stop(); //stops the thread in case it hasn't already
    }

    /* starts a new thread. The synchronized keyword 
    is used when working with threads directly */
    public synchronized void start() {
        if(running) return;        //does not execute if the game is already running
        running = true;            //starts the game loop
        thread = new Thread(this); //initialize this thread with this class
        thread.start();            //runs the game class on this thread.
                                   //The thread.start() calls the run() method
    }

    //stops the thread
    public synchronized void stop() {
        if(!running) return;        //does not execute if the game is already not running
        running = false;            //stops the game loop
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void exit() {
        display.close();
    }

    public void victory() {
        State.setState(victoryState);
    }


    //GETTERS SETTERS

    public int getWindowWidth() {
        return windowWidth;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public KeyManager getKeyManager() {
        return keyManager;
    }

    public MouseManager getMouseManager() {
        return mouseManager;
    }

    public GameCamera getGameCamera() {
        return gameCamera;
    }
}