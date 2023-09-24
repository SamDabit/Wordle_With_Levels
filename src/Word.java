import java.util.*;
import java.io.*;

/**
 *
 * @author Samir Dabit
 *
 */
public class Word {

    //String ArrayLists
    private ArrayList<ArrayList<String>> listOfLists = new ArrayList<ArrayList<String>>();
    private ArrayList<String> list3 =  new ArrayList<String>();
    private ArrayList<String> list4 =  new ArrayList<String>();
    private ArrayList<String> list5 =  new ArrayList<String>();
    private ArrayList<String> list6 =  new ArrayList<String>();
    private ArrayList<String> list7 =  new ArrayList<String>();
    private ArrayList<String> list8 =  new ArrayList<String>();
    private ArrayList<String> list9 =  new ArrayList<String>();
    private ArrayList<String> list10 = new ArrayList<String>();
    private ArrayList<String> list11 = new ArrayList<String>();
    private ArrayList<String> list12 = new ArrayList<String>();
    private ArrayList<String> list13 = new ArrayList<String>();
    private ArrayList<String> list14 = new ArrayList<String>();
    private ArrayList<String> list15 = new ArrayList<String>();

    private ArrayList<File> listOfFiles = new ArrayList<File>();
    private File file3  = new File("3LetterWords.txt");
    private File file4  = new File("4LetterWords.txt");
    private File file5  = new File("5LetterWords.txt");
    private File file6  = new File("6LetterWords.txt");
    private File file7  = new File("7LetterWords.txt");
    private File file8  = new File("8LetterWords.txt");
    private File file9  = new File("9LetterWords.txt");
    private File file10 = new File("10LetterWords.txt");
    private File file11 = new File("11LetterWords.txt");
    private File file12 = new File("12LetterWords.txt");
    private File file13 = new File("13LetterWords.txt");
    private File file14 = new File("14LetterWords.txt");
    private File file15 = new File("15LetterWords.txt");


    //File temp = new File("3LetterWords");

    public Word() {
        //Sets up each ArrayList, and list of lists
        Setup();
    }

    /**
     * Returns a random word from the list corresponding to the wordLength
     * @param wordLength
     * @return
     */
    public String GetRandomWord(int wordLength) {
        String randWord = "";

        switch (wordLength) {
            case 3:
                randWord = list3.get(GetRandomNumber(list3.size() - 1));
                break;
            case 4:
                randWord = list4.get(GetRandomNumber(list4.size() - 1));
                break;
            case 5:
                randWord = list5.get(GetRandomNumber(list5.size() - 1));
                break;
            case 6:
                randWord = list6.get(GetRandomNumber(list6.size() - 1));
                break;
            case 7:
                randWord = list7.get(GetRandomNumber(list7.size() - 1));
                break;
            case 8:
                randWord = list8.get(GetRandomNumber(list8.size() - 1));
                break;
            case 9:
                randWord = list9.get(GetRandomNumber(list9.size() - 1));
                break;
            case 10:
                randWord = list10.get(GetRandomNumber(list10.size() - 1));
                break;
            case 11:
                randWord = list11.get(GetRandomNumber(list11.size() - 1));
                break;
            case 12:
                randWord = list12.get(GetRandomNumber(list12.size() - 1));
                break;
            case 13:
                randWord = list13.get(GetRandomNumber(list13.size() - 1));
                break;
            case 14:
                randWord = list14.get(GetRandomNumber(list14.size() - 1));
                break;
            case 15:
                randWord = list15.get(GetRandomNumber(list15.size() - 1));
                break;
        }

        return randWord;

    } // end GetRandomWord

    /**
     * Returns a random number between 0 and max
     * @param max
     * @return
     */
    public static int GetRandomNumber(int max) {
        return (int)(Math.random() * (max + 1)); // min = 0

    } // end GetRandomNumber

    /**
     * Returns ArrayList with values from a certain text file
     * @param file
     */
    public ArrayList<String> ReadFile(File file) {
        ArrayList<String> myList = new ArrayList<String>();

        try {
            Scanner myReader = new Scanner(file);

            // Reads the entire
            while(myReader.hasNextLine()) {
                myList.add(myReader.nextLine());
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occured.");
            e.printStackTrace();
        }
        return myList;

    }

    /**
     *
     * @param listNum
     * @return
     */
    public ArrayList<String> GetWordList(int listNum){
        return listOfLists.get(listNum - 3);
    }

    /**
     *
     * @param fileNum
     * @return
     */
    public File GetFile(int fileNum) {
        return listOfFiles.get(fileNum - 3);
    }

    /**
     *
     */
    public void Setup() {
        list3  = ReadFile(this.file3);
        list4  = ReadFile(this.file4);
        list5  = ReadFile(this.file5);
        list6  = ReadFile(this.file6);
        list7  = ReadFile(this.file7);
        list8  = ReadFile(this.file8);
        list9  = ReadFile(this.file9);
        list10 = ReadFile(this.file10);
        list11 = ReadFile(this.file11);
        list12 = ReadFile(this.file12);
        list13 = ReadFile(this.file13);
        list14 = ReadFile(this.file14);
        list15 = ReadFile(this.file15);

        listOfLists.add(list3);
        listOfLists.add(list4);
        listOfLists.add(list5);
        listOfLists.add(list6);
        listOfLists.add(list7);
        listOfLists.add(list8);
        listOfLists.add(list9);
        listOfLists.add(list10);
        listOfLists.add(list11);
        listOfLists.add(list12);
        listOfLists.add(list13);
        listOfLists.add(list14);
        listOfLists.add(list15);

        listOfFiles.add(file3);
        listOfFiles.add(file4);
        listOfFiles.add(file5);
        listOfFiles.add(file6);
        listOfFiles.add(file7);
        listOfFiles.add(file8);
        listOfFiles.add(file9);
        listOfFiles.add(file10);
        listOfFiles.add(file11);
        listOfFiles.add(file12);
        listOfFiles.add(file13);
        listOfFiles.add(file14);
        listOfFiles.add(file15);

    }// end function

}
