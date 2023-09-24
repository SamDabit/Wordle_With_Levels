import java.util.*;
//import java.io.*;

//TODO add a feature to keep track of the number of times a player has restarted & retried the game
//TODO track & print points from each instance of the game

/**
 * @version 0.9
 * @created 3/25/22
 * @author Samir Dabit
 */
public class Main {
    final private static int MAX_ROUNDS = 13;
    final private static int START_ROUND = 3;
    final static double EASY_MULT = 0.75; // If easy mode is selected, the score will be multiplied by this
    private static Points points = new Points();
    private static boolean hasRestarted = false;
    //private TextDemo textDemo = new TextDemo();

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        if(!hasRestarted) {
            PrintRules();
            //sssssssssssssssssTextDemo.createAndShowGUI();
        }
        Scanner input = new Scanner(System.in);
        String userInput = "";

        // do-while loop | Restarts game
        do {

            userInput = "";
            boolean easyMode = false;
            points.SetTotalPoints(0);
            points.ClearRoundPoints();


            // -- EASY_MODE --
            System.out.print("Easy Mode? (y/n) ");
            userInput = input.nextLine().trim().toLowerCase();
            if(userInput.equalsIgnoreCase("y")) {
                easyMode = true;
                System.out.println("|Easy Mode Activated|");
            }
            // -- END easy_mode --

            //Start of each round | Default: starts at 3. runs while i < 16
            for(int i = START_ROUND; i < (MAX_ROUNDS + START_ROUND); i++) {
                Game game = new Game(i); // new Game object every round
                int numTries = 0;
                System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-\n\t  Round " + (i - 2));

                //System.out.println("Answer: " + game.GetWordAnswer()); //TEST: Print answer for current round

                System.out.println("     | " + i + "-letter words |");
                game.Setup();
                System.out.println("---------------------------");

                //Start of each try
                while(numTries < 6) {
                    System.out.print("("+ (numTries + 1) +") Enter your guess: ");

                    // ** START CHECK **
                    do {

                        userInput = input.nextLine();
                        if(!userInput.equals(null)) {
                            userInput = (userInput).trim().toLowerCase(); //trims whitespace off ends
                        }

                        //------ START Test Commands -------
                        // TEST COMMAND: sets highScore to something | Syntax: '/s_hs #'
                        if(userInput.startsWith("/s_hs") && userInput.length() > 5 && !userInput.equals(null)) {
                            if( !userInput.substring(5).trim().equals(null) && ContainsNumbers(userInput.substring(5).trim()) ) {

                                //TEST
								/*
								String tempString = userInput.substring(5).trim();
								System.out.println("substring(5): " + tempString);
								double tempDouble = Double.parseDouble(tempString);
								System.out.println("tempDouble: " + tempDouble);
								*/
                                //END TEST

                                points.SetHighScore( Double.parseDouble(userInput.substring(5).trim()) );
                                continue;
                            }
                        }

                        // TEST COMMAND: prints highScore | Syntax: '/p_hs'
                        if(userInput.equals("/p_hs")) {
                            System.out.println("Current hi-score: " + points.GetHighScore());
                            continue;
                        }
                        // --------- END Test Commands ------

                        // CHECK: /RESTART
                        if(userInput.equals("/restart")) { //checks if user wants to restart
                            System.out.print("Are you sure you would like to restart?\n"
                                    + "Type 'y' or 'yes' for yes, anything else for no: ");
                            userInput = input.nextLine();

                            if( userInput.equals("y") || userInput.equals("yes")) { // double checks for restart
                                hasRestarted = true;
                                System.out.println("\t[GAME RESTARTED]\n------------------------------------");
                                main(args);
                            }

                            // if no restart, continue. Essentially does the same thing as the start of the while loop
                            System.out.println("---------------------------");
                            System.out.print("("+ (numTries + 1) +") Enter your guess: ");
                            continue;
                        }//end RESTART

                        // CHECK: /EXIT
                        if(userInput.equalsIgnoreCase("/exit") ) {// Checks: if user wants to exit
                            System.out.println("Quit program.");
                            input.close();
                            EndGame();
                            return;
                        }// end EXIT

                        //added in PC version on 4/3/22: isBlank() changed to isEmpty()

                        // CHECK: VALIDATION
                        if( userInput.isEmpty() || ContainsNumbers(userInput)) { //Checks: if blank OR contains #s
                            System.out.print("INVALID RESPONSE: Input must contain letters. Please retry: ");
                        } else if( (userInput.length() < game.GetWordLength()) ) { // Checks: if userInput length is too short
                            System.out.print("INVALID RESPONSE: Input must be at least " + i + " letters long. Please retry: ");
                        } else if(!game.GetWordObj().GetWordList(i).contains(userInput)) { //Checks: if userInput is contained in ArrayList/valid answer
                            System.out.print("INVALID RESPONSE: Not a possible answer. Please retry: ");
                        }

                    } while(userInput.isEmpty() ||  (userInput.length() < game.GetWordLength()) || ContainsNumbers(userInput) || !(game.GetWordObj().GetWordList(i).contains(userInput)) );
                    // ** END CHECK **



                    game.SetGameBoard(userInput, numTries, i);
                    game.PrintGameBoard();
                    //game.PrintGameBoardGUI(userInput);
                    if(easyMode) {
                        Points.PrintOccurrences(numTries, i, game, userInput);
                    }

                    if( game.IsWordCorrect(userInput) ) {
                        System.out.println("****** CORRECT ******\n**** NEXT ROUND ****");
                        break;
                    } else {
                        //System.out.println("Incorrect");
                    }//end else
                    System.out.println("");


                    // ------- Points section ---------
                    int curNumGreens = game.GetNumGreens(userInput);
                    int numExtraYellows = game.GetNumExtraYellows(userInput);

                    //Testing
                    //System.out.println("GetNumGreens(): " + curNumGreens);
                    //System.out.println("GetNumExtraYellows(): " + numExtraYellows);
                    //points.SetRoundPoints( (points.CalcWordPoints(i - 3, numTries + 1, game, userInput, curNumGreens, curNumYellows) + points.GetRoundPoints(i - 3) ), i - 3);
                    //System.out.println("" + points.CalcWordPoints(i - 3, numTries + 1, game, userInput, curNumGreens, curNumYellows)); //testing
                    //end Testing

                    //points.UpdateRoundPoints(i, numTries, game, userInput, curNumGreens, numExtraYellows, easyMode);
                    //double num = points.GetRoundPoints(i - 3);
                    double num = Points.CalcWordPoints(i, numTries, game, userInput, curNumGreens, numExtraYellows);
                    points.UpdateRoundPoints(i, num , easyMode);

                    // Easy Mode only gives 75% of points
						/*
						if(easyMode) {
							num *= EASY_MULT;
						}
						*/

                    points.SetTotalPoints(num + points.GetTotalPoints());
                    //System.out.println("roundNum / tryNum: " + (double)(i - 2) / (double)(numTries + 1) );
                    System.out.printf(">Round Points: %.2f \n", num);
                    System.out.printf(">Total Points: %.2f\n", points.GetTotalPoints() );
                    // ------- end points section -------

                    numTries++;
                    System.out.println("---------------------------");

                    //Set high score after every round if newTotalPoints > old high score
                    if(points.GetTotalPoints() > points.GetHighScore()) {
                        points.SetHighScore(points.GetTotalPoints());
                    }

                } // end while
                System.out.println("Round " + (i - 2) + " Points: " + points.GetRoundPoints(i - 3));

                /* Ends game if user gets it incorrect on 6th try
                 * Maybe have function: GameLose()
                 */
                if( !(game.IsWordCorrect(userInput)) ) {
                    System.out.println("You Lose! L bozo\n"
                            + "| Answer was '" + game.GetWordAnswer() + "' |");
                    System.out.println("Enter 'y' if you would like to retry? (y/n)");
                    //Checks if user wants to retry
                    if(input.nextLine().trim().equalsIgnoreCase("y")) {
                        hasRestarted = true;
                        System.out.println("-----------------------------------");
                        main(args);
                    } else {
                        EndGame();
                        input.close();
                        return;
                    }
                }


            }// end for

            //	if user makes it here, they beat the game!
            //	EndGame function
            System.out.println("-------------------------------");
            System.out.println("Congrats! You beat the game!");
            EndGame();

            //Checks if user wants to retry
            System.out.println("Enter 'y' if you would like to retry? (y/n)");
            userInput = input.nextLine().trim();
            if(userInput.equals("y")) {
                hasRestarted = true;
            }

        } while(userInput.equalsIgnoreCase("y"));

