package fr.weytensjohann.springcefim;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hello")

public class HelloController {

    private final Logger logger = LoggerFactory.getLogger(HelloController.class);
    @RequestMapping("/world")
    public String helloWorld() {
        logger.info("Hello World");
        return "Hello World";

    }
}
