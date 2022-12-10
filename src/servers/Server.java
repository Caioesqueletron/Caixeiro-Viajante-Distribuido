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
			ServerSocket ss = new ServerSocket(porta);

			do {
				Socket socket = ss.accept();
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				ObjectSocket message = (ObjectSocket) ois.readObject();
				BestPath bestPath = searchForBestPath(message.city, message.fisrtSource, message.source, message.visitedVertex, message.cost, message.bestPath,message.path);
				oos.writeObject(bestPath);
				oos.flush();
				oos.close();
				ois.close();
			} while (true);

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
				
				if (cost < path.cost) {
					path.cost = cost;
					System.out.println("Best cost changed: " + path.cost);
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
				if (cost + grafo[currentVertex][adjVertex.get(i)] < path.cost) {
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
