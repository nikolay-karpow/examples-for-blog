package hello;

import hello.dto.Greeting;
import hello.dto.Person;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class GreetingsController {
    
    @RequestMapping("/greeting")
    public ResponseEntity greeting() {
        Greeting greeting = new Greeting(new Person("Heisenberg"), "Guten Tag");
        return ResponseEntity.ok(greeting);
    }
    
}
