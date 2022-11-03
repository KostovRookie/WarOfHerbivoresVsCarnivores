import java.security.SecureRandom;
import java.util.Random;
import java.util.Scanner;


public class WarOfHerbivoresVsCarnivores {
    static int fence;  // to use it when the player nears the border and check for indexoutofbounds
    static int totalEaten = 0;
    static int kobeEaten = 0;
    static int hunterHere;
    static int kobeBeef;
    static int emptySpace;
    static int carnivore;
    static int blackAngus;
    static int blackAngusEaten = 0;


    static Scanner sc = new Scanner(System.in);

    static int[][] openYard = new int[][]{                  // This is my simple 2D Array of 10 x 10 tiles, i use this map to move the player and beef around
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 5, 2, 2, 2, 2, 2, 2, 5, 0},
            {0, 5, 5, 2, 2, 2, 5, 2, 2, 0},
            {0, 4, 5, 5, 5, 2, 5, 5, 2, 0},
            {0, 5, 3, 3, 3, 5, 5, 3, 5, 0},
            {0, 2, 5, 2, 2, 2, 2, 2, 2, 0},
            {0, 3, 5, 5, 4, 2, 3, 2, 2, 0},
            {0, 4, 5, 5, 5, 2, 2, 4, 2, 0},
            {0, 5, 5, 2, 2, 2, 5, 4, 5, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};
    static boolean wannaPlayAGame;
    static int x = openYard[5][0];
    static int y = openYard[0][5];
    static int playerPos = openYard[x][y];
    static int life = 90;                         // this is the amount of lives available for the game


    public static void startGame() {
        System.out.println("Type, up, down, left, or right, to move.");
        while (wannaPlayAGame) {  // while wannaPlayGame is true, play the game and end it when you eat all the herbivores or lives are spent
            move();


        }

    }

    public static void main(String[] args) {
        String isPlaying = "";
        System.out.println("Electronic Arts Presents:");
        System.out.println("Game is simple, start the game by typing Start, after this, you will create a field 10x10 with a yard, full" +
                " of animals that you must eat. There are tasty Kobe and Black Angus herbivores. You win if you eat all of them. Actually the game plays itself" +
                " as there is a random chance - 25% that you - the carnivore can move in any direction - left,right,up,down. If you find a tasty beef, you like it and" +
                " carry on, however if you find the owner, he shoots you and you lose a life. You have limited lives. Good LucK! ");
        System.out.println("Type Start");
        isPlaying = sc.nextLine();
        if (isPlaying.equals("Start")) {                                  // simple String check to initiate the game
            wannaPlayAGame = true;
            startGame();                                                     // the main function of the game

        } else {
            System.out.println("Why you leave me???");                      // If the player declines the start or types something wrong
            System.exit(1);
        }
    }


    public static void legend() {    // here i am declaring the names-integers of the population of the matrix
        fence = 0;
        emptySpace = 2;
        hunterHere = 3;
        blackAngus = 4;
        kobeBeef = 5;
        carnivore = 6;


        System.out.println(playerPos);   // I am showing the next movement position of the carnivore-player for reference
        if (playerPos == fence) {
            System.out.println("Thats a fence. You will return 2 positions back.");

        }
        if (playerPos == emptySpace) {
            System.out.println("This is empty space, nothing to eat or fight here");
        }
        if (playerPos == hunterHere) {
            monsterEncounter();
        }
        if (playerPos == blackAngus) {
            BlackAngusEncounter();
        }
        if (playerPos == kobeBeef) {
            TastyKobeEncounter();
        }

    }

    public static void move() {

        Random r = new SecureRandom();
        //movement = sc.nextLine();          //

        // We can make this a real game if we ask the user to input string in sc.nextLine something like : "go left", "go right"
        // so he can guess the correct path to the cattle. Now i am using random int 0 - 3 which is 25% probability which path it will choose.
        // This is why tha game plays itself
        switch (r.nextInt(4)) {
            case 0:
                x = x + 1;
                if (x >= 10)
                    x = x - 2;
                playerPos = openYard[x][y];
                legend();
                break;
            case 1:
                x = x - 1;
                if (x < 0)
                    x = x + 2;
                playerPos = openYard[x][y];
                legend();
                break;
            case 2:
                y = y - 1;
                if (y < 0)
                    y = y + 2;
                playerPos = openYard[x][y];
                legend();
                break;
            case 3:
                y = y + 1;
                if (y >= 10)
                    y = y - 2;
                playerPos = openYard[x][y];
                legend();
                break;

        }
    }

    public static void monsterEncounter() {
        if (life > 0) {

            System.out.println("You found the HUNTER.. YOU LOSE A LIFE, but you use cheats and go back up again");
            life = life - 1;
            System.out.println("You have: " + life + " life left! Don't die..");
        } else {
            System.out.println("GG EZ, all lives lost");
            System.exit(1);   // end game when all the lives are spent
        }
        for (int[] x : openYard) {
            for (int y : x) {
                System.out.print(y + " ");

            }
            System.out.println();

        }

    }

    public static void BlackAngusEncounter() {
        System.out.println("TASTY MEAT BLACK ANGUS");
        blackAngusEaten += 1;
        openYard[x][y] = 2;    // replace old tile with something 
    }

    public static void TastyKobeEncounter() {
        int total = 24;    // this is the total amount of herbivores out there, both kobe and black angus alike
        System.out.println("NICE, you found kobe beef");
        kobeEaten += 1;


        total = total - kobeEaten - totalEaten;
        System.out.println("You ate. " + total + " animals to go!");
        openYard[x][y] = 2;    // replace old tile with something 
        blackAngusEaten -= 1;

        if (total == 0) {
            System.out.println("GG! You ate all the black angus and kobe beef, successfully avoided the hnuter and still have" +
                    " lives to spare! Great."); // when we run out of beef
            System.exit(1);   // this is to end the program
        } else {
            System.out.println("No luck");
        }
        for (int[] x : openYard) {     //for each to show the map and keep track of game
            for (int y : x) {
                System.out.print(y + " ");

            }
            System.out.println();

        }


    }
}