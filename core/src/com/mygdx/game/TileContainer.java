package com.mygdx.game;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

public class TileContainer {
	public enum PositionType {LOWER, UPPER, LEFT, RIGHT, DIAGLOWERLEFT, DIAGLOWERRIGHT, DIAGUPPERLEFT, DIAGUPPERRIGHT}
	
	private PositionType relativePosition;
	private HitBox relativeHitBox;
	private Cell tileCell;
	
	public TileContainer(PositionType relativePosition, HitBox relativeHitBox, Cell tileCell) {
		super();
		this.relativePosition = relativePosition;
		this.tileCell = tileCell;
		this.relativeHitBox = relativeHitBox;
	}

	public PositionType getRelativePosition() {
		return relativePosition;
	}

	public void setRelativePosition(PositionType relativePosition) {
		this.relativePosition = relativePosition;
	}

	public Cell getTileCell() {
		return tileCell;
	}

	public void setTileCell(Cell tileCell) {
		this.tileCell = tileCell;
	}

	public HitBox getRelativeHitBox() {
		return relativeHitBox;
	}

	public void setRelativeHitBox(HitBox relativeHitBox) {
		this.relativeHitBox = relativeHitBox;
	}	
}
