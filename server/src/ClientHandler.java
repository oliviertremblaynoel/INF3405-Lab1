import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
public class ClientHandler extends Thread { // pour traiter la demande de chaque client sur un socket particulier
    private Socket socket;
    private int clientNumber;
    public ClientHandler(Socket socket, int clientNumber) {
        this.socket = socket;
        this.clientNumber = clientNumber;
        System.out.println("New connection with client#" + clientNumber + " at" + socket);
    }
    public void run() { // Création de thread qui envoi un message à un client
        try {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream()); // création de canal d’envoi
            out.writeUTF("Hello from server - you are client#" + clientNumber); // envoi de message

// Céatien d'un canal entrant pour recevoir les messages envoyés, par le serveur
            DataInputStream in = new DataInputStream(socket.getInputStream());
// Attente de la réception d'un message envoyé par le, server sur le canal
            String byeMessageFromClient = in.readUTF();
            System.out.println(byeMessageFromClient);

        } catch (IOException e) {
            System.out.println("Error handling client# " + clientNumber + ": " + e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("Couldn't close a socket, what's going on?");
            }
            System.out.println("Connection with client# " + clientNumber + " closed");
        }
    }
}