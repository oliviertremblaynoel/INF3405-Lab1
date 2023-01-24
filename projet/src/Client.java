import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
// Application client
public class Client {
private static Socket socket;
        public static void main(String[] args) throws Exception {

                // Issue here : The program doesnt wait until user finishes prompt before continuing
                Connection connectInfos = new Connection();
        // For testing purpose
        // Adresse et port du serveur
        // String serverAddress = "localhost";
        // int port = 5000;
        
        // Création d'une nouvelle connexion aves le serveur
        // socket = new Socket(
        //         connectInfos.ip, 
        //         Integer.parseInt(connectInfos.port)
        // );
        // System.out.format("Serveur lancé sur [%s:%d]", connectInfos.IP, connectInfos.port);

        socket = new Socket(
                "localhost", 
                5001
        );
        // System.out.format("Serveur lancé sur [%s:%d]", connectInfos.IP, connectInfos.port);

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