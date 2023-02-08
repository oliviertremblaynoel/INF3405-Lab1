import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayDeque;

public class ClientHandler extends Thread { // Pour traiter la demande de chaque client sur un socket particulier
    private Socket socket;
    private int clientNumber;
    private String currentDir = System.getProperty("user.dir");
    private String dirDEP = currentDir;

    public ClientHandler(Socket socket, int clientNumber) throws IOException {
        this.socket = socket;
        this.clientNumber = clientNumber;
        System.out.println("Nouvelle connection avec client #" + clientNumber + " à : \n" + socket);
    }

    public void run() { // Création de thread qui envoi un message à un client
        try {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream()); // Création de canal d’envoi
            DataInputStream in = new DataInputStream(socket.getInputStream()); // Création d'un canal entrant pour recevoir les messages envoyés, par le serveur

            out.writeUTF("Message du server : Vous êtes le client #" + clientNumber);

            boolean connected = true; // Flag pour quitter le programme
            while (connected) {

                String message = in.readUTF(); // Attendre le prochain message du client
                System.out.println(header() + message); // Afficher la commande envoyée par le client sur le server

                // Note : Ici, Switch case impossible à cause du pattern matching de la string
                if (message.matches("exit")) {

                    // Sortir de la boucle while et déconnecter le client
                    connected = false;
                } else if (message.matches("ls")) {

                    out.writeUTF(ls()); // Afficher le dossier courant

                } else if (message.startsWith("mkdir")) {

                    mkdir(out, message); // Créer un dossier

                } else if (message.startsWith("download")) {

                    new SendFile(currentDir, message, out);// Envoyer un fichier au client

                } else if (message.startsWith("upload")) {
                    new RecieveFile(currentDir, message, in); // Recevoir un fichier du client

                } else if (message.startsWith("cd ")) {
                    cd(message);
                }
            }

        } catch (IOException e) {
            System.out.println("Problème de gestion du client# " + clientNumber + ": " + e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("Impossible de fermer un socket, que se passe-t-il ?");
            }
            System.out.println("Connexion avec le client# " + clientNumber + " fermée");
        }

    }

    private String header() {
        return "[" + socket.getInetAddress().toString().substring(1) + ":" + socket.getPort() + " - " + LocalDate.now()
                + "@"
                + LocalTime.now().truncatedTo(ChronoUnit.SECONDS) + "] : ";

    }

    private String ls() {
        File curDir = new File(currentDir);
        String str = "\n";
        File[] allList = curDir.listFiles();
        for (File f : allList) {
            if (f.isDirectory())
                str = str + "Dossier : \t" + f.getName() + "\n";
        }
        for (File f : allList) {
            if (f.isFile())
                str = str + "Fichier : \t" + f.getName() + "\n";
        }
        return str;
    }

    private void mkdir(DataOutputStream out, String message) throws IOException {
        String dir = message.split(" ")[1];
        File newDir = new File(currentDir + '\\' + dir);
        System.out.println(newDir.getPath());
        if (!newDir.exists()) {
            newDir.mkdirs();
            out.writeUTF("Le dossier a été créé avec succès");
        } else {
            out.writeUTF("Le dossier existe déjà");
        }
    }

    private void cd(String message) {

        ArrayDeque<String> cdCut = new ArrayDeque<>();
        StringBuilder stringBuilder = new StringBuilder();

        // Dossiers intermédiaires
        StringBuilder stringBuilder_second = new StringBuilder();
        ArrayDeque<String> copy_cdCut = cdCut.clone();

        // Découpage du chemin initial
        String dirCD = message.trim().split(" ")[1];
        String[] elementsCD = new String[dirDEP.split("\\\\").length];
        int newIndicator = 0;

        for (String elementHERE : dirDEP.split("\\\\")) {
            elementsCD[newIndicator] = elementHERE;
            newIndicator += 1;
        }

        // Ajout du chemin initial
        for (String eachelement : elementsCD) {
            cdCut.push(eachelement);
        }

        String[] elementsCD_2 = new String[currentDir.split("\\\\").length];
        int newIndicator_2 = 0;

        for (String elementHERE_2 : currentDir.split("\\\\")) {
            elementsCD_2[newIndicator_2] = elementHERE_2;
            newIndicator_2 += 1;
        }
        // Ajout de la différence avec le chemin initial
        for (int j = elementsCD.length; j < elementsCD_2.length; j++) {
            cdCut.push(elementsCD_2[j]);
        }

        stringBuilder_second.append(currentDir);

        for (String index_cd : dirCD.split("\\\\")) {

            // Chemin dossier alternatif
            File curDir = new File(stringBuilder_second.toString());
            File[] directoriesList = curDir.listFiles(File::isDirectory);
            String[] nameDirectories = new String[directoriesList.length];

            int indicator = 0;
            int test_check = 0;

            // Tests de la présence du dossier
            for (File direct : directoriesList) {
                nameDirectories[indicator] = (direct.getName());
                indicator += 1;
            }

            for (String test_cd : nameDirectories) {
                if (test_cd.equals(index_cd)) {
                    test_check = 1;

                }
            }

            // Remplissage de notre nouveau chemin d'accès
            if (index_cd.equals("..") && !(cdCut.getFirst() == elementsCD[elementsCD.length - 1])) {
                stringBuilder_second.setLength(0);
                cdCut.pop();
                copy_cdCut = cdCut.clone();
                while (!copy_cdCut.isEmpty()) {

                    stringBuilder_second.append("\\\\").append(copy_cdCut.pollLast());
                }

            } else if (test_check == 1 && !index_cd.isEmpty() && !index_cd.equals(".") && !index_cd.equals("..")) {
                stringBuilder_second.setLength(0);
                cdCut.push(index_cd);
                copy_cdCut = cdCut.clone();
                while (!copy_cdCut.isEmpty()) {
                    stringBuilder_second.append("\\\\").append(copy_cdCut.pollLast());
                }
            } else {
                System.out.println("Le chemin de fichier indiqué n'existe pas ou n'est pas valide.");
            }

        }

        // Passage de ArrayDeque à String
        while (!(cdCut.isEmpty())) {
            stringBuilder.append(cdCut.pollLast()).append("\\"); 
                                                                    
        }

        currentDir = stringBuilder.toString();
    }
}