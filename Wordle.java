import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.text.Normalizer;


public abstract class Wordle {

    // declaracion de variables y listas de arreglos
    static List<String> languagePossibilities = new ArrayList<>(Arrays.asList("a", "english", "b", "español", "c", "daltonico"));
    protected String chosenWordListFileName;
    protected String chosenWordListWithoutAccentsFileName;
    protected String userDictionaryWithoutAccentsFileName;
    protected String chosenWord;
    protected String chosenWordWithoutAccents;
    protected List<String> chosenWordListWithoutAccents;
    protected List<String> chosenWordList;
    protected List<String> userWordListWithoutAccents;
    protected List<Character> greenLetters = new ArrayList<>();
    protected List<Character> yellowLetters = new ArrayList<>();
    protected List<Character> greyLetters = new ArrayList<>();
    protected String result;
    protected String youWonMessage;
    protected String youLostMessage;

    // declaracion de los colores
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_GREY_BACKGROUND = "\u001B[100m";

    public Wordle() {
    }


    // lee el diccionario y escoge un palabra aleatoria a adivinar
    public List<String> readDictionary(String fileName) {

        List<String> wordList = new ArrayList<>();

        try {
            // abre y lee el archivo de diccionario
            InputStream in = this.getClass().getClassLoader().getResourceAsStream(fileName);
            assert in != null;
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String strLine;

            //lee el archivo linea por linea
            while ((strLine = reader.readLine()) != null) {
                wordList.add(strLine);
            }
            
            in.close();

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        return wordList;
    }

   
    public String getRandomWord(List<String> wordList) {
        Random rand = new Random(); 
        int upperbound = wordList.size();
       
        int int_random = rand.nextInt(upperbound);
        return wordList.get(int_random);
    }

    public String removeAccents(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }

    
    public abstract void printInstructions();

    
    public abstract void askForFirstGuess();

    
    public abstract String obtainValidUserWord (List<String> wordList, int index);

    
    public String replaceChar(String str, char ch, int index) {
        char[] chars = str.toCharArray();
        chars[index] = ch;
        return String.valueOf(chars);
    }

    public void loopThroughSixGuesses(List<String> wordList) {

        for (int j = 0; j < 6; j++) {

            System.out.print("--> " + (j + 1) + ") ");

            String userWord = obtainValidUserWord(wordList, j);

            String chosenWordWithoutGreensAndYellows = chosenWordWithoutAccents;

            
            if (userWord.equals(chosenWordWithoutAccents)) {
                System.out.println((result + ANSI_GREEN_BACKGROUND + userWord.toUpperCase() + ANSI_RESET));
                System.out.println();
                System.out.println(youWonMessage);
                System.out.println();
                break;
            } else {

                System.out.print(result);

               

                String userWordWithoutGreensAndYellows = userWord;
                String[] positionColors = new String[5];

                // revisa las letras verdes
                for (int i = 0; i < 5; i++) {
                    if (userWord.charAt(i) == chosenWord.charAt(i)) {
                        userWordWithoutGreensAndYellows = replaceChar(userWordWithoutGreensAndYellows, '0', i);
                        chosenWordWithoutGreensAndYellows = replaceChar(chosenWordWithoutGreensAndYellows, '0', i);
                        // System.out.print(ANSI_GREEN_BACKGROUND + userWord.toUpperCase().charAt(i) + ANSI_RESET);
                        greenLetters.add(userWord.toUpperCase().charAt(i));
                        positionColors[i] = ANSI_GREEN_BACKGROUND;
                    }
                }

                // revisa las letras amarillas
                for (int i = 0; i < 5; i++) {
                    if (userWordWithoutGreensAndYellows.charAt(i) == '0') {

                    } else if (chosenWordWithoutGreensAndYellows.indexOf(userWordWithoutGreensAndYellows.charAt(i)) != -1) {
                        chosenWordWithoutGreensAndYellows = replaceChar(chosenWordWithoutGreensAndYellows, '0', chosenWordWithoutGreensAndYellows.indexOf(userWordWithoutGreensAndYellows.charAt(i)));
                        userWordWithoutGreensAndYellows = replaceChar(userWordWithoutGreensAndYellows, '0', i);
                        yellowLetters.add(userWord.toUpperCase().charAt(i));
                        positionColors[i] = ANSI_YELLOW_BACKGROUND;
                    } else {
                        greyLetters.add(userWord.toUpperCase().charAt(i));
                        positionColors[i] = ANSI_GREY_BACKGROUND;
                    }
                }

                //imprime la palabra con el color
                for (int i = 0; i < 5; i++) {
                    System.out.print(positionColors[i] + userWord.toUpperCase().charAt(i) + ANSI_RESET);
                }
                System.out.println();


                //imprime el alfabeto con las letras y su respectivo color
                printingColouredAlphabet(greenLetters, yellowLetters, greyLetters);

            }

            System.out.println();
            if (j == 5) {
                System.out.println();
                System.out.println(youLostMessage + chosenWord.toUpperCase() + ".");
                System.out.println();
            }
        }
    }


    public abstract void printingColouredAlphabet(List<Character> greenLetters, List<Character> yellowLetters, List<Character> greyLetters);

    public void play () {
        chosenWordList = this.readDictionary(chosenWordListFileName);
        chosenWordListWithoutAccents = this.readDictionary(chosenWordListWithoutAccentsFileName);
        userWordListWithoutAccents = this.readDictionary(userDictionaryWithoutAccentsFileName);

        chosenWord = getRandomWord(chosenWordList);

        // elimina los acentos
        chosenWordWithoutAccents = removeAccents(chosenWord);

        // instrucciones del juego
        this.printInstructions();

        
        this.askForFirstGuess();

        this.loopThroughSixGuesses(userWordListWithoutAccents);
    }
}