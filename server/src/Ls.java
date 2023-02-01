import java.io.File;

public class Ls {
    public String Ls(String currentDir){
        File curDir = new File(currentDir);
        String str = "";
        File[] filesList = curDir.listFiles();
        for (File f : filesList) {
            if (f.isDirectory())
                str = str + f.getName() + "\n";
            if (f.isFile()) {
                str = str + f.getName() + "\n";
            }
        }
        return str;
    }
}
