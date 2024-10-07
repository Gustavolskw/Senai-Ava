package senai.com.backend_atividades.exception;

public class UsernameNotFaundException extends RuntimeException {
    public UsernameNotFaundException(String message) {
        super(message);
    }
}
