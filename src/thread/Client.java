package thread;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import bestPath.BestPath;
import objectSocket.ObjectSocket;

public class Client extends Thread {
	private String ip;
	private int port;
	private ObjectSocket object;
	private BestPath bestPath;
	

	public Client(int port, String ip) {
		this.ip = ip;
		this.port = port;
	}

	public void run() {

		try {
			Socket socket = null;
			ObjectOutputStream oos = null;
			ObjectInputStream ois = null;
			socket = new Socket(this.ip, this.port);
			System.out.println("Tamanho do caminho " + object.bestPath.size());
			System.out.println(object.source);
			oos = new ObjectOutputStream(socket.getOutputStream());
			System.out.println("Sending request to Server");
			System.out.println("Custo atual " + this.object.cost);
			oos.writeObject(this.object);
			ois = new ObjectInputStream(socket.getInputStream());
			BestPath message = (BestPath) ois.readObject();
			synchronized (bestPath) {
				if (message.cost < bestPath.cost) {
					bestPath.setCost(message.cost);
					bestPath.setBestPath(message.bestPath);
				}
			}
			ois.close();
			oos.close();
			socket.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public ObjectSocket getObject() {
		return object;
	}

	public void setObject(ObjectSocket object) {
		this.object = object;
	}

	public BestPath getBestPath() {
		return bestPath;
	}

	public void setBestPath(BestPath bestPath) {
		this.bestPath = bestPath;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
}