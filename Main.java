import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // poner estadio de verificacion de lo ingredo por el usuario
        System.out.println("\n\n W O R D L E \nIn which language do you want to play?/En qué idioma quiere jugar?");
        System.out.println("A) English");
        System.out.println("B) Espanol");
        System.out.print("--> ");
        Scanner sc = new Scanner(System.in); 
        String language = sc.nextLine();  
        language = language.toLowerCase(); 

        Wordle newGame;

        while (! Wordle.languagePossibilities.contains(language) ) {
            System.out.println("The language selected is not one of the options available./ El idioma elegido no es una de las opciones disponible.");
            System.out.println("Please, pick one the following options: / Por favor, eliga una de las siguientes opciones:");
            System.out.println("A) English");
            System.out.println("B) Espanol");
            System.out.print("--> ");
            language = sc.nextLine(); 
            language = language.toLowerCase(); 
        }

        if (language.equals("english") || language.equals("a")) {
            newGame = new English();
        } 
       
         else {
            newGame = new Spanish();
        }

        newGame.play();

        sc.close();
    }
}