        input.close();

    }// end main

    //===================== METHODS =====================

    /**
     *
     * @return
     */
    public static int GetMAXROUNDS() {
        return MAX_ROUNDS;
    }

    /**
     *
     * @return
     */
    public static int GetSTARTROUND() {
        return START_ROUND;
    }

    /**
     *
     * @return
     */
    public static double GetEASYMULT() {
        return EASY_MULT;
    }

    /**
     *
     * @param wordLength
     */
    public void Play(int wordLength) {
        System.out.println("Word Length: " + wordLength);
    }

    /**
     *
     * @param str
     * @return
     */
    public static boolean ContainsNumbers(String str) {
        for(int i = 0; i < str.length(); i++) {
            if( Character.isDigit(str.charAt(i)) ) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     */
    public static void PrintRules() {
        System.out.println("=============== RULES ===============\n"
                + "\t| >> GAMEPLAY << |\n" //GAMEPLAY
                + "> A random word with the letter length will be chosen.\n"
                + "> If a letter is surrounded by brackets [], it is in the correct position.\n"
                + "> If a letter is surrounded by asterisks *, it is contained in the answer but NOT in the correct position.\n"
                + "> You have 6 tries in each round to get it right. Good luck!\n"
                + "\n\t| >> POINTS << |\n" //POINTS
                + "> +2 pts for correct each position\n"
                + "> +1 pt for each contained but incorrect positions\n"
                + "> Your points are multiplied (roundNum / tryNum).\n"
                + "  (This means later rounds are worth more points!)\n"
                + "\n\t| >> EASY MODE << |\n" //EASY MODE
                + "> Tells you how many times each letter you input appears in the answer!\n"
                + "> You only receive 75% of the points.\n"
                + "\n\t| >> COMMANDS << |\n" //COMMANDS
                + "> Enter '/restart' to restart the game.\n"
                + "> Enter '/exit' to quit the game.\n"
                + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }

    /**
     *
     */
    public static void EndGame() {

        //Prints all points from each round
        for(int p = 0; p < points.GetRoundPoints().length; p++) {
            System.out.println("Round " + (p + 1) + ": " + points.GetRoundPoints(p));
        }
        System.out.println("Total Points: " + points.GetTotalPoints());

        // Check if total points > highScore, sets highScore to that
        if(points.GetTotalPoints() > points.GetHighScore()) {
            points.SetHighScore(points.GetTotalPoints()); //Sets highScore to the totalPoints
        }
        System.out.println("High-Score: " + points.GetHighScore() + "\n---------------------------");

        points.SaveMemory();
    }


}//end Main