import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;

public class Mkdir {
    public Mkdir(DataOutputStream out, String message) throws IOException{    
        String dir = message.split(" ")[1];
        File newDir = new File(dir);
        if (!newDir.exists()) {
            newDir.mkdirs();
        } else {
            out.writeUTF("le dossier existe déjà");
        }
    }
}
