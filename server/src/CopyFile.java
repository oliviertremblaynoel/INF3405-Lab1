import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CopyFile {
    public CopyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buf = new byte[8192];
        int len = 0;
        while ((len = in.read(buf)) != -1) {
            out.write(buf, 0, len);
        }
    }
}