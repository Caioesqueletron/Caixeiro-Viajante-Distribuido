package graph;

import java.util.ArrayList;

public class Graph {
	private int nVertex;
	private String fileName;
	private int graph[][];

	public Graph(int nVertex, String fileName) {
		this.setnVertex(nVertex);
		this.setFileName(fileName);
		this.setGraph(new int[nVertex][nVertex]);
	}

	public void createMakeGraph() {
		FileManager fileManager = new FileManager();
		ArrayList<String> text = fileManager.stringReader("./data/" + this.getFileName());
		int cont = 0;
		int nVertex = this.getnVertex();
		for (int i = 0; i < nVertex + 1; i++) {
			cont = 0;
			String line = text.get(i);
			if (i == 0) {
				graph = new int[nVertex][nVertex];
			} else {
				int oriVertex = Integer.parseInt(line.split(" ")[0]);
				String splits[] = line.substring(line.indexOf(" "), line.length()).split(";");
				for (String part : splits) {
					String edgeData[] = part.split("-");
					int targetVertex = Integer.parseInt(edgeData[0].trim());
					int weight = Integer.parseInt(edgeData[1]);

					graph[oriVertex][targetVertex] = weight;
					graph[targetVertex][oriVertex] = weight;
					cont++;
					if (cont == nVertex - 1)
						break;

				}

			}

		}
		this.setGraph(graph);

	}

	public int getnVertex() {
		return nVertex;
	}

	public void setnVertex(int nVertex) {
		this.nVertex = nVertex;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int[][] getGraph() {
		return graph;
	}

	public void setGraph(int graph[][]) {
		this.graph = graph;
	}
}
