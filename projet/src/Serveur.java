import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;

import javax.swing.text.html.HTMLDocument.Iterator;

// Les ports 5000 à 5050 sont valides
public class Serveur {
	private static int minPort = 5000;
	private static int maxPort = 5030;
	
	// Application Serveur
	public static void main(String[] args) throws Exception {
		ArrayList<Listener> listener = new ArrayList<>();
		// Adresse et port du serveur
		String serverAddress = "127.0.0.1";
		// String serverAddress = "10.200.47.102";

		System.out.format("The server is running on %s. Valid ports are 5000 to 5050", serverAddress);
		


        // ServerSocket socket = new ServerSocket();
        // socket.setReuseAddress(true);
        // InetAddress serverIP = InetAddress.getByName(serverAddress);
        // socket.bind(new InetSocketAddress(serverIP, 5001));

		// ServerSocket socket2 = new ServerSocket();
        // socket2.setReuseAddress(true);
        // InetAddress serverIP2 = InetAddress.getByName(serverAddress);
        // socket2.bind(new InetSocketAddress(serverIP2, 5000));




		// Selector selector = Selector.open();
		// for (int port = minPort; port <= maxPort; port++) {
		// 	ServerSocketChannel serverChannel = ServerSocketChannel.open();
		// 	serverChannel.configureBlocking(false);
		// 	serverChannel.socket().bind(new InetSocketAddress(port));
		// 	serverChannel.register(selector, SelectionKey.OP_ACCEPT);
		// }

		// while (true) {
		// 	selector.select();
		// 	Iterator<SelectionKey> selectedKeys = selector.selectedKeys().iterator();
		// 	while (selectedKeys.hasNext()) {
		// 		SelectionKey selectedKey = selectedKeys.next();
		
		// 		if (selectedKey.isAcceptable()) {
		// 			SocketChannel socketChannel = ((ServerSocketChannel) selectedKey.channel()).accept();
		// 			socketChannel.configureBlocking(false);
		// 			switch (socketChannel.socket().getPort()) {
		// 				case 1234:
		// 					// handle connection for the first port (1234)
		// 					break;
		// 				case 4321:
		// 					// handle connection for the secon port (4321)
		// 					break;
		// 			}
		// 		} else if (selectedKey.isReadable()) {
		// 			// yada yada yada
		// 		}
		// 	}
		// }





		// Création de la connexien pour communiquer ave les, clients
		for (int i = minPort; i <= maxPort; i++) {
			listener.add(new Listener("127.0.0.1", i));
		}
		for (int i = minPort; i <= maxPort; i++) {
			listener.get(i - minPort).run();
		}

	// 	int clientNumber = 0;
	// 	try {
	// 		while (true) {
	// 			// Important : la fonction accept() est bloquante: attend qu'un prochain client se connecte. Une nouvetle connection : on incémente le compteur clientNumber
    //             new ClientHandler(socket.accept(), clientNumber++).start();
	// 		}
    //     } finally {
	// 		// Fermeture de la connexion
    //             socket.close();
	// 	}
	}
} 	