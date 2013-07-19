package com.example.crisis;

public interface TiledMap {
	
	public int getWidthT();
	
	public int getHeightT();
	
	public void pathFinderVisited(int x, int y);
	
	public boolean blocked(Mover mover, int x, int y);
	
	public float getCost(Mover mover, int sx, int sy, int tx, int ty);

}
