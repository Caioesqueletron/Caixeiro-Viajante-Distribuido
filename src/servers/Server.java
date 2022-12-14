package servers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import bestPath.BestPath;
import objectSocket.ObjectSocket;



public class Server {

	public Socket cliente;

	public static void main(String[] args) throws ClassNotFoundException {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Which port will run the server: ");
		int porta = scanner.nextInt();
		try {
			
			while(true) {
				ServerSocket ss = new ServerSocket(porta);
				Socket socket = ss.accept();
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				ObjectSocket message = (ObjectSocket) ois.readObject();
				BestPath bestPath = searchForBestPath(message.getCity(), message.getFisrtSource(), message.getSource(), message.getVisitedVertex(), message.getCost(), message.bestPath,message.getPath());
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
				oos.writeObject(bestPath);
				oos.flush();
				System.out.println("Finishing calculating best cost: "+ bestPath.getCost());
				oos.close();
				ois.close();
				socket.close();
				ss.close();
			}
			
		} catch (IOException ex) {
			Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		scanner.close();

	}

	public static BestPath searchForBestPath(int grafo[][], int source, int currentVertex, boolean[] visitedVertex,
			int cost, ArrayList<Integer> bestPath, BestPath path) {
	
		List<Integer> adjVertex = searchForAdjVertex(grafo, currentVertex);

		if (allVisited(visitedVertex.length, visitedVertex)) {
			if (adjVertex.contains(source)) {

				cost += grafo[currentVertex][source];
				bestPath.add(source);
				
				if (cost < path.getCost()) {
					path.setCost(cost);
					System.out.println("Best cost changed: " + path.getCost());
					path.bestPath.clear();
					for (int k = 0; k < bestPath.size(); k++) {
						path.bestPath.add(bestPath.get(k));
					}
				}

				bestPath.remove(bestPath.size() - 1);

			}

			return path;

		}

		visitedVertex[currentVertex] = true;

		for (int i = 0; i < adjVertex.size(); i++) {
			if (!visitedVertex[adjVertex.get(i)]) {
				if (cost + grafo[currentVertex][adjVertex.get(i)] < path.getCost()) {
					visitedVertex[adjVertex.get(i)] = true;
					if (adjVertex.get(i) != source) {
						bestPath.add(adjVertex.get(i));
					}

					path = searchForBestPath(grafo, source, adjVertex.get(i), visitedVertex,
							(cost + grafo[currentVertex][adjVertex.get(i)]), bestPath, path);
					visitedVertex[adjVertex.get(i)] = false;
					bestPath.remove(adjVertex.get(i));

				}
			}
		}

		return path;

	}

	public static boolean allVisited(int nVertices, boolean[] verticesVisitados) {
		int aux = 0;
		for (int i = 0; i < nVertices; i++) {
			if (verticesVisitados[i]) {
				aux++;
			}
		}
		return aux == nVertices ? true : false;
	}

	private static List<Integer> searchForAdjVertex(int graph[][], int verticeAtual) {

		List<Integer> vertices = new ArrayList<Integer>();

		for (int i = 0; i < graph.length; i++) {
			if (graph[verticeAtual][i] != 0 && verticeAtual != i) {
				vertices.add(i);
			}
		}

		return vertices;
	}

}
