package bestPath;

import java.io.Serializable;
import java.util.ArrayList;

public class BestPath implements Serializable{
	private static final long serialVersionUID = -1006817289069012637L;
	private int cost = Integer.MAX_VALUE;
	
	public ArrayList<Integer> bestPath = new ArrayList<Integer>();
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public ArrayList<Integer> getBestPath() {
		return bestPath;
	}
	public void setBestPath(ArrayList<Integer> bestPath) {
		this.bestPath = bestPath;
	}
}
