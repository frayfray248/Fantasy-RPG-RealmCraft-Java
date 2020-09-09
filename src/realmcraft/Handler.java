package realmcraft;

import gfx.GameCamera;
import realmcraft.input.KeyManager;
import realmcraft.input.MouseManager;
import realmcraft.worlds.World;
import states.WorldStorage;

public class Handler {
	
	private Game game;
	private World world;
	private WorldStorage worldStorage;

	public Handler(Game game) {
		this.game = game;
	}
	
	public int getWidth() {
		return game.getWindowWidth();
	}
	
	public int getHeight() {
		return game.getWindowHeight();
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public GameCamera getGameCamera() {
		return game.getGameCamera();
	}

	public World getWorld() {
		return world;
	}

	public WorldStorage getWorldStorage() {
		return worldStorage;
	}

	public void setWorldStorage(WorldStorage worldStorage) {
		this.worldStorage = worldStorage;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public KeyManager getKeyManager() {
		return game.getKeyManager();
	}

	public MouseManager getMouseManager() {
		return game.getMouseManager();
	}

}
