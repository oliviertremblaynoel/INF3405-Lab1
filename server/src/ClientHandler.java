import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class ClientHandler extends Thread { // pour traiter la demande de chaque client sur un socket particulier
    private Socket socket;
    private int clientNumber;
    private String currentDir = System.getProperty("user.dir");

    public ClientHandler(Socket socket, int clientNumber) {
        this.socket = socket;
        this.clientNumber = clientNumber;
        System.out.println("Nouvelle connection avec client #" + clientNumber + " à : \n" + socket);
    }

    public void run() { // Création de thread qui envoi un message à un client
        try {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream()); // création de canal d’envoi            
            DataInputStream in = new DataInputStream(socket.getInputStream()); // Céatien d'un canal entrant pour recevoir les messages envoyés, par le serveur
            
            
            
            out.writeUTF("Message du server : Vous êtes le client #" + clientNumber); 
            
            boolean connected = true;
            while (connected) {

                String message = in.readUTF(); // Attendre le prochain message du client
                System.out.println(header() + message); // Afficher la commande envoyée par le client sur le server

                // Note : Ici, Switch case impossible à cause du pattern matching de la string
                if (message.matches("exit")) {
                    // sortir de la boucle while et déconnecter le client 
                    connected = false;
                } else if (message.matches("ls")) {                    
                    out.writeUTF(new Ls().Ls(currentDir)); // afficher le dossier courant
                } else if (message.startsWith("mkdir")) {
                    new Mkdir(out, message); // créer un dossier
                } else if (message.startsWith("cd")) {
                    // changer le dossier courant 
                } else if (message.startsWith("download")) {
                    new SendFile(out, message, socket);// envoyer un fichier au client
                } else if (message.startsWith("upload")) {
                    new RecieveFile(out, message, socket); // recevoir un fichier du client
                }
            }

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

    private String header() {
        return "[" + socket.getInetAddress() + ":" + socket.getPort() + " - " + LocalDate.now() + "@"
                + LocalTime.now().truncatedTo(ChronoUnit.SECONDS) + "] : ";
    }
}