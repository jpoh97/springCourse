package guru.springframework.controllers;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestControler {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public hello.Greetings greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new hello.Greetings(counter.incrementAndGet(),
                String.format(template, name));
    }
}