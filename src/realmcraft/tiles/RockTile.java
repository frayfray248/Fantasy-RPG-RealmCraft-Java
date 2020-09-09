package realmcraft.tiles;

import gfx.Assets;

public class RockTile extends Tile {

	public RockTile(int id) {
		super(Assets.rock, id);
	}

	@Override
	public boolean isSolid() {
		return true;
	}
}
