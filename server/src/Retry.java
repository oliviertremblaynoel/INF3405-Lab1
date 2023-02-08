import java.util.Scanner;

class Retry {   
    public Retry(Scanner userEntry) {
        System.out.println("Entrée invalide.");
        boolean test = true;

        while (test) {
            System.out.println("Souhaitez-vous réessayer o/n?");
            String answer = userEntry.nextLine();
            if (answer.compareToIgnoreCase("N") == 0) {
                System.out.println("Terminaison du programme");
                System.exit(0);
            } else if (answer.compareToIgnoreCase("O") == 0) {
                test = false;
            } else {
                System.out.println("Réponse invalide.");
            }
        }
    }

    public static void main(String[] args) {
        new Retry(new Scanner(System.in));
    }
}