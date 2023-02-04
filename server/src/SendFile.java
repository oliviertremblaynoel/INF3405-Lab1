import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class SendFile {
    public SendFile(String commande, DataOutputStream out) throws IOException {
        String[] messageSplit = commande.split(" ");
        File file = new File(messageSplit[1]);
        
        try {
            FileInputStream fileInputStream = new FileInputStream(file);      

            out.writeLong(file.length());
            byte[] buffer = new byte[4 * 1024];
            int bytes;
            while ((bytes = fileInputStream.read(buffer)) != -1) {
                out.write(buffer, 0, bytes);
                out.flush();
            }
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
