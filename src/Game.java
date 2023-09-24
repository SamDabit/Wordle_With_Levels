//import java.util.*;
//import java.io.*;

//TODO Fix SetBoard function to not have any bugs with printing yellows/greens algorithm

/**
 *
 * @author Samir Dabit
 *
 */
public class Game {
    private final static int MAX_TRIES = 6;
    private static int wordLength;
    private int round;
    private static String wordAnswer; //made into static so that IsLetterCorrect can be used in static way
    private String[][] gameBoard; //= new String[MAX_TRIES][wordLength];
    private Word word = new Word();
    private boolean[] foundLetters;

    public Game(int newWordLength) {
        //Word word = new Word();
        wordLength = newWordLength;
        wordAnswer = word.GetRandomWord(wordLength).toLowerCase();
        this.gameBoard = new String[MAX_TRIES][wordLength];
        this.foundLetters = new boolean[wordLength];

        //Sets all letters to be unfound at the start
        for(int i = 0; i < wordLength; i++) {
            this.foundLetters[i] = false;
        }
    }

    public static int GetMAXTRIES() {
        return MAX_TRIES;
    }

    /**
     * Returns wordLength
     * @return wordLength
     */
    public int GetWordLength() {
        return wordLength;
    }

    /**
     * Sets wordLength to the parameter newWordLength
     * @param
     */
    public void SetWordLength(int newWordLength) {
        wordLength = newWordLength;
    }

    /**
     *
     * @return
     */
    public String GetWordAnswer() {
        return wordAnswer;
    }

    /**
     *
     * @param wordAnswer
     */
    public void SetWordAnswer(String newWordAnswer) {
        wordAnswer = newWordAnswer;
    }

    /**
     *
     * @return
     */
    public int GetRound() {
        return this.round;
    }

    /**
     *
     * @param round
     */
    public void SetRound(int round) {
        this.round = round;
    }

    /**
     *
     * @param bool
     * @param i
     */
    public void SetFoundLetters(boolean bool, int i) {
        this.foundLetters[i] = bool;
    }

    public boolean GetFoundLetters(int i) {
        return this.foundLetters[i];
    }

    /**
     *
     * @return
     */
    public boolean[] GetFoundLettersArray() {
        return this.foundLetters;
    }

    /**
     *
     * @return
     */
    public String[][] GetGameBoard(){
        return this.gameBoard;
    }

    /**
     *
     * @return
     */
    public Word GetWordObj() {
        return this.word;
    }

    /**
     *
     * @param userWord
     * @param roundNum
     */
    public void SetGameBoard(String userWord, int numTries, int roundNum) {

        //Case 1: Letter is not contained & Letter is not correct
        //Case 2: Letter is contained only there & Letter is correct
        //Case 3: Letter is contained only there & Letter is incorrect (wrong position)
        //Case 4: Letter is contained in multiple places & Letter is incorrect in all
        //Case 4: Letter is contained in multiple places & Letter is correct in 1 position
        //Case 5: Letter is contained in multiple places & Letter is correct in multiple positions
        int numGreens = 0;
        int numYellows = 0;
        int count = 0;

        // --- 1st Attempt --- (Problem: sets yellow for any and all occurrences that aren't correct)
        int[] numOccurInWord = new int[wordLength];
        int[] numOccurInAnswer = new int[wordLength];

        //** CHECK FUNCTION **
        for(int i = 0; i < wordLength; i++) {
            //adds occurrence of each word to another array with corresponding indices
            numOccurInWord[i] = FindNumOccurrences(userWord, userWord.charAt(i));
            numOccurInAnswer[i] = FindNumOccurrences(wordAnswer, userWord.charAt(i));


            if(IsLetterCorrect(userWord, i)) {
                gameBoard[numTries][i] = "[" + String.valueOf(userWord.toUpperCase().charAt(i)) + "]";
                //foundLetters[i] = true;
                numGreens++;
                //this.foundLetters[i] = true;
            } else if(IsLetterContained(userWord, i)) {
                gameBoard[numTries][i] = "*" + String.valueOf(userWord.toUpperCase().charAt(i)) + "*";
                numYellows++;
            } else {
                gameBoard[numTries][i] = " " + String.valueOf(userWord.toUpperCase().charAt(i)) + " ";
            }
        }

        //goes through numOccur[] and corrects output for values with more than one occurrence
        for(int i = 0; i < wordLength; i++) {
            int numOccurAnswer = FindNumOccurrences(wordAnswer, userWord.charAt(i));
            int numOccurUser = FindNumOccurrences(userWord, userWord.charAt(i));
            int difference = numOccurUser - numOccurAnswer;

            if(difference > 0) { // originally difference < 0
                //System.out.println("----\n difference stuff is running \n----");
                if(numOccurInWord[i] > 1) {
                    //Correct the output
                    for(int k = 0; k < wordLength; k++) {
                        if(!IsLetterCorrect(userWord, i) && (count < difference) && IsLetterContained(userWord, i)) { //changed (numOccur[i] + i + 1) to (numOccur[i] - 1)
                            gameBoard[numTries][i] = " " + String.valueOf(userWord.toUpperCase().charAt(i)) + " ";
                            numYellows--;
                            count++;
                        }//end if
                    }//end for
                }// end if
            }// end if
        }// end for

        if(numYellows < 0) {
            numYellows = 0;
        }

        //System.out.println("Word Points: " + Points.CalcWordPoints(numTries, roundNum, this, userWord, numGreens, numYellows)) ; //needs to be done in main probably

        //Used for testing
        //Points.PrintOccurrences(numTries, roundNum, this, userWord, numGreens, numYellows);

    }

    /**
     * Returns number of occurrences of a certain letter in a given word in the wordAnswer
     * @param userWord
     * @return
     */
    public int FindNumOccurrences(String word, char target) {
        int numOccurrence = 0;

        for(int i = 0; i < wordLength; i++) {
            // if (target == the character at that index in the answer)
            if( String.valueOf(target).toLowerCase().equals(String.valueOf(word.charAt(i))) ) {
                numOccurrence++;
            }
        }
        return numOccurrence;
    }// end function

    /**
     * Finds each index of a target char contained within a certain word
     * @param userWord
     * @param target
     * @return
     */
    public int[] GetOccurrenceIndexes(String word, char target){
        word = word.toLowerCase(); // makes word lowerCase
        target = (String.valueOf(target).toLowerCase() ).charAt(0); //makes target lowerCase
        int[] occurrenceIndexes = new int[wordLength];
        int numOccur = FindNumOccurrences(wordAnswer, target);
        int trueIndex = 0;

        for(int i = 0; i < numOccur; i++) {
            if(word.charAt(i) == target) {
                occurrenceIndexes[i] = trueIndex;
                i--;
                trueIndex++;
            } else {
                occurrenceIndexes[i] = -1;
            }

        }

        return occurrenceIndexes;
    }

    /**
     *
     * @param userWord
     * @return
     */
    public boolean IsWordCorrect(String userWord) {
        if (userWord.toLowerCase().equals(wordAnswer.toLowerCase())) {
            return true;
        }
        return false;
    }

    /**
     *
     * @param userWord
     * @param index
     * @return
     */
    public boolean IsLetterCorrect(String userWord, int index) {
        if (userWord.toLowerCase().charAt(index) == wordAnswer.toLowerCase().charAt(index)) {
            return true;
        }
        return false;
    }

    /**
     * Checks if the letter at the given index in the userWord is in the answer
     * @param userWord
     * @param index
     * @return True if letter is contained in answer; False if not
     */
    public static boolean IsLetterContained(String userWord, int index) {
        if(wordAnswer.contains(String.valueOf((userWord.toLowerCase().charAt(index)) ))) {
            return true;
        }
        return false;
    }//end IsLetterContained

    /**
     * Prints entire gameBoard 2D array as a grid (wordLength x MAX_TRIES)
     */
    public void PrintGameBoard() {
        for (int k = 0; k < MAX_TRIES; k++) {
            System.out.print("\t");
            for (int i = 0; i < wordLength; i++) {
                System.out.print(gameBoard[k][i] + " ");
            }// end for

            System.out.println();
        }// end for
    }// end PrintGameBoard

    public void PrintGameBoardGUI(String userWord) {
        TextDemo.PrintGameBoardGUI(userWord, this);
    }

    /**
     * Sets up gameBoard
     */
    public void Setup() {

        // Sets up game board with all empty values
        for (int k = 0; k < MAX_TRIES; k++) {
            for (int i = 0; i < wordLength; i++) {
                gameBoard[k][i] = " - ";
            }
        }

        PrintGameBoard();

        //Sets the foundLetters list to be all false at the start of the round
        for(int i = 0; i < wordLength; i++) {
            this.foundLetters[i] = false;
        } //end for
    }//end Setup

    /**
     * Returns how many letters in a given word are in the correct position
     * @param userWord
     * @param numTries
     * @param roundNum
     * @return
     */
    public int GetNumGreens(String userWord) {
        int numGreens = 0;

        for(int i = 0; i < wordLength; i++) {
            if(IsLetterCorrect(userWord, i)) {
                numGreens++;
            }
        }

        return numGreens;
    }//end GetNumGreens

    /**
     *
     * @param userWord
     * @return
     */
    public int GetNumExtraYellows(String userWord) {
        int numYellows = 0;
        int count = 0;
        int[] numOccurInWord = new int[wordLength];
        int[] numOccurInAnswer = new int[wordLength];

        //** CHECK FUNCTION **
        for(int i = 0; i < wordLength; i++) {
            //adds occurrence of each word to another array with corresponding indices
            numOccurInWord[i] = FindNumOccurrences(userWord, userWord.charAt(i));
            numOccurInAnswer[i] = FindNumOccurrences(wordAnswer, userWord.charAt(i));

            if(IsLetterContained(userWord, i) && !IsLetterCorrect(userWord, i)) {
                numYellows++;
            }
        }

        //goes through numOccur[] and corrects output for values with more than one occurrence
        for(int i = 0; i < wordLength; i++) {
            int numOccurAnswer = FindNumOccurrences(wordAnswer, userWord.charAt(i));
            int numOccurUser = FindNumOccurrences(userWord, userWord.charAt(i));
            int difference = numOccurUser - numOccurAnswer;

            if(difference > 0) { // originally difference < 0
                //System.out.println("----\n difference stuff is running \n----");
                if(numOccurInWord[i] > 1) {
                    //Correct the output
                    for(int k = 0; k < wordLength; k++) {
                        if(!IsLetterCorrect(userWord, i) && (count < difference) && IsLetterContained(userWord, i)) { //changed (numOccur[i] + i + 1) to (numOccur[i] - 1)
                            numYellows--;
                            count++;
                        }//end if
                    }//end for
                }// end if
            }// end if
        }// end for

        if(numYellows < 0) {
            numYellows = 0;
        }
        // end (3)

        return numYellows;
    }// end GetNumExtraYellows

} // end Game class
