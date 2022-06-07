
import java.util.List;
import java.util.Scanner;

public class Spanish extends Wordle { 
    public int streakcount;

    public Spanish() { //constructor
        super(); 
        chosenWordListWithoutAccentsFileName = "diccionario-sin-tildes.txt";
        chosenWordListFileName = "diccionario-con-tildes.txt";
        userDictionaryWithoutAccentsFileName = "extended-diccionario-sin-tildes.txt";
        result = "Resultado: ";
        youWonMessage = "FELICITACIONES! HA GANADO!";
        youLostMessage = "HA PERDIDO, LA PALABRA ELEGIDA POR EL JUEGO ES: ";
    }

    // metodos
    @Override
    public void printInstructions() { //instrucciones
        System.out.println("\nEl juego ha elegido una palabra de 5 letras para que adivine.");
        System.out.println("Tiene 6 intentos. En cada uno, el juego le indicara que letras tienen en común la palabra elegida y la adivinada:");
        System.out.println("- Las letras resaltadas en " + ANSI_GREEN_BACKGROUND + "verde" + ANSI_RESET + " estan en el lugar correcto.");
        System.out.println("- Las letras resaltadas en " + ANSI_YELLOW_BACKGROUND + "amarillo" + ANSI_RESET + " están contenidas en la palabra elegida, pero es una posición diferente.");
        System.out.println("- Las letras resaltadas en " + ANSI_GREY_BACKGROUND + "gris" + ANSI_RESET + " no figuran en la palabra elegida.");
        System.out.println("Streak Count: " + streakcount);
    }
    @Override
    //pregunta al usuario la primer palabra
    public void askForFirstGuess() {
        System.out.println();
        System.out.println("Por favor, escriba su primer palabra:");
    }
    @Override
    //
    public String obtainValidUserWord (List<String> wordList, int index) {
        Scanner myScanner = new Scanner(System.in);  
        String userWord = myScanner.nextLine();  
        String modifiedUserWord = userWord.toLowerCase(); 
        modifiedUserWord = removeAccents(modifiedUserWord); 


        //revisa si la longitud de la palabra es correcta y si esta dentro de las posibles palabras.
        while ((modifiedUserWord.length() != 5) || !(wordList.contains(modifiedUserWord))) {
            if ((modifiedUserWord.length() != 5)) {
                System.out.println("La palabra " + userWord + " no tiene 5 letras.");
            } else {
                System.out.println("La palabra " + userWord + " no esta contenida dentro del listado de opciones.");
            }
            // pregunta una nueva palabra
            System.out.println("Por favor, ingrese una nueva palabra de 5 letras.");
            System.out.print("--> " + (index + 1) + ") ");
            userWord = myScanner.nextLine();
            modifiedUserWord = userWord.toLowerCase();
            modifiedUserWord = removeAccents(modifiedUserWord);

            myScanner.close();

        }
        return modifiedUserWord;
    }
    @Override
    // imprime las letras con sus respectivos colores.
    public void printingColouredAlphabet(List<Character> greenLetters, List<Character> yellowLetters, List<Character> greyLetters) {
        char c;

        for (c = 'A'; c <= 'N'; ++c) {
            if (greenLetters.contains(c)) {
                System.out.print(ANSI_GREEN_BACKGROUND + c + ANSI_RESET + " ");
            } else if (yellowLetters.contains(c)) {
                System.out.print(ANSI_YELLOW_BACKGROUND + c + ANSI_RESET + " ");
            } else if (greyLetters.contains(c)) {
                System.out.print(ANSI_GREY_BACKGROUND + c + ANSI_RESET + " ");
            } else {
                System.out.print(c + " ");
            }
        }
        char enie = 'Ñ';

        if (greenLetters.contains(enie)) {
            System.out.print(ANSI_GREEN_BACKGROUND + enie + ANSI_RESET + " ");
        } else if (yellowLetters.contains(enie)) {
            System.out.print(ANSI_YELLOW_BACKGROUND + enie + ANSI_RESET + " ");
        } else if (greyLetters.contains(enie)) {
            System.out.print(ANSI_GREY_BACKGROUND + enie + ANSI_RESET + " ");
        } else {
            System.out.print(enie + " ");
        }

        for (c = 'O'; c <= 'Z'; ++c) {
            if (greenLetters.contains(c)) {
                System.out.print(ANSI_GREEN_BACKGROUND + c + ANSI_RESET + " ");
            } else if (yellowLetters.contains(c)) {
                System.out.print(ANSI_YELLOW_BACKGROUND + c + ANSI_RESET + " ");
            } else if (greyLetters.contains(c)) {
                System.out.print(ANSI_GREY_BACKGROUND + c + ANSI_RESET + " ");
            } else {
                System.out.print(c + " ");
            }
        }

    }
}