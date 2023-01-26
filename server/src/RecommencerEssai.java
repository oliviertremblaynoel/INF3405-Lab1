import java.util.Scanner;

class RecommencerEssai {
    public RecommencerEssai () {
        System.out.println("Entrée invalide.");
        boolean essai = true;
        Scanner entreeUtil = new Scanner(System.in);

        while (essai) {
            System.out.println("Souhaitez-vous réessayer o/n?");
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
       entreeUtil.close();
    }
    public static void main(String[] args){
        new RecommencerEssai();
    }
}