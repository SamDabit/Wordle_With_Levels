import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

//TODO fix points function

/**
 * Contains the roundPoints, totalPoints, and high-score(TBD).
 * @author Samir Dabit
 *
 */
public class Points {
    private double[] roundPoints;
    private double totalPoints;
    private double highScore;
    private File pointsMemoryFile = new File("Wordle_Points_Memory.txt");

    public Points() {
        this.totalPoints = 0;
        this.roundPoints = new double[Main.GetMAXROUNDS()]; //sets it to have a length of the max rounds
        ReadMemory();
    }

    /*======================= GETTERS & SETTERS ==========================*/

    /**
     * Gets the double[] array of round points.
     * @return Returns the double[] array containing each round's points.
     */
    public double[] GetRoundPoints() {
        return this.roundPoints;
    }

    /**
     * Gets the points of a specific round.
     * @param
     * @return Returns the element at 'index'.
     */
    public double GetRoundPoints(int index) {
        return this.roundPoints[index];
    }

    /**
     * Sets the round points of a certain round (index) equal to 'points'.
     * @param points
     * @param index
     */
    public void SetRoundPoints(double points, int index) {
        this.roundPoints[index] = points;
    }

    public void ClearRoundPoints() {
        for(int i = 0; i < Main.GetMAXROUNDS(); i++) {
            this.roundPoints[i] = 0;
        }
    }

    /**
     * Returns the total points.
     * @return totalPoints
     */
    public double GetTotalPoints() {
        return this.totalPoints;
    }

    /**
     * Sets total points equal to 'newPoints'.
     * @param newPoints
     */
    public void SetTotalPoints(double newPoints) {
        this.totalPoints = newPoints;
    }

    /**
     * Returns highScore.
     * @return highScore
     */
    public double GetHighScore() {
        return this.highScore;
    }

    /**
     * Sets highScore equal to 'newHighScore'.
     * @param newHighScore
     */
    public void SetHighScore(double newHighScore) {
        this.highScore = newHighScore;
    }

    /**
     * Returns the memory file for this class.
     * @return memoryFile
     * NO SET FUNCTION: Don't want anyone to change name of file rn.
     */
    public File GetMemoryFile() {
        return this.pointsMemoryFile;
    }

    /*================================= METHODS ========================================*/

    /**
     * Reads memory file if it exists and saves highScore.
     */
    public void ReadMemory() {
        try {
            Scanner myReader = new Scanner(pointsMemoryFile);

            //Check if file already exists
            if(pointsMemoryFile.exists()) {
                //File already exists -> Read

                //Loops until end of file -> FUTUREPROOFING
                while(myReader.hasNextLine()) {
                    String data = myReader.nextLine();

                    //Checks if line contains highScore data
                    if(data.startsWith("highscore=")) {
                        this.highScore=Double.parseDouble(data.substring(11)); // sets highScore equal to numbers after '=' in line
                        myReader.close();
                        return;
                    }//end if

                }//end while
                this.highScore = 0; //highScore = 0 if file doesn't exist
            }//end if
            myReader.close();

        } catch(IOException e){
            System.out.println("An error occurred in ReadMemory().");
            //e.printStackTrace();
        }
    } //end ReadMemory


    /**
     * Saves memory of the points file (high-score) to the memory file.
     */
    public void SaveMemory() {
        //System.out.println("In SaveMemory() function."); //TEST
        try {
            //System.out.println("In try{} section."); //TEST

            //FileWriter myWriter = new FileWriter(pointsMemoryFile);
            //Scanner myReader = new Scanner(pointsMemoryFile);

            //If file doesn't exists: create and format it.
            //Else: change value of highScore in file memory.
            if(!pointsMemoryFile.exists()) {

                FileWriter myWriter = new FileWriter(pointsMemoryFile); //OVERWRITES EXISTING FILE BC NOT TOLD TO APPEND (2nd param)
                Scanner myReader = new Scanner(pointsMemoryFile);

                pointsMemoryFile.createNewFile();
                //File just created; FORMAT MEMORY
                //System.out.println("Formatting saveFile."); //TEST
                myWriter.write("highscore=" + this.highScore);

                myWriter.close();
                myReader.close();

            } else {
                //System.out.println("File length at start of else{}: " + pointsMemoryFile.length()); //TEST

                FileWriter myWriter = new FileWriter(pointsMemoryFile); //OVERWRITES EXISTING FILE BC NOT TOLD TO APPEND (2nd param)
                Scanner myReader = new Scanner(pointsMemoryFile);
                //System.out.println("File length after instantiation of reader/writer: " + pointsMemoryFile.length()); //TEST

                //File already exists
                //System.out.println("In else{}."); //TEST
                //myWriter.write("test"); //TEST

                do {
                    //System.out.println("In do-while().");//TEST
                    String data = "";
                    //Check if file is even
                    //System.out.println("File length: " + pointsMemoryFile.length()); //TEST

                    //if(pointsMemoryFile.length() != 0) {
                    //	System.out.println("In if( .length() )."); //TEST
                    //	data = myReader.nextLine();
                    //}

                    //System.out.println("data: " + data); //TEST

                    //System.out.println("In if(data.startsWith())."); //TEST
                    String replaceLine = "highscore="  + this.highScore + "\n";
                    myWriter.append(replaceLine); // sets highScore equal to numbers after '=' in line
                    myReader.close();
                    myWriter.close();
                    return;

                    //Checks if line contains highScore data
                    //if(data.startsWith("highscore=")) {

                    //}//end if

                } while(myReader.hasNextLine());//end while
                //myWriter.close();
                //myReader.close();
            }
            //myWriter.close();
            //myReader.close();
        } catch(IOException e) {
            System.out.println("An error occurred in SaveMemory().");
            e.printStackTrace();
        }
    }

    /**
     *
     * @param roundNum
     * @param tryNum
     * @param gameObj
     * @param userWord
     * @param numGreens
     * @param numYellows
     */
    public void UpdateRoundPoints(int roundNum, int tryNum, Game gameObj, String userWord, int numGreens, int numExtraYellows, boolean isEasy) {

        // if easyMode is on, apply multiplier
        if(isEasy) {
            this.roundPoints[roundNum - 3] += ( CalcWordPoints(roundNum, tryNum,  gameObj,  userWord,  numGreens,  numExtraYellows) * Main.GetEASYMULT());
            return;
        } else {
            this.roundPoints[roundNum - 3] += CalcWordPoints(roundNum, tryNum,  gameObj,  userWord,  numGreens,  numExtraYellows);
        }
    }

    public void UpdateRoundPoints(int roundNum, double points, boolean isEasy) {

        // if easyMode is on, apply multiplier
        if(isEasy) {
            this.roundPoints[roundNum - 3] += ( points);
            return;
        } else {
            this.roundPoints[roundNum - 3] += points;
        }
    }

