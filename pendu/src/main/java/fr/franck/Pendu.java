package fr.franck;

import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.nio.charset.Charset;

public class Pendu {

    String randomWord;
    static List<String> wordListFr;
    StringBuffer maskedWordInGame;
    String[] splitWord;
    String[] maskedArray;
    String maskedWord;
    String playerLetter;
    String nomJoueur;
    int winningCount;
    int losingCount;
    int score;
    boolean isWin;
    boolean isLost;

    // Intro
    public void displayIntro() {
        System.out.println();
        System.out.println("*****************************************************************");
        System.out.println("*****************************************************************");
        System.out.println("*******************                           *******************");
        System.out.println("*******************        JEU DU PENDU       *******************");
        System.out.println("*******************                           *******************");
        System.out.println("*****************************************************************");
        System.out.println("*****************************************************************");
        System.out.println();

    }

    // Demande le nom du joueur.
    public void enterYourName() {
        losingCount = 0; // Compteur d'erreur, à zéro en début de partie.
        Scanner clavier1 = new Scanner(System.in);

        System.out.println();
        System.out.println("*****************************************************************");
        System.out.println("*****************************************************************");
        System.out.println("*****                                                       *****");
        System.out.println("*****   Bienvenue au jeu du Pendu le mieux codé du monde.   *****");
        System.out.println("*****                                                       *****");
        System.out.println("*****************   Veuillez entrer votre nom   *****************");
        System.out.println("*****************                               *****************");
        System.out.println("*****************************************************************");
        System.out.println("*****************************************************************");
        System.out.println();

        this.nomJoueur = clavier1.next();

        System.out.println();
        System.out.println("Ok " + this.nomJoueur + ",");
        System.out.println("Tu dois deviner un mot.");
        System.out.println("Mais attention, tu n'as le droit qu'à 7 erreurs,");
        System.out.println("à la huitième la partie est perdue...");
        System.out.println();
        System.out.println("Prêt à jouer?");
        System.out.println();
        System.out.println("Let's GO!!!");
        System.out.println();
    }

    // Donne un mot, tiré aléatoirement d'un fichier texte.
    public String generateRandomWord() {

        try {
            wordListFr = Files.readAllLines(Paths.get("words.txt"), Charset.defaultCharset());

        } catch (IOException e) {
            e.printStackTrace();
        }
        Random random = new Random();
        this.randomWord = wordListFr.get(random.nextInt(wordListFr.size()));

        return this.randomWord; // Par exemple: "hello"
    }

    public String generateMaskedWord(String randomWord) {

        this.maskedWord = new String();

        for (int i = 0; i < randomWord.length(); i++) {
            this.maskedWord += '*'; // Donne une chaine de caractere "*" de la même longueur que "hello": "*****"
        }
        splitWord = randomWord.split(""); // [h, e, l, l, o]
        maskedArray = maskedWord.split(""); // [*, *, *, *, *]
        return this.maskedWord;
    }

    // Le joueur rentre une lettre dans la console.
    public void playerTry() {

        Scanner clavier3 = new Scanner(System.in);
        Pattern p = Pattern.compile("[a-z]");

        System.out.print("Entrer une lettre: ");
        playerLetter = clavier3.next(); // Demande d'une lettre par la console.
        System.out.println();

        Matcher m = p.matcher(playerLetter);
        boolean check = m.find();

        if (playerLetter.length() > 1) { // Si on tape plus d'une lettre, ou qu'elle n'est pas en minuscule.
            System.out.println("Veuillez ne taper qu'une seule lettre.");
        }

        if (!check) {
            System.out.println("Veuillez ne taper que des lettres minuscules.");
        }
    }

