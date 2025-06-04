package vn.hoidanit.laptopshop;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/")
    public String index() {
        return "Hello Worldsssssss";
    }

    @GetMapping("/user")
    public String userpage() {
        return "Hello Worldsssssss of user";
    }

    @GetMapping("/admin")
    public String adminpage() {
        return "Hello Worldsssssss of admin";
    }

}
