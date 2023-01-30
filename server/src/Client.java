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

    public static void main(String[] args) throws Exception {
        boolean entreeValide = false;
        while (!entreeValide) {
            // Connection connectInfos = new Connection();
            // serverAddress = connectInfos.ip;
            // port = Integer.parseInt(connectInfos.port);

            serverAddress = "127.0.0.1"; // Pour test seulement
            port = 5000; // pour test seulement

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
        System.out.format("Serveur lancé sur [%s:%d]\n", serverAddress, port);

        // Céatien d'un canal entrant pour recevoir les messages envoyés, par le serveur
        DataInputStream in = new DataInputStream(socket.getInputStream());

        // Attente de la réception d'un message envoyé par le, server sur le canal
        System.out.println(in.readUTF());

        // création de canal d’envoi
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        out.writeUTF("Byebye#"); // envoi de message

        while (true) {
            String commande = input.nextLine();
            // Switch case impossible because of pattern matching of strings
            if (commande.matches("exit")) {
                out.writeUTF(commande); // envoi de message
                socket.close();
                System.out.println("Vous avez été déconnecté avec succès.");
                System.exit(0);
            } else if (commande.matches("ls")) {
                // if mkdir/cd suivi de plus de deux nom de fichier -> commande invalide? 
                out.writeUTF(commande); // envoi de message
                System.out.println(in.readUTF());
            } else if (commande.startsWith("mkdir") && commande.split(" ").length == 2) {
                out.writeUTF(commande); // envoi de message
            } else if (commande.startsWith("cd")) {
                System.out.print(
                        "Commandes : \n ls : afficher les fichiers et dossiers \n cd : changer de dossier \n mkdir <nom_du_dossier> : créer un dossier \n download <fichier> \n upload <fichier> \n exit : se déconnecter et quitter \n");
            } else if (commande.matches("aide")) {
                System.out.print(
                        "Commandes : \n ls : afficher les fichiers et dossiers \n cd : changer de dossier \n mkdir <nom_du_dossier> : créer un dossier \n download <fichier> \n upload <fichier> \n exit : se déconnecter et quitter \n");
            } else {
                System.out.println("Commande invalide. Pour voir les commandes possibles, entrez : aide");
            }
        }
    }
}
