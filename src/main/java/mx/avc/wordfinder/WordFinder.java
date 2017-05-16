
package mx.avc.wordfinder;

import java.util.BitSet;
import static java.util.Collections.EMPTY_SET;
import static java.util.Collections.unmodifiableSet;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public class WordFinder {

    private static final Map<Set<String>, BitSet> WORDLENGTH_CACHE =
            new WeakHashMap<Set<String>, BitSet>();

    private static <T> Set<T> newSet(Set<T> set, T elem) {
        if(set.contains(elem)) {
            return set;
        }

        Set<T> result = new HashSet<T>(set);
        result.add(elem);
        return unmodifiableSet(result);
    }

    private static Set<Set<String>> searchWords(String subc, Set<String> path,
            Set<String> dictionary) {
        BitSet validWordLengths = dictionaryWordLengths(dictionary);

        Set<String> wordsFound = new HashSet<String>();
        for(int i = validWordLengths.nextSetBit(0); i >= 0;
                i = validWordLengths.nextSetBit(i + 1)) {
            if(i <= subc.length()) {
                String sstr = subc.substring(0, i);
                if(dictionary.contains(sstr)) {
                    wordsFound.add(sstr);
                }
            }
        }
        
        Set<Set<String>> results = new HashSet<Set<String>>();

        for(String word : wordsFound) {
            Set<String> new_path = newSet(path, word);
            int wordLength = word.length();
            if(wordLength == subc.length()) {
                results.add(new_path);
            } else {
                results.addAll(searchWords(
                        subc.substring(wordLength), new_path, dictionary));
            }
        }
        
        return unmodifiableSet(results);
    }

    private static BitSet dictionaryWordLengths(Set<String> dictionary) {
        synchronized(WORDLENGTH_CACHE) {
            if(WORDLENGTH_CACHE.containsKey(dictionary)) {
                return WORDLENGTH_CACHE.get(dictionary);
            }

            BitSet word_lengths = new BitSet();

            for(String word : dictionary) {
                word_lengths.set(word.length());
            }

            WORDLENGTH_CACHE.put(dictionary, word_lengths);
            return word_lengths;
        }
    }

    public static Set<? extends Set<String>> findWords(String cs,
            Set<String> dictionary) {
        Set<Set<String>> results = searchWords(cs, EMPTY_SET, dictionary);
        return unmodifiableSet(results);
    }

}