    // Verifie si la lettre rentrée correspond à une lettre du mot.
    public void checkingTry() {

        boolean lettreTrouvée = false;

        for (int i = 0; i < splitWord.length; i++) {
            if (splitWord[i].equals(playerLetter)) {
                maskedArray[i] = splitWord[i]; // Si la lettre donnée correspond, l'insère dans l'array "masquée" à
                                               // l'index spécifié.
                lettreTrouvée = true;
            }
        }

        if (lettreTrouvée == true) {
            System.out.println("Bien joué " + this.nomJoueur + "!");
            winningCount++;
        } else {
            if (losingCount == 3) {
                System.out.println("Pas d'bol...");
                losingCount++;
                System.out.println("Echec: " + losingCount + ", prudence...");
            } else if (losingCount == 5) {
                System.out.println("Pas d'bol...");
                losingCount++;
                System.out.println("Echec: " + losingCount + ", ça sent le sapin cette histoire...");
            } else {
                System.out.println("Pas d'bol...");
                losingCount++;
                System.out.println("Echec: " + losingCount);
            }

            System.out.println();
        }
        maskedWordInGame = new StringBuffer();
        for (int i = 0; i < maskedArray.length; i++) { // Afficher le mot masqué avec les lettres trouvées.
            maskedWordInGame.append(maskedArray[i]);
        }
        System.out.println();
        System.out.println(maskedWordInGame);
        System.out.println();
    }

    public void scoring() {
        score = winningCount * 3789; // Pour que le score paraisse impressionnant...
        System.out.println("Your Score : " + score + ".");
    }

    // Condition de victoire.
    public boolean winningGame() {

        isWin = false;

        if (randomWord.equals(maskedWordInGame.toString())) {
            isWin = true;
            System.out.println();
            System.out.println("CONGRATULATION!!!");
            System.out.println("YOU WIN!!!");
            System.out.println();
        }
        return isWin;
    }

    // Condition de défaite.
    public boolean losingGame() {

        isLost = false;

        if (losingCount > 7) {
            isLost = true;
            System.out.println();
            System.out.println("AAAARRRGHHHH!!!");
            System.out.println();
            System.out.println("*******************************************************************");
            System.out.println("*******************************************************************");
            System.out.println("*****                                                         *****");
            System.out.println("*****                      .AMMMMMMMMMA.                      *****");
            System.out.println("*****                    AV. :::.:..|::..MA                   *****");
            System.out.println("*****                   A  :..         : .: A                 *****");
            System.out.println("*****                 M  .    .::.:   ::.  . .M               *****");
            System.out.println("*****                 M  :   :.        ..:   .M               *****");
            System.out.println("*****                 V : :.:::         :.:  :V               *****");
            System.out.println("*****                A`  A    ..:.. . ..:.   A '              *****");
            System.out.println("*****                V  (::.   .::)  (::.  .:) M              *****");
            System.out.println("*****                A   .VMMMMMZ :.Y.:AMMMMM `A`             *****");
            System.out.println("*****              M'  . (MMMMMMV   |  VMMMM) ..:M            *****");
            System.out.println("*****              V.:. ...VMMMV   JAL  VMV.  .: V            *****");
            System.out.println("*****               V.` .:. ...   JMAML  . .:..:J             *****");
            System.out.println("*****                 VVMMA:`...' L|^|J`. .:MMJ               *****");
            System.out.println("*****                     `M:` ..   |    .J                   *****");
            System.out.println("*****                      `M:`.        .:J                   *****");
            System.out.println("*****                       `:.I,I,I,I,I.M                    *****");
            System.out.println("*****                         `-!_!_!_!-'                     *****");
            System.out.println("*****                                                         *****");
            System.out.println("*************************      YOU FAILED!    *********************");
            System.out.println("*************************                     *********************");
            System.out.println("*******************************************************************");
            System.out.println("*******************************************************************");
            System.out.println();
            System.out.println("Réponse: " + randomWord);
        }
        return isLost;
    }

    // Méthode globale.
    public void letSPlay() {

        displayIntro();

        enterYourName();

        generateRandomWord();

        // System.out.println(randomWord); // Si je veux afficher le mot pendant la
        // partie #TRICHE.
        generateMaskedWord(randomWord);
        System.out.println(maskedWord);

        while (isWin == false && isLost == false) {

            playerTry();
            checkingTry();
            winningGame();
            losingGame();
        }

        scoring();

        System.out.println();
        System.out.println("*****************************************************************");
        System.out.println("*****************************************************************");
        System.out.println("*******************                           *******************");
        System.out.println("*******************         GAME OVER         *******************");
        System.out.println("*******************                           *******************");
        System.out.println("*****************************************************************");
        System.out.println("*****************************************************************");
        System.out.println();

    }

}
