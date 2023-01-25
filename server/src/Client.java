import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ConnectException;
import java.net.Socket;

// Application client
public class Client {
    private static Socket socket;
    private static String serverAddress;
    private static int port;
    
    public static void main(String[] args) throws Exception {
        boolean entreeValide = false;
        while (!entreeValide) {
            // Connection connectInfos = new Connection();
            // serverAddress = connectInfos.ip;
            // port = Integer.parseInt(connectInfos.port);
            
            serverAddress = "127.0.0.1"; // Pour test seulement
            port = 5001; // pour test seulement
            try {
                // Création d'une nouvelle connexion aves le serveur
                socket = new Socket(serverAddress, port); // pour test seulement
                entreeValide = true;
            } catch (ConnectException e) {
                System.out.println("Impossible de connecter au server");
                new RecommencerEssai();
                entreeValide = false;
            }
       }
        System.out.format("Serveur lancé sur [%s:%d]", serverAddress, port);

        // Céatien d'un canal entrant pour recevoir les messages envoyés, par le serveur
        DataInputStream in = new DataInputStream(socket.getInputStream());

        // Attente de la réception d'un message envoyé par le, server sur le canal
        String helloMessageFromServer = in.readUTF();
        System.out.println(helloMessageFromServer);

        DataOutputStream out = new DataOutputStream(socket.getOutputStream()); // création de canal d’envoi
        out.writeUTF("Byebye#"); // envoi de message

        // fermeture de La connexion avec le serveur
        socket.close();
    }
}