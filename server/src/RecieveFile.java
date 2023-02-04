import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
public class RecieveFile {
    public RecieveFile(String commande, Socket socket) throws IOException {
        String[] tmp = commande.split("/");
        String fileName = tmp[tmp.length - 1];

        try { 
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream (socket.getOutputStream());
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            
            long size = dataInputStream.readLong(); // read file size
            byte[] buffer = new byte[4 * 1024];
            int bytes;
            while (size > 0 && (bytes = dataInputStream.read(buffer, 0,(int) Math.min(buffer.length, size))) != -1) {
                fileOutputStream.write(buffer, 0, bytes);
                size -= bytes;
            }
            fileOutputStream.close();
            dataInputStream.close();
            dataOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
