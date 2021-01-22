public class Word {
    private String word;

    final char[] VOWELS = {'a','e','i','o','u'};
    final char[] SOMETIMES_VOWELS = {'y'};

    public Word(String word) {
        this.word = word;
    }

    public int indexOfFirstVowel() {
        int i = 0;
        while (i < word.length() && !isCharAVowel(i)){
            i++;
        }
        return i;
    }

    public boolean isCharARegularVowel(int pos) {
        char character = word.toLowerCase().charAt(pos);
        for (int i = 0; i < VOWELS.length; i++) {
            if (character == VOWELS[i]) return true;
        }
        return false;
    }

    public boolean isCharAVowel(int pos) {
        char character = word.toLowerCase().charAt(pos);

        // Start by checking if it is a regular vowel
        if (isCharARegularVowel(pos)) return true;

        // Check if it has a constant acting as a vowel
        for (int i = 0; i < SOMETIMES_VOWELS.length; i++) {

            // Character sometimes is a vowel
            if (character == SOMETIMES_VOWELS[i]) {

                // If not at start check left side
                if (pos > 0 && isCharARegularVowel(pos-1)) return false;

                if (pos < word.length()-1 && isCharARegularVowel(pos+1)) return false;
                return true;
            }
        }
        return false;
    }

    public boolean hasChar(char c) {
        return (word.indexOf(c) != -1);
    }

    public boolean hasCharIgnoreCase(char c) {
        return (word.toLowerCase().indexOf(Character.toLowerCase(c)) != -1);
    }

    public String toString() {
        return word;
    }


}
