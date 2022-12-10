package cooredenator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import bestPath.BestPath;
import graph.Graph;
import objectSocket.ObjectSocket;
import thread.Client;

public class Coordenator {

	public static String ip;
	public static int port;

	public static void main(String[] args) throws ClassNotFoundException, IOException, InterruptedException {

		int source = 0;
		int cost = 0;
		int bestCost = Integer.MAX_VALUE;
		int sourceFirst = 0;
		boolean[] visitedVertex = null;

		ArrayList<Integer> bestPath = new ArrayList<Integer>();
		ArrayList<Client> threads = new ArrayList<Client>();
		int treeDepth = 0;
		int nServers = 0;

		Scanner sc = new Scanner(System.in);
		do {
			if (nServers > 5) {
				System.out.println("Server amount not alowed. Insert again from 1 to 5: ");
				nServers = sc.nextInt();

			} else {
				System.out.println("How many servers do you need from 1 to 5 ?");

				nServers = sc.nextInt();
			}

		} while (nServers > 5);

		for (int j = 0; j < nServers; j++) {
			System.out.println("Insert the server's ip: ");
			ip = sc.next();
			System.out.println("Which port is running the server: ");
			port = sc.nextInt();
			threads.add(new Client(port, ip));

		}

		System.out.println("Insert the number of cities: ");
		int nVertex = sc.nextInt();

		Graph graph = new Graph(nVertex, "Cidade2.txt");
		graph.createMakeGraph();
		int[][] city = new int[nVertex][nVertex];
		city = graph.getGraph();
		visitedVertex = new boolean[nVertex];

		for (int i = 0; i < nVertex; i++) {
			visitedVertex[i] = false;
		}

		BestPath melhorVertice = new BestPath();
		BestPath goodPath = searchForBestPath(city, source, sourceFirst, visitedVertex, cost, bestCost, bestPath,
				melhorVertice, treeDepth, threads);

		System.out.println("Best path: " + goodPath.bestPath);
		System.out.println("Best cost: " + goodPath.cost);
		sc.close();

	}

	public static BestPath searchForBestPath(int grafo[][], int source, int currentVertex, boolean[] visitedVertex,
			int cost, int bestCost, ArrayList<Integer> bestPath, BestPath path, int depthTree,
			ArrayList<Client> threads) throws ClassNotFoundException, IOException, InterruptedException {

		System.out.println("Best current cost: " + path.cost);
		List<Integer> adjVertex = searchForAdjVertex(grafo, currentVertex);

		visitedVertex[currentVertex] = true;

		for (int i = 0; i < adjVertex.size(); i++) {
			if (!visitedVertex[adjVertex.get(i)]) {
				if (cost + grafo[currentVertex][adjVertex.get(i)] < path.cost) {
					visitedVertex[adjVertex.get(i)] = true;
					if (adjVertex.get(i) != source) {
						bestPath.add(adjVertex.get(i));
					}
					if (depthTree >= 3) {

						ObjectSocket object = new ObjectSocket();
						ArrayList<Integer> copyBestPath = new ArrayList<Integer>();
						boolean[] copyVisitedVertex = new boolean[visitedVertex.length];
						for (int j = 0; j < bestPath.size(); j++) {
							copyBestPath.add(bestPath.get(j));
						}
						for (int j = 0; j < visitedVertex.length; j++) {
							copyVisitedVertex[j] = visitedVertex[j];

						}
						object.bestPath = copyBestPath;
						object.path = path;
						object.city = grafo;
						object.cost = cost + grafo[currentVertex][adjVertex.get(i)];
						object.visitedVertex = copyVisitedVertex;
						object.fisrtSource = source;
						object.source = adjVertex.get(i);

						while (areAllThreadRunning(threads)) {

						}

						for (int k = 0; k < threads.size(); k++) {
							if (!threads.get(k).isAlive()) {
								System.out.println("Executing thread: " + k + " from city: " + object.source);
								threads.set(k, new Client(threads.get(k).getPort(), threads.get(k).getIp()));
								threads.get(k).setObject(object);
								threads.get(k).setBestPath(path);
								threads.get(k).start();
								break;

							}

						}

					} else {
						path = searchForBestPath(grafo, source, adjVertex.get(i), visitedVertex,
								(cost + grafo[currentVertex][adjVertex.get(i)]), path.cost, bestPath, path,
								depthTree + 1, threads);
					}
					bestPath.remove(adjVertex.get(i));
					visitedVertex[adjVertex.get(i)] = false;

				}
			}
		}
		if (depthTree == 0) {
			System.out.println("Finishing threads jobs...");
			for (int k = 0; k < threads.size(); k++) {
				if (threads.get(k).isAlive()) {
					threads.get(k).join();
				}
			}
		}

		return path;

	}

	public static boolean areAllThreadRunning(ArrayList<Client> threads) {
		int isAllRunning = 0;
		for (int i = 0; i < threads.size(); i++) {
			if (threads.get(i).isAlive()) {
				isAllRunning++;
			}
		}
		if (isAllRunning == threads.size()) {
			return true;

		} else {
			return false;
		}
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
