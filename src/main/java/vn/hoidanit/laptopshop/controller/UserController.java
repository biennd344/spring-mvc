package vn.hoidanit.laptopshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.UserRepository;
import vn.hoidanit.laptopshop.service.UserService;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;

    }

    @RequestMapping("/")
    public String getHomePage(Model model) {
        String test = this.userService.hendleHello();
        model.addAttribute("eric", test);
        model.addAttribute("bin", "from controller with model");
        return "hello";
    }

    @RequestMapping("/admin/user")
    public String getUserPage(Model model) {
        String test = this.userService.hendleHello();
        model.addAttribute("newUser", new User());
        model.addAttribute("bin", "from controller with model");
        return "admin/user/create";
    }

    @RequestMapping(value = "/admin/user/create", method = RequestMethod.POST)
    public String createUserPage(Model model, @ModelAttribute("newUser") User bin) {
        System.out.println("run here " + bin);
        this.userService.handleSaveUser(bin);

        return "hello";
    }

}

// @RestController
// public class UserController {
// private UserService userService;

// public UserController(UserService userService) {
// this.userService = userService;
// }

// @GetMapping("/")
// public String getHomepage() {
// return this.userService.hendleHello();
// }

// }
