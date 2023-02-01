import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RecieveFile {
    public RecieveFile(DataOutputStream out, String commande, Socket socket) throws IOException{
        // out.writeUTF(commande); // envoi de message
        String[] tmp = commande.split("/");
        String fileName = tmp[tmp.length-1];
        InputStream inStream = socket.getInputStream();
        OutputStream outStream = new FileOutputStream(fileName);
        new CopyFile(inStream, outStream);
        outStream.close();
        // inStream.close();
    }
}
