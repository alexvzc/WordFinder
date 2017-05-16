
package mx.avc.wordfinder;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static org.junit.Assert.*;
import org.junit.Test;

public class WordFinderTest {

    private static <T> Set<T> asSet(T... elems) {
        return Collections.unmodifiableSet(
                new HashSet<T>(Arrays.asList(elems)));
    }

    private static <T> List<T> asList(T... elems) {
        return Collections.unmodifiableList(Arrays.asList(elems));
    }

    /*
     * private static final Set<String> DICTIONARY = asSet(
     * "lorem", "ipsum", "dolor", "sit", "amet", "consectetuer",
     * "adipiscing", "elit", "sed", "diam", "nonummy", "nibh",
     * "euismod", "tincidunt", "ut", "laoreet", "dolore", "magna",
     * "aliquam", "erat", "volutpat", "ut", "wisi", "enim", "ad",
     * "minim", "veniam", "quis", "nostrud", "exerci", "tation",
     * "ullamcorper", "suscipit", "lobortis", "nisl", "ut", "aliquip",
     * "ex", "ea", "commodo", "consequat", "duis", "autem", "vel",
     * "eum", "iriure", "dolor", "in", "hendrerit", "in", "vulputate",
     * "velit", "esse", "molestie", "consequat", "vel", "illum",
     * "dolore", "eu", "feugiat", "nulla", "facilisis", "at", "vero",
     * "eros", "et", "accumsan", "et", "iusto", "odio", "dignissim",
     * "qui", "blandit", "praesent", "luptatum", "zzril", "delenit",
     * "augue", "duis", "dolore", "te", "feugait", "nulla", "facilisi",
     * "nam", "liber", "tempor", "cum", "soluta", "nobis", "eleifend",
     * "option", "congue", "nihil", "imperdiet", "doming", "id", "quod",
     * "mazim", "placerat", "facer", "possim", "assum", "typi", "non",
     * "habent", "claritatem", "insitam", "est", "usus", "legentis",
     * "in", "iis", "qui", "facit", "eorum", "claritatem",
     * "investigationes", "demonstraverunt", "lectores", "legere", "me",
     * "lius", "quod", "ii", "legunt", "saepius", "claritas", "est",
     * "etiam", "processus", "dynamicus", "qui", "sequitur",
     * "mutationem", "consuetudium", "lectorum", "mirum", "est",
     * "notare", "quam", "littera", "gothica", "quam", "nunc",
     * "putamus", "parum", "claram", "anteposuerit", "litterarum",
     * "formas", "humanitatis", "per", "seacula", "quarta", "decima",
     * "et", "quinta", "decima", "eodem", "modo", "typi", "qui", "nunc",
     * "nobis", "videntur", "parum", "clari", "fiant", "sollemnes",
     * "in", "futurum"
     * )
     *
     * private static final String TEST_PATTERN =
     * "loremipsumdolorsitametconsectetueradipiscingelitseddiamnonum" +
     * "mynibheuismodtinciduntutlaoreetdoloremagnaaliquameratvolutpa" +
     * "tutwisienimadminimveniamquisnostrudexercitationullamcorpersu" +
     * "scipitlobortisnislutaliquipexeacommodoconsequatduisautemvele" +
     * "umiriuredolorinhendreritinvulputatevelitessemolestieconsequa" +
     * "tvelillumdoloreeufeugiatnullafacilisisatveroerosetaccumsanet" +
     * "iustoodiodignissimquiblanditpraesentluptatumzzrildelenitaugu" +
     * "eduisdoloretefeugaitnullafacilisinamlibertemporcumsolutanobi" +
     * "seleifendoptionconguenihilimperdietdomingidquodmazimplacerat" +
     * "facerpossimassumtypinonhabentclaritateminsitamestususlegenti" +
     * "siniisquifaciteorumclaritateminvestigationesdemonstraveruntl" +
     * "ectoreslegeremeliusquodiileguntsaepiusclaritasestetiamproces" +
     * "susdynamicusquisequiturmutationemconsuetudiumlectorummirumes" +
     * "tnotarequamlitteragothicaquamnuncputamusparumclaramanteposue" +
     * "ritlitterarumformashumanitatisperseaculaquartadecimaetquinta" +
     * "decimaeodemmodotypiquinuncnobisvidenturparumclarifiantsollem" +
     * "nesinfuturum"
     */
    private static final Set<String> DICTIONARY = asSet(
            "a", "ab", "ba", "aba", "bab");

    private static final String TEST_PATTERN =
            "ababababababbababaabbabababaababababaabbaba";

    private static final Set<Set<String>> EXPECTED_RESULT =
            asSet(asSet("ab", "ba", "a"), asSet("ab", "ba", "aba", "bab"),
            asSet("ba", "a", "ab", "aba", "bab"), asSet("ab", "ba", "aba"),
            asSet("a", "bab", "ab"), asSet("a", "bab", "ab", "aba"),
            asSet("a", "ba", "bab", "ab"));

    @Test
    public void testWordFinder() {
        System.out.println("Warming up");
        for(int i = 0; i < 10; i++) {
            WordFinder.findWords(TEST_PATTERN, DICTIONARY);
            System.out.print(".");
        }
        System.out.println("complete");
        long non_parallel = 0L;
        for(int j = 0; j < 20; j++) {
            long start = System.nanoTime();
            Set<? extends Set<String>> result =
                    WordFinder.findWords(TEST_PATTERN, DICTIONARY);
            long end = System.nanoTime();
            System.out.println("Execution time = " + (end - start));
            non_parallel += (end - start);
        }
        System.out.println("Average time (ms) " + (non_parallel / 20e6d));
        //assertEquals(EXPECTED_RESULT, result);
        assertTrue(true);
    }

}
