import java.lang.foreign.Addressable;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

class Connection {
    public String adresse_ip = "testip";    
    public String port = "testport";

    public void Connection() {
        //   boolean entreeValide = false;
    Scanner entreeUtil = new Scanner(System.in);
      
    //   String ipRegex = "^(([0-1]?[0-9]?[0-9]?|2[0-4][0-9]|25[0-5])\\.){3}([0-1]?[0-9]?[0-9]?|2[0-4][0-9]|25[0-5]){1}$";
    //   // Le regex ci-haut est tiré de :
    //   // https://stackoverflow.com/questions/31684083/validate-if-input-string-is-a-number-between-0-255-using-regex
    //   String portRegex = "^(50[0-4][0-9]|5050)$";
      
      
      String messageEntreeIp = "Entrez l'addresse IP";
    //   String messageEntreePort = "Entrez le port";
      
    //   String adresse_ip = "";
    //   String port = "";
    // while(!entreeValide){
        // System.out.println();
        adresse_ip = entreeUtil.nextLine();
        adresse_ip = entreeUtil.nextLine();
        adresse_ip = entreeUtil.nextLine();
    //     // TODO demander Bilal si 001.001.001.001 est valide
    //     Pattern p = Pattern.compile("^(([0-1]?[0-9]?[0-9]?|2[0-4][0-9]|25[0-5])\\.){3}([0-1]?[0-9]?[0-9]?|2[0-4][0-9]|25[0-5]){1}$");
    //     // Le regex ci-haut est tiré de : https://stackoverflow.com/questions/31684083/validate-if-input-string-is-a-number-between-0-255-using-regex
    //     Matcher m = p.matcher(adresse_ip);
    //     ipValide = m.matches();
    //     if (!ipValide){
    //         recommencerEssai();
    //     }
    // }
    // entreeValide = false;
    // while(!entreeValide){
    //     System.out.println("Entrer le Port");
    //     port = entreeUtil.nextLine();    
    //     Pattern p = Pattern.compile("^(50[0-4][0-9]|5050)$");
    //     Matcher m = p.matcher(port);
    //     portValide = m.matches();
    //     if (!portValide){
    //         recommencerEssai();
    //     }
    // }
}
// private String match (String message, String regex) {

// }

private void recommencerEssai () {
    System.out.println("Entrée invalide.");
      boolean essai = true;
      while (essai) {
          System.out.println("Souhaitez-vous réessayer o/n?");
          Scanner entreeUtil = new Scanner(System.in);
          String reponse = entreeUtil.nextLine();
          if (reponse.compareToIgnoreCase("N") == 0) {
              System.out.println("Terminaison du programme");
              System.exit(0);
          } else if (reponse.compareToIgnoreCase("O") == 0) {
              essai = false;
          } else {
              System.out.println("Réponse invalide.");
          }
      }
  }
    public static void main(String[] args){
        Connection a = new Connection();
        // System.out.println(new Connection());
        System.out.println(a.port);
        
    }
}