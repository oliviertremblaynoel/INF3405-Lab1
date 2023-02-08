import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Scanner;

// Application client
public class Client {
    private static Socket socket;
    private static String serverAddress;
    private static int port;
    private static Scanner input = new Scanner(System.in);
    private static String currentDir = System.getProperty("user.dir");

    public static void main(String[] args) throws Exception {
        boolean validInput = false;
        while (!validInput) {
            Connection connectInfos = new Connection();
            serverAddress = connectInfos.ip;
            port = Integer.parseInt(connectInfos.port);

            try {
                // Création d'une nouvelle connexion aves le serveur
                socket = new Socket(serverAddress, port);
                validInput = true;
            } catch (ConnectException e) {
                System.out.println("Impossible de connecter au server");
                new Retry();
                validInput = false;
            }
        }
        System.out.format("Serveur lancé sur [%s:%d]\n", serverAddress, port);

        DataInputStream in = new DataInputStream(socket.getInputStream()); // Création d'un canal entrant pour recevoir les messages envoyés, par le serveur
        DataOutputStream out = new DataOutputStream(socket.getOutputStream()); // Création du canal d’envoi

        System.out.println(in.readUTF()); // Attente de la réception d'un message de bienvenue envoyé par le server sur le canal

        while (true) {
            String command = input.nextLine(); // Attendre le prochain message de l'utilisateur

            // Note : Ici, Switch case impossible à cause du pattern matching de la string
            if (command.matches("exit")) {

                out.writeUTF(command); // Envoi de message
                socket.close();
                System.out.println("Vous avez été déconnecté avec succès.");
                System.exit(0);

            } else if (command.matches("ls")) {

                out.writeUTF(command); // Envoi de message
                System.out.println(in.readUTF()); // Retour du message

            } else if (command.startsWith("mkdir") && command.split(" ").length == 2) {

                out.writeUTF(command); // Envoi de message
                System.out.println(in.readUTF()); // Retour du message

            } else if (command.startsWith("cd")){

                out.writeUTF(command); // Envoi de message
                
            } else if (command.startsWith("download")) {

                out.writeUTF(command);
                new RecieveFile(currentDir, command, in);

            } else if (command.startsWith("upload")) {

                out.writeUTF(command);
                new SendFile(currentDir, command, out);// Envoyer un fichier au client

            } else if (command.matches("aide")) {

                System.out.print(
                        "commands : \n ls : afficher les fichiers et dossiers \n cd : changer de dossier \n mkdir <nom_du_dossier> : créer un dossier \n download <fichier> \n upload <fichier> \n exit : se déconnecter et quitter \n");

            } else {

                System.out.println("Commande invalide. Pour voir les commandes possibles, entrez : aide");

            }
        }
    }
}