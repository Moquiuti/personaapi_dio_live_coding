package one.digitalinnovation.personalapi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/people")
public class PersonalapiApplication {

    @GetMapping
    public String getBook() {
        return "API Rest";
    }

}
