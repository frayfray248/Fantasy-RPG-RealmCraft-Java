package realmcraft.tiles;

import gfx.Assets;
import realmcraft.Game;

public class VictoryTile extends Tile {

	private boolean victory;

	public VictoryTile(int id) {
		super(Assets.victory, id);

	}

	@Override
	public boolean isVictoryTile() {
		return true;
	}

}