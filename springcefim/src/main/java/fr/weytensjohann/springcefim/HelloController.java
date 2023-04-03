package fr.weytensjohann.springcefim;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hello")

public class HelloController {
    @RequestMapping("/world")
    public String helloWorld() {
        return "Hello World";
    }
}
