import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
public class RecieveFile {
    public RecieveFile(String commande, DataInputStream in) throws IOException {

        String[] tmp = commande.split("/"); // Process command name
        String fileName = tmp[tmp.length - 1]; // isolate file name without path

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            
            long size = in.readLong(); // read file size
            byte[] buffer = new byte[4 * 1024];
            int bytes;
            
            while (size > 0 && (bytes = in.read(buffer, 0,(int) Math.min(buffer.length, size))) != -1) {
                fileOutputStream.write(buffer, 0, bytes);
                size -= bytes;
            }
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
