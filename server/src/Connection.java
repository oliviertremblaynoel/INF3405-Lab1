import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

class Connection {
    public String ip;
    public String port;
    Scanner entreeUtil = new Scanner(System.in);

    public Connection() {

        String ipRegex = "^(([0-1]?[0-9]?[0-9]?|2[0-4][0-9]|25[0-5])\\.){3}([0-1]?[0-9]?[0-9]?|2[0-4][0-9]|25[0-5]){1}$";
        // Le regex ci-haut est tiré de :
        // https://stackoverflow.com/questions/31684083/validate-if-input-string-is-a-number-between-0-255-using-regex
        ip = match("Entrez l'addresse IP", ipRegex);

        String portRegex = "^(50[0-4][0-9]|5050)$";
        port = match("Entrez le port (entre 5000 et 5050)", portRegex);
    }

    private String match(String message, String regex) {
        String sortie = "";
        boolean entreeValide = false;
        while (!entreeValide) {
            System.out.println(message);
            sortie = entreeUtil.nextLine();
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(sortie);
            entreeValide = m.matches();
            if (!entreeValide) {
                new RecommencerEssai();
            }
        }
        return sortie;
    }

    public static void main(String[] args) {
        Connection a = new Connection();
        System.out.println("IP : " + a.ip);
        System.out.println("PORT : " + a.port);
    }
}