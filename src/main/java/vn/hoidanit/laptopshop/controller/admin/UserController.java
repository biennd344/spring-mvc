package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.UploadService;
import vn.hoidanit.laptopshop.service.UserService;

@Controller
public class UserController {
    private final UserService userService;
    private final UploadService uploadService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, UploadService uploadService,
            PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.uploadService = uploadService;
        this.passwordEncoder = passwordEncoder;

    }

    @RequestMapping(value = "/admin/user")
    public String getUserPage(Model model) {
        List<User> users = this.userService.getAllUsers();
        model.addAttribute("users", users);
        System.out.println("check" + users);

        return "admin/user/show";
    }

    @RequestMapping(value = "/admin/user/{id}")
    public String getUserDeteilPage(Model model, @PathVariable long id) {
        User user = this.userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("id", id);

        return "admin/user/detail";
    }

    @RequestMapping(value = "/admin/user/update/{id}")
    public String getUpdateUserPage(Model model, @PathVariable long id) {
        User currentUser = this.userService.getUserById(id);

        model.addAttribute("newUser", currentUser);
        // model.addAttribute("id", id);

        return "admin/user/update";
    }

    @PostMapping(value = "/admin/user/update")
    public String postUpdateUserPage(Model model, @ModelAttribute("newUser") User bin) {
        User currentUser = this.userService.getUserById(bin.getId());
        if (currentUser != null) {
            currentUser.setAddress(bin.getAddress());
            currentUser.setFullName(bin.getFullName());
            currentUser.setPhone(bin.getPhone());
            this.userService.handleSaveUser(currentUser);
        }
        return "redirect:/admin/user";
    }

    @GetMapping(value = "/admin/user/create")
    public String getCreateUserPage(Model model) {
        model.addAttribute("newUser", new User());

        return "admin/user/create";
    }

    @PostMapping(value = "/admin/user/create")
    public String createUserPage(Model model, @ModelAttribute("newUser") @Valid User bin,
            BindingResult newUserBindingResult,
            @RequestParam("binFile") MultipartFile file) {
        // validate
        // if (newUserBindingResult.hasErrors()) {
        // newUserBindingResult.getFieldErrors()
        // .forEach(error -> System.out.println(error.getField() + " - " +
        // error.getDefaultMessage()));
        // return "/admin/user/create"; // Trả về trang form với lỗi
        // }
        List<FieldError> errors = newUserBindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(error.getField() + " - " + error.getDefaultMessage());
        }
        if (newUserBindingResult.hasErrors()) {
            return "/admin/user/create";
        }

        String avatar = this.uploadService.handleSaveUploadFile(file, "avatar");
        String hashPassword = this.passwordEncoder.encode(bin.getPassword());
        bin.setAvatar(avatar);
        bin.setPassword(hashPassword);
        bin.setRole(this.userService.getRoleByName(bin.getRole().getName()));

        // save role

        this.userService.handleSaveUser(bin);

        return "redirect:/admin/user";
    }

    @GetMapping(value = "/admin/user/delete/{id}")
    public String getDeleteUserPage(Model model, @PathVariable long id) {
        User user = new User();
        user.setId(id); // gán id vào đối tượng
        model.addAttribute("newUser", user); // truyền đối tượng đã có id
        return "admin/user/delete";
    }

    @PostMapping(value = "/admin/user/delete")
    public String postDeleteUserPage(Model model, @ModelAttribute("newUser") User bin) {
        this.userService.deleteUserById(bin.getId());

        return "redirect:/admin/user";
    }

}
