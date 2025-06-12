package vn.hoidanit.laptopshop.service;

import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String hendleHello() {
        return "hi";
    }

    public User handleSaveUser(User user) {
        User bin = this.userRepository.save(user);
        System.out.println(bin);
        return bin;
    }
}
