import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class RecieveFile {
    public RecieveFile(String currentDir, String command, DataInputStream in) throws IOException {
        String filePath = command.split(" ")[1]; // Nom de la commande du processus
        String[] tmp = filePath.split("/"); // Nom de la commande du processus
        String fileName = tmp[tmp.length - 1]; // Isoler le nom du fichier sans le chemin
        fileName = currentDir + "/" + fileName; 

        if (in.readUTF().equals("OK")) {
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);

            long size = in.readLong(); // Lire la taille du fichier
            byte[] buffer = new byte[4 * 1024];
            int bytes;

            while (size > 0 && (bytes = in.read(buffer, 0, (int) Math.min(buffer.length, size))) != -1) {
                fileOutputStream.write(buffer, 0, bytes);
                size -= bytes;
            }
            fileOutputStream.close();

        } else {

            System.out.println("Fichier non trouvé");

        }
    }
}