    /**
     *
     * @param roundNum
     * @param tryNum
     * @param gameObj
     * @param userWord
     * @param numGreens
     * @param numYellows
     * @return
     */
    public static double CalcWordPoints(int roundNum, int tryNum, Game gameObj, String userWord, int numGreens, int numExtraYellows) {

        // Not necessary
        if(gameObj.IsWordCorrect(userWord)) {
            return userWord.length() * 2;
        }

        double points = 0;
        roundNum -= 2; //Round will always start at 1
        tryNum++;
        //int wordLength = userWord.length();
        //int[] occurList = new int[wordLength];

		/*----------- (1) WORKING POINTS ----------
		 * 		Problem: Doesn't check to see if the letter has already been found.
		if(numExtraYellows != 0) {
			points += (1 * numExtraYellows);
		}
		if(numGreens != 0) {
			points += (2 * numGreens);
		}
		* -----------------------------------
		*/

        //return points * ((double)roundNum / (double)tryNum);


        /* -------------------------
         * (2) Check if the letter has already been found
         * 		if found: skip
         * 		if not found: add points accordingly
         *
         * Should only flag a position as 'found' if the player found the correct position.
         * Yellow letters should always give points.
         */
        // HandleGreens() -> Adds 2 points for each correct letter that has not been found before.

        for(int i = 0; i < userWord.length(); i++) {
            //System.out.println("In for loop in points thing"); // TEST
            if(gameObj.IsLetterCorrect(userWord, i) && !gameObj.GetFoundLetters(i) ) { //if (letter is correct &&  it hasn't been found this round)
                points += 2;
                //System.out.println("points in loop " + i + ": " + points);
                gameObj.SetFoundLetters(true, i);
                //System.out.println("Set foundLetters[" + i + "] to true. It's now found.");
            }//end if
        }//end for

        // HandleYellows() -> Adds 1 point for each extra yellow
        ///System.out.println("numExtraYellows: " + numExtraYellows);
        points += numExtraYellows;

        //System.out.println("Returning points: " + ( points * ((double)roundNum / (double)tryNum)) );
        //return points - ((double)roundNum / (double)tryNum) + Game.GetMAXTRIES(); //linear
        //return points * ((double)roundNum / (double)tryNum); //exponential
        return points * ( ((double)roundNum * tryNum) / Game.GetMAXTRIES());

        /* -------------------
         * Theory: 	(1) Points should be multiplied by ratio of roundNum to tryNum (roundNum / tryNum)
         * 			(2) Points should be fair based on difficulty. Maybe include something to do with uncommon letters.
         * 			(3) Points per letter should be multiplied by # of occurrences in answer to occurrences in input (occInp / occAns)
         * 			(4) Points per word = sum of points for each letter
         * 			(5) Points per certain letter multiplied by 2 if has not been found before
         * 				-> How to find if the letter has been found?
         * 					> Maybe make an array to store the indexes of the found letters in the round
         *
         * 			Equation: points = (roundNum / tryNum) * (occInp / occnAns)
         * -------------------
         */

        //(1)
		/*
		// loops through word and skips letters that have already been found
		for(int i = 0; i < gameObj.GetWordLength();i++) {
			// if not found before, add points of this specific letter

			if(gameObj.GetFoundLetters(i) == false) {
				//occurrence of letter
				int occInp = gameObj.FindNumOccurrences(userWord, userWord.charAt(i));
				int occAns = gameObj.FindNumOccurrences(gameObj.GetWordAnswer(), userWord.charAt(i));




				//points += equation for points
				points += (roundNum / tryNum);

//				(1) For now, yellow = 1, green = 3. Multiply by (roundNum / tryNum)
				//Checks if green, yellow, gray,
				if(Game.IsLetterCorrect(userWord, i)) {
					//multiply points by 3 (green multiplier)
					points += 2;
				} else if (Game.IsLetterContained(userWord, i)){
					points +=1;
				}
			}

		}
		*/



    }
    //testing, not necessary

    /**
     * Print occurrences of every letter in an input word in the answer
     * @param roundNum
     * @param tryNum
     * @param gameObj
     * @param userWord
     * @param numGreens
     * @param numYellows
     */
    public static void PrintOccurrences(int roundNum, int tryNum, Game gameObj, String userWord, int numGreens, int numYellows) {
        //double pointsNum = 0;
        roundNum -= 2; //Round will always start at 1
        int wordLength = gameObj.GetWordLength();
        int[] occurList = new int[wordLength];
        //int[] foundLetters = new int[wordLength];
        for(int i = 0; i < wordLength; i++) {
            occurList[i] = gameObj.FindNumOccurrences(gameObj.GetWordAnswer(), userWord.charAt(i));
            System.out.println("Occurrence of " + userWord.charAt(i) + " in answer: " + occurList[i]);
        }
        //System.out.println("numYellows: " + numYellows);
        //System.out.println("numGreens: " + numGreens);
    }


    /**
     * Print occurrences of every letter in an input word in the answer.
     * @Override doesn't need numGreens or numYellows so that one can use it as a static in main
     * @param roundNum
     * @param tryNum
     * @param gameObj
     * @param userWord
     */
    public static void PrintOccurrences(int roundNum, int tryNum, Game gameObj, String userWord) {
        //double pointsNum = 0;
        roundNum -= 2; //Round will always start at 1
        int wordLength = gameObj.GetWordLength();
        int[] occurList = new int[wordLength];
        //int[] foundLetters = new int[wordLength];
        for(int i = 0; i < wordLength; i++) {
            occurList[i] = gameObj.FindNumOccurrences(gameObj.GetWordAnswer(), userWord.charAt(i));
            System.out.println("Occurrence of " + userWord.charAt(i) + " in answer: " + occurList[i]);
        }
        //System.out.println("numYellows: " + numYellows);
        //System.out.println("numGreens: " + numGreens);

    }
}
