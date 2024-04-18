package at.spengergasse;

// 1) Checked Exceptions
// - extends Exceptions
// - have to delcare them

// For things which are unknown in advance but may happen

// 2) Unchecked Exceptions
// - extends RuntimeException
// - do not need declaration

// - For things which are foreseeable
//  e.g. parameter validation

//public class ApplicationException extends Exception {
public class ApplicationException extends RuntimeException {

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
