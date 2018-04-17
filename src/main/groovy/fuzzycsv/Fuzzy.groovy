package fuzzycsv

import groovy.transform.CompileStatic
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@CompileStatic
class Fuzzy {
    private static Logger log = LoggerFactory.getLogger(Fuzzy)

    static int findBestPosition(def phrases, String header, float minScore) {
        phrases = phrases as List
        def csvColIdx = findPosition(phrases, header)
        csvColIdx
    }

    static int findPosition(def phrases, String name) {
        phrases.findIndexOf { value -> value.toString().toLowerCase().trim().equalsIgnoreCase(name.trim().toLowerCase()) }
    }
}
