import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;

public class Listener extends Thread {
    public ServerSocket socket;
    public int clientNumber = 0;
    
    public Listener(String ip, int port) throws Exception{
        socket = new ServerSocket();
        socket.setReuseAddress(true);
        InetAddress serverIP = InetAddress.getByName(ip);
        socket.bind(new InetSocketAddress(serverIP, port));
        System.out.println(port);
    }

    public void run(){
		try {
			while (true) {
				// Important : la fonction accept() est bloquante: attend qu'un prochain client se connecte. Une nouvetle connection : on incémente le compteur clientNumber
                new ClientHandler(socket.accept(), clientNumber++).start();
			}
		} catch (IOException e) {
            e.printStackTrace();
        } finally {
			// Fermeture de la connexion
			try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
		}
    }
}