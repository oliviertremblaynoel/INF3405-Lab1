import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
public class Server {
    private static ServerSocket Listener;
    public static void main(String[] args) throws Exception {
        int clientNumber = 0; // Compteur incrémenté à chaque connexion d'un client au serveur

        // String serverAddress = "10.200.47.102";
        String serverAddress = "localhost"; 
        int serverPort = 5000; 

        // Création de la connexien pour communiquer ave les, clients
        Listener = new ServerSocket();
        Listener.setReuseAddress(true);
        InetAddress serverIP = InetAddress.getByName(serverAddress);

        // Association de l'adresse et du port à la connexion
        Listener.bind(new InetSocketAddress(serverIP, serverPort));
        System.out.format("The server is running on %s:%d%n", serverAddress, serverPort);

        try {
            // À chaque fois qu'un nouveau client se, connecte, on exécute la fonction run() de l'objet ClientHandler
            while (true) {
                // Important : la fonction accept() est bloquante: attend qu'un prochain client se connecte. Une nouvetle connection : on incémente le compteur clientNumber
                new ClientHandler(Listener.accept(), serverIP, clientNumber++).start();
            }
        } finally {
            // Fermeture de la connexion
            Listener.close();
        }
    }
}