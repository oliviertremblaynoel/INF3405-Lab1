import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SendFile {
    public SendFile(String currentDir, String command, DataOutputStream out) throws IOException {
        File file = new File(currentDir + "/" + command.split(" ")[1]);

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            String confirm = "OK";
            out.writeUTF(confirm);

            out.writeLong(file.length());
            byte[] buffer = new byte[4 * 1024];
            int bytes;
            while ((bytes = fileInputStream.read(buffer)) != -1) {
                out.write(buffer, 0, bytes);
                out.flush();
            }
            fileInputStream.close();
        } catch (FileNotFoundException e) {

            String confirm = "NOTOK";
            out.writeUTF(confirm);
            System.out.println("Fichier non trouvé");

        }
    }
}
