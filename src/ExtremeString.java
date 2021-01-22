import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ExtremeString {
    private String string;
    private String words[];

    private int checkingPos = 0;

    private int[][] wordsPos;

    public ExtremeString(String s) {
        string = s;
        // Clean up input
        words = s
                .replaceAll("\n"," ")
                .trim()
                .replaceAll("[^a-zA-Z '-]", " ")
                .split(" ");
        wordsPos = new int[words.length][2];


        // Get positions of all words
        int currentWordPos = 0;
        for (int i = 0; i < words.length; i++) {

            String word = words[i];

            int wordPos = s.indexOf(word);

            wordsPos[i] = new int[]{currentWordPos+wordPos, currentWordPos + word.length()};

            // Cut out the word
            if (i < words.length-1) s = s.substring(word.length() + 1); else s = "";

            // Add word length + lost space
            currentWordPos += wordPos+word.length()+1;
        }
    }

    public String getNextWord() {
        String word = words[checkingPos];
        checkingPos++;
        return word;
    }

    public boolean hasNextWord() {
        return checkingPos < words.length;
    }

    @Override
    public String toString() {
        return string;
    }

    public String disEmvowelment() {
        String outString = "";

        boolean start = false;

        // Loop through words
        while (hasNextWord()) {
            if (start) outString += getBetweenWords();;
            start = true;

            Word currentWord = new Word(getNextWord());

            String outWord = "";
            for (int i = 0; i < currentWord.toString().length(); i++) {
                if (!currentWord.isCharAVowel(i)) outWord += currentWord.toString().charAt(i);
            }

            outString += outWord;
        }

        return outString;
    }

    public String toPigLatin() {
        String outString = "";

        boolean start = false;

        // Loop through words
        while (hasNextWord()) {
            if (start) outString += getBetweenWords();;
            start = true;

            Word currentWord = new Word(getNextWord());

            String newWordString = "";

            // Get position of first vowel
            int firstVowel = currentWord.indexOfFirstVowel();

            // Convert word
            if (firstVowel == 0) {
                newWordString = currentWord.toString() + "yay";
            } else {
                newWordString = currentWord.toString().substring(firstVowel) + currentWord.toString().substring(0, firstVowel) + "ay";
            }

            outString += newWordString;
        }
        return outString;
    }

    public String toLipogram(char forbiddenChar) throws IOException {
        String outString = "";
        boolean start = false;

        // Loop through words
        while (hasNextWord()) {
            if (start) outString += getBetweenWords();

            Word currentWord = new Word(getNextWord());

            String wordToOut = "";
            // Check if it has the bad char
            if (currentWord.hasCharIgnoreCase(forbiddenChar)) {
                JSONArray possibleReplacements = getRelatedWords(currentWord.toString());

                for (int i = 0; i < possibleReplacements.length() && wordToOut == ""; i++) {
                    Word testingWord = new Word(((JSONObject)possibleReplacements.get(i)).getString("word"));
                    if (!testingWord.hasCharIgnoreCase(forbiddenChar)) wordToOut = testingWord.toString();
                }
            } else {
                wordToOut = currentWord.toString();
            }

            // No first space
            if (wordToOut != "") {
                start = true;
            }
            outString += wordToOut;
        }

        return outString;
    }

    public JSONArray getRelatedWords(String word) throws IOException {
        URL url = new URL("http://api.datamuse.com/words?ml=" + word);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();

        String result = content.toString();

        return new JSONArray(result);
    }

    public String getBetweenWords() {
        if (checkingPos-1 < words.length && (words[checkingPos] != "" || words[checkingPos] != null)) {
            int startPos = wordsPos[checkingPos-1][1];
            int endPos;

            if (checkingPos-1 < words.length-1) { // If it is not at the last word
                endPos = wordsPos[checkingPos][0];
                return string.substring(startPos,endPos);
            } else {
                return "";
            }
        } else {
            return "";
        }
    }
}
