package one.digitalinnovation.personalapi.exception;

import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PersonNotFoundException extends Exception {
    public PersonNotFoundException(@NonNull Long id) {
        super("Person not found with ID: " + id);
    }
}
