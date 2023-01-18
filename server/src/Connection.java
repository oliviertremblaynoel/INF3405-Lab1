import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

class Connection {
  public Connection() {
      
    String adresse_ip;
    String port;
    Scanner entreeUtil = new Scanner(System.in);

    // Addresse IP
    boolean ipValide = false;
    boolean portValide = false;
    while(!ipValide){
        System.out.println("Entrer l'addresse IP");
        adresse_ip = entreeUtil.nextLine();
        // TODO demander Bilal si 001.001.001.001 est valide
        Pattern p = Pattern.compile("^(([0-1]?[0-9]?[0-9]?|2[0-4][0-9]|25[0-5])\\.){3}([0-1]?[0-9]?[0-9]?|2[0-4][0-9]|25[0-5]){1}$");
        // Le regex ci-haut est tiré de : https://stackoverflow.com/questions/31684083/validate-if-input-string-is-a-number-between-0-255-using-regex
        Matcher m = p.matcher(adresse_ip);
        ipValide = m.matches();
        if (!ipValide){
            recommencerEssai();
        }
    }
    while(!portValide){
        System.out.println("Entrer le Port");
        port = entreeUtil.nextLine();    
        Pattern p = Pattern.compile("^(50[0-4][0-9]|5050)$");
        Matcher m = p.matcher(port);
        portValide = m.matches();
        if (!portValide){
            recommencerEssai();
        }
    }
  }
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
      }
  }