package objectSocket;

import java.io.Serializable;
import java.util.ArrayList;

import bestPath.BestPath;

public class ObjectSocket implements Serializable{
	private static final long serialVersionUID = 5864263074889752646L;
	private int cost = 0;
	private boolean[] visitedVertex = null;
	private int city[][] = null;
	private int source = 0;
	private int fisrtSource = 0;
	private BestPath path;
	public ArrayList<Integer> bestPath = new ArrayList<Integer>();
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public boolean[] getVisitedVertex() {
		return visitedVertex;
	}
	public void setVisitedVertex(boolean[] visitedVertex) {
		this.visitedVertex = visitedVertex;
	}
	public int[][] getCity() {
		return city;
	}
	public void setCity(int[][] city) {
		this.city = city;
	}
	public int getSource() {
		return source;
	}
	public void setSource(int source) {
		this.source = source;
	}
	public int getFisrtSource() {
		return fisrtSource;
	}
	public void setFisrtSource(int fisrtSource) {
		this.fisrtSource = fisrtSource;
	}
	public BestPath getPath() {
		return path;
	}
	public void setPath(BestPath path) {
		this.path = path;
	}
	public ArrayList<Integer> getBestPath() {
		return bestPath;
	}
	public void setBestPath(ArrayList<Integer> bestPath) {
		this.bestPath = bestPath;
	}
	

}
