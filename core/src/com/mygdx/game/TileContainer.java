package com.mygdx.game;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

public class TileContainer {
	public enum PositionType {LOWER, UPPER, LEFT, RIGHT, DIAGLOWERLEFT, DIAGLOWERRIGHT, DIAGUPPERLEFT, DIAGUPPERRIGHT}
	
	private PositionType relativePosition;
	private Cell tileCell;
	private int xIndex, yIndex;
	
	public TileContainer(PositionType relativePosition, Cell tileCell, int xIndex, int yIndex) {
		super();
		this.relativePosition = relativePosition;
		this.tileCell = tileCell;
		this.xIndex = xIndex;
		this.yIndex = yIndex;		
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

	public int getxIndex() {
		return xIndex;
	}

	public void setxIndex(int xIndex) {
		this.xIndex = xIndex;
	}

	public int getyIndex() {
		return yIndex;
	}

	public void setyIndex(int yIndex) {
		this.yIndex = yIndex;
	}	
}
