package at.spengergasse.foundation;

import at.spengergasse.ApplicationException;

import java.util.function.Supplier;

public abstract class Assert {


    // Null Assertions -------------------------------------------------------------

    // public static Object isNotNull(Object value, String paramName) {
    public static <T> T isNotNull(T value, String paramName) {
        if (value == null)
            throw new ApplicationException(STR."\{paramName} is null");

        return value;
    }


    // String Assertions -----------------------------------------------------------

    public static String isNotBlank(String value, String paramName) {
        isNotNull(value, paramName);

        if (value.isBlank())
            throw new ApplicationException(STR."\{paramName} is blank");

        return value;
    }

    public static String hasMaxLength(String value, int maxLength, String paramName) {

        isNotBlank(value, paramName);

        if (value.length() > maxLength)
            throw new ApplicationException(STR."\{paramName} is greater than \{maxLength}");

        return value;
    }



    // Expression Assertions -------------------------------------------------------

    // EAGER message evaluation:
    // Eager means that the error message is evaluated even if the expression is true
    // public static void isTrue(boolean expression, String errorMsg) {
    //     if (!expression)
    //         throw new ApplicationException(errorMsg);
    // }

    // LAZY message evaluation:
    // Lazy means that the error message is only evaluated if the expression is false
    public static void isTrue(boolean expression, Supplier<String> errorMsg) {
        if (!expression)
            throw new ApplicationException(errorMsg.get());
    }
}
