package senai.com.backend_atividades.exception;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Validation extends RuntimeException {

    public Map<String, String> validations = new HashMap<>();

    public Validation() {
        super("Validations Ocurred");
    }

    public void add(String field, String message) {
        validations.put(field, message);
    }

}

