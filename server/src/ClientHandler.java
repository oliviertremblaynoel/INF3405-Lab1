import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

            boolean connected = true;
            while (connected) {
                String message = in.readUTF();
                System.out.println(header() + message);

                // Switch case impossible because of pattern matching of strings
                if (message.matches("exit")){
                    connected = false;
                }else if(message.matches("ls")){
                    File curDir = new File(currentDir);
                    String str = "";
                    File[] filesList = curDir.listFiles();
                    for (File f : filesList) {
                        str = str + f.getName() + "\n";
                    }
                    out.writeUTF(str); // envoi de message
                }else if(message.startsWith("mkdir")){
                    String dir = message.split(" ")[1];
                    File newDir = new File(dir);
                    if (!newDir.exists()){
                        newDir.mkdirs();
                    }else{
                        out.writeUTF("folder already exists"); 
                    }
                }else if(message.startsWith("upload")){
                    int size = in.readInt();
                    byte[] buffer = new byte[size];
                    in.read(buffer);
                    File uploadedFile = new File("test"); 
                    uploadedFile.createNewFile();
                    FileOutputStream f = new FileOutputStream(uploadedFile);
                    f.write(buffer);
                    //System.out.println(uploadedFile.getName());
                    /* FileOutputStream f = new FileOutputStream(message.split(" ")[1]);
                    f.write(buffer); */
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