package fuzzycsv

/**
 * Created with IntelliJ IDEA.
 * User: kay
 * Date: 10/20/13
 * Time: 6:21 PM
 * To change this template use File | Settings | File Templates.
 */
class FuzzyCSVUtils {

    static List<Number> toNumbers(List list) {
        def rt = list.collect { it ->
            return coerceToNumber(it)
        }
        return rt
    }

     static Number coerceToNumber(obj) {
        if (obj == null)
            return 0

        if (obj instanceof Number)
            return obj

        def strValue = obj.toString()

        try {
            return Integer.parseInt(strValue)
        } catch (Exception x) {
        }

        try {
            return Double.parseDouble(strValue)
        } catch (Exception x) {
        }
        System.err.println("FuzzyCSVUtils:toNumbers() Could not convert [$strValue] to a Number")
        return 0
    }

}