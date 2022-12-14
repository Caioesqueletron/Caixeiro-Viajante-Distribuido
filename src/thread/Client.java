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
			Socket socket = new Socket(this.ip, this.port);
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(getObject());
			oos.flush();
			BestPath message = null;
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			message = (BestPath) ois.readObject();
			System.out.println("Cost received: " + message.getCost());
			synchronized (bestPath) {
					if (message.getCost() < bestPath.getCost()) {
						bestPath.setCost(message.getCost());
						bestPath.setBestPath(message.getBestPath());
					}
				}
			oos.close();
			ois.close();
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