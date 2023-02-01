import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SendFile {
    public SendFile(DataOutputStream out, String commande, Socket socket) throws IOException{
        String[] messageSplit = commande.split(" ");
        InputStream inStream = new FileInputStream(messageSplit[1]);
        OutputStream outStream = socket.getOutputStream();
        new CopyFile(inStream, outStream);
        // outStream.close();
        inStream.close();
    }
}
