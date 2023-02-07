import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayDeque;
import java.util.Arrays;

public class ClientHandler extends Thread { // pour traiter la demande de chaque client sur un socket particulier
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
        System.out.println(currentDir);
        try {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream()); // création de canal d’envoi
            DataInputStream in = new DataInputStream(socket.getInputStream()); // Céatien d'un canal entrant pour
                                                                               // recevoir les messages envoyés, par le
                                                                               // serveur

            out.writeUTF("Message du server : Vous êtes le client #" + clientNumber);

            boolean connected = true; // flag pour quitter le programme
            while (connected) {

                String message = in.readUTF(); // Attendre le prochain message du client
                System.out.println(header() + message); // Afficher la commande envoyée par le client sur le server

                // Note : Ici, Switch case impossible à cause du pattern matching de la string
                if (message.matches("exit")) {

                    // sortir de la boucle while et déconnecter le client
                    connected = false;
                }else if(message.matches("ls")){

                    out.writeUTF(ls()); // afficher le dossier co

                }else if(message.startsWith("mkdir")){

                    mkdir(out, message); // créer un dossier

                }else if(message.startsWith("cd ")){

                    ArrayDeque<String> cd_coupe = new ArrayDeque<>();
                    StringBuilder stringBuilder = new StringBuilder();

                    // Dossiers intermédiaires
                    StringBuilder stringBuilder_second = new StringBuilder();
                    ArrayDeque<String> copy_cd_coupe = cd_coupe.clone();
                    

                    // Découpage du chemin initial
                    String le_dir_cd = message.trim().split(" ")[1];
                    String [] elementsCD = new String[dirDEP.split("\\\\").length];
                    int new_indicateur = 0;
        
                    for(String elementHERE : dirDEP.split("\\\\")){
                        elementsCD[new_indicateur] = elementHERE;
                        //System.out.println(elementsCD[new_indicateur]);
                        new_indicateur +=1;
                        
                    }

                    // Ajout du chemin initial
                    for(String eachelement : elementsCD){
                        cd_coupe.push(eachelement);
                        //System.out.println(cd_coupe);
                    }
                    //System.out.println("Coucou");
                    
                    String [] elementsCD_2 = new String[currentDir.split("\\\\").length];
                    int new_indicateur_2 = 0;

                    for(String elementHERE_2 : currentDir.split("\\\\")){
                        elementsCD_2[new_indicateur_2] = elementHERE_2;
                        //System.out.println(elementsCD_2[new_indicateur_2]);
                        new_indicateur_2 += 1;
                    }
                    // Ajout de la différence avec le chemin initial
                    for(int j = elementsCD.length; j <elementsCD_2.length ;j++){
                        cd_coupe.push(elementsCD_2[j]);
                        //System.out.println(cd_coupe);
                    }

                    stringBuilder_second.append(currentDir);

                    for (String index_cd : le_dir_cd.split("\\\\")) {
                        //System.out.println(index_cd);

                        // Chemin dossier alternatif
                        File curDir = new File(stringBuilder_second.toString());
                        File[] directoriesList = curDir.listFiles(File::isDirectory);
                        //System.out.println(Arrays.toString(directoriesList));
                        String[] nameDirectories = new String[directoriesList.length];

                        int indicateur = 0;
                        int test_check = 0;

                        // Tests de la présence du dossier
                        for(File direct : directoriesList ){
                            nameDirectories[indicateur] = (direct.getName());
                            indicateur +=1;
                        }
                        //System.out.println(Arrays.toString(nameDirectories));

                        for(String test_cd : nameDirectories){
                            //System.out.println(test_cd);
                            if(test_cd.equals(index_cd)){
                                test_check = 1;
                                
                            }
                        }
                        //System.out.println(test_check);                                     
                        
                        // Remplissage de notre nouveau chemin d'accès
                        if (index_cd.equals("..") && !(cd_coupe.getFirst()==elementsCD[elementsCD.length -1])) {
                            stringBuilder_second.setLength(0);
                            cd_coupe.pop();
                            copy_cd_coupe = cd_coupe.clone();
                            while (!copy_cd_coupe.isEmpty()) {
                                
                                stringBuilder_second.append("\\\\").append(copy_cd_coupe.pollLast());
                            }

                        } else if (test_check == 1 && !index_cd.isEmpty() && !index_cd.equals(".") && !index_cd.equals("..")){
                            stringBuilder_second.setLength(0);
                            cd_coupe.push(index_cd);
                            copy_cd_coupe = cd_coupe.clone();
                            while (!copy_cd_coupe.isEmpty()) {
                                stringBuilder_second.append("\\\\").append(copy_cd_coupe.pollLast());
                            }
                        } else {
                            System.out.println("Le chemin de fichier indiqué n'existe pas ou n'est pas valide.");
                        }

                    }
                    
                    // Passage de ArrayDeque à String
                    while (!(cd_coupe.isEmpty())){
                        stringBuilder.append(cd_coupe.pollLast()).append("\\");                 // Remove the Stack element from bottom + Voir si le current dir le / est avant ou après
                    }
                    
                    currentDir = stringBuilder.toString();
                    System.out.println(currentDir);
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
        return "[" + socket.getInetAddress().toString().substring(1) + ":" + socket.getPort() + " - " + LocalDate.now()
                + "@"
                + LocalTime.now().truncatedTo(ChronoUnit.SECONDS) + "] : ";
    
    }

    private String ls() {

        File curDir = new File(currentDir);
        String str = "";
        File[] filesList = curDir.listFiles();
        for (File f : filesList) {
            if (f.isDirectory())
                str = str + f.getName() + "\n";
            if (f.isFile()) {
                str = str + f.getName() + "\n";
            }
        }
        return str;
    }

    private void mkdir(DataOutputStream out, String message) throws IOException {
        String dir = message.split(" ")[1];
        File newDir = new File(dir);
        if (!newDir.exists()) {
            newDir.mkdirs();
        } else {
            out.writeUTF("le dossier existe déjà");
        }
    }
}