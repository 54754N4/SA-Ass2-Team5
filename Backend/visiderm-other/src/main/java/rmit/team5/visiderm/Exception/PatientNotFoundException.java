package rmit.team5.visiderm.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Patient information is existed")
public class PatientNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 2L;

    public PatientNotFoundException(String message) {
        super(message);
    }
}
