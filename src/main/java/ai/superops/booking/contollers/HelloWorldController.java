package ai.superops.booking.contollers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @GetMapping("hello")
    public String helloWorld(@RequestParam(value = "name", defaultValue = "World") String name) {
        SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return "Hello " + name + "!!";
    }
}
