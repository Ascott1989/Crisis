package com.example.crisis;

import java.util.ArrayList;
import java.util.Collections;

public class AStar implements PathFinder {
	/** The set of nodes that have been searched through */
	private ArrayList closed = new ArrayList();
	
	private SortedList open = new SortedList();
	
	private TiledMap map;
	
	private int maxSearchDistance;
	/** The complete set of nodes across the map */
	private Node[][] nodes;
	
	private boolean allowDiagMovement;
	
	private AstarH heuristic;
	
	
	public AStar(TiledMap map, int maxSearchDistance, boolean allowDiagMovement) {
		this(map, maxSearchDistance, allowDiagMovement, new ClosestHeuristic());
	}

	
	public AStar(TiledMap map, int maxSearchDistance, 
						   boolean allowDiagMovement, AstarH heuristic) {
		this.heuristic = heuristic;
		this.map = map;
		this.maxSearchDistance = maxSearchDistance;
		this.allowDiagMovement = allowDiagMovement;
		
		nodes = new Node[map.getWidthT()][map.getHeightT()];
		for (int x=0;x<map.getWidthT();x++) {
			for (int y=0;y<map.getHeightT();y++) {
				nodes[x][y] = new Node(x,y);
			}
		}
	}
	
	
	public Path findPath(Mover mover, int sx, int sy, int tx, int ty) {
		// easy first check, if the destination is blocked, we can't get there

		if (map.blocked(mover, tx, ty)) {
			return null;
		}
		
		
		nodes[sx][sy].cost = 0;
		nodes[sx][sy].depth = 0;
		closed.clear();
		open.clear();
		open.add(nodes[sx][sy]);
		
		nodes[tx][ty].parent = null;
		
		
		int maxDepth = 0;
		while ((maxDepth < maxSearchDistance) && (open.size() != 0)) {
			

			Node current = getFirstInOpen();
			if (current == nodes[tx][ty]) {
				break;
			}
			
			removeFromOpen(current);
			addToClosed(current);
			
			

			for (int x=-1;x<2;x++) {
				for (int y=-1;y<2;y++) {
				

					if ((x == 0) && (y == 0)) {
						continue;
					}
				

					if (!allowDiagMovement) {
						if ((x != 0) && (y != 0)) {
							continue;
						}
					}
					
					int xp = x + current.x;
					int yp = y + current.y;
					
					if (isValidLocation(mover,sx,sy,xp,yp)) {
						

						float nextStepCost = current.cost + getMovementCost(mover, current.x, current.y, xp, yp);
						Node neighbour = nodes[xp][yp];
						map.pathFinderVisited(xp, yp);
						
						

						if (nextStepCost < neighbour.cost) {
							if (inOpenList(neighbour)) {
								removeFromOpen(neighbour);
							}
							if (inClosedList(neighbour)) {
								removeFromClosed(neighbour);
							}
						}
						
					
						if (!inOpenList(neighbour) && !(inClosedList(neighbour))) {
							neighbour.cost = nextStepCost;
							neighbour.heuristic = getHeuristicCost(mover, xp, yp, tx, ty);
							maxDepth = Math.max(maxDepth, neighbour.setParent(current));
							addToOpen(neighbour);
						}
					}
				}
			}
		}

		

		if (nodes[tx][ty].parent == null) {
			return null;
		}
		
		

		Path path = new Path();
		Node target = nodes[tx][ty];
		while (target != nodes[sx][sy]) {
			path.prependStep(target.x, target.y);
			target = target.parent;
		}
		path.prependStep(sx,sy);
		
	

		return path;
	}

	
	protected Node getFirstInOpen() {
		return (Node) open.first();
	}
	
	
	protected void addToOpen(Node node) {
		open.add(node);
	}
	
	
	protected boolean inOpenList(Node node) {
		return open.contains(node);
	}
	
	
	protected void removeFromOpen(Node node) {
		open.remove(node);
	}
	
	protected void addToClosed(Node node) {
		closed.add(node);
	}
	
	
	protected boolean inClosedList(Node node) {
		return closed.contains(node);
	}
	
	
	protected void removeFromClosed(Node node) {
		closed.remove(node);
	}
	
	
	protected boolean isValidLocation(Mover mover, int sx, int sy, int x, int y) {
		boolean invalid = (x < 0) || (y < 0) || (x >= map.getWidthT()) || (y >= map.getHeightT());
		
		if ((!invalid) && ((sx != x) || (sy != y))) {
			invalid = map.blocked(mover, x, y);
		}
		
		return !invalid;
	}
	
	
	public float getMovementCost(Mover mover, int sx, int sy, int tx, int ty) {
		return map.getCost(mover, sx, sy, tx, ty);
	}

	
	public float getHeuristicCost(Mover mover, int x, int y, int tx, int ty) {
		return heuristic.getCost(map, mover, x, y, tx, ty);
	}
	
	
	private class SortedList {
		
		private ArrayList list = new ArrayList();
		
		public Object first() {
			return list.get(0);
		}
		
		
		public void clear() {
			list.clear();
		}
		
		
		public void add(Object o) {
			list.add(o);
			Collections.sort(list);
		}
		
		
		public void remove(Object o) {
			list.remove(o);
		}
	
		
		public int size() {
			return list.size();
		}
		
		
		public boolean contains(Object o) {
			return list.contains(o);
		}
	}
	
	
	private class Node implements Comparable {
		/** The x coordinate of the node */
		private int x;
		/** The y coordinate of the node */
		private int y;
		/** The path cost for this node */
		private float cost;
		/** The parent of this node, how we reached it in the search */
		private Node parent;
		/** The heuristic cost of this node */
		private float heuristic;
		/** The search depth of this node */
		private int depth;
		
		
		public Node(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		
		public int setParent(Node parent) {
			depth = parent.depth + 1;
			this.parent = parent;
			
			return depth;
		}
		
		
		public int compareTo(Object other) {
			Node o = (Node) other;
			
			float f = heuristic + cost;
			float of = o.heuristic + o.cost;
			
			if (f < of) {
				return -1;
			} else if (f > of) {
				return 1;
			} else {
				return 0;
			}
		}
	}
}