import java.io.File;

class Ls {
    public String Ls()
    {
        File curDir = new File("..");
        return getAllFiles(curDir);
    }
    private String getAllFiles(File curDir) {
        String str = "";
        File[] filesList = curDir.listFiles();
        for(File f : filesList){
            if(f.isDirectory())
                str = str+f.getName();
            if(f.isFile()){
                str = str+f.getName();
            }
        }
        return str; 
    }
}