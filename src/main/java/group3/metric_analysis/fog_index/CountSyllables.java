package group3.metric_analysis.fog_index;

import java.util.ArrayList;

/**
 * Estimate the number of syllables in a word.
 *
 * Based on
 * http://search.cpan.org/src/GREGFAST/Lingua-EN-Syllable-0.251/Syllable.pm
 * Taken from https://gist.github.com/jbertouch/5f19ed775b5064b7a197cb2c9017ce52 //TODO: Reference properly
 */
public class CountSyllables {

    static String[] addSyllableArray = { "ia", "riet", "dien", "iu", "io", "ii", "[aeiouym]bl$", "[aeiou]{3}", "^mc", "ism$",
            "[^aeiouy][^aeiouy]l$", "[^l]lien", "^coa[dglx].", "[^gq]ua[^auieo]", "dnt$" };
    static String[] subtractSyllableArray = { "cial", "tia", "cius", "cious", "giu", "ion", "iou", "sia$", ".ely$" };

    public static int countComplexWords(String sentence) {
        ArrayList<String> wordsInSentence = new ArrayList<String>();
        String currentWord = "";
        for(String word: sentence.split(" ")){
            wordsInSentence.add(word);
        }
        int complexWordCount = 0;
        for (String string : wordsInSentence) {
            string = string.toLowerCase();
            string = string.replaceAll("'", "");

            if (string.equals("i"))
                return 0;
            if (string.equals("a"))
                return 0;

            if (string.endsWith("e")) {
                string = string.substring(0, string.length() - 1);
            }

            String[] phonemes = string.split("[^aeiouy]+");

            int syllableCount = 0;
            for (int i = 0; i < subtractSyllableArray.length; i++) {
                String syllable = subtractSyllableArray[i];
                if (string.matches(syllable)) {
                    syllableCount--;
                }
            }
            for (int i = 0; i < addSyllableArray.length; i++) {
                String syllable = addSyllableArray[i];
                if (string.matches(syllable)) {
                    syllableCount++;
                }
            }
            if (string.length() == 1) {
                syllableCount++;
            }

            for (int i = 0; i < phonemes.length; i++) {
                if (phonemes[i].length() > 0)
                    syllableCount++;
            }

            if (syllableCount == 0) {
                syllableCount = 1;
            }

            if(syllableCount >= 3){
                complexWordCount++;
            }


        }

        return complexWordCount;
    }

}
