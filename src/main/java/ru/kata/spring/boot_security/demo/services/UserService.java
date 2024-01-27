package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.configs.WebSecurityConfig;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;


import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        Optional<User> userFromDb = Optional.ofNullable(userRepository.findByUsername(username));
        return userFromDb.orElse(null);
    }


    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Пользователь не найден");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getName(),
                user.getPassword(),
                user.getRoles());
    }

    @Transactional(readOnly = true)
    public User getById(Long id) {
        Optional<User> userFromDb = userRepository.findById(id);
        return userFromDb.orElse(new User());
    }

    @Transactional(readOnly = true)
    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public User update(User user) {
        User userFromDb = userRepository.findByUsername(user.getUsername());
        if (userFromDb == null) {
            System.out.println("Пользователь не найден");
            return null;
        }
        userFromDb.setName(user.getName());
        userFromDb.setLastname(user.getLastname());
        userFromDb.setAge(user.getAge());
        userFromDb.setEmail(user.getEmail());
        return userRepository.save(userFromDb);
    }

    public void save(User user) {
        User userFromDb = userRepository.findByUsername(user.getUsername());
        if (userFromDb != null) {
            System.out.println("Такой пользователь уже существует!");
        }
        user.setPassword(WebSecurityConfig.passwordEncoder().encode(user.getPassword()));
        userRepository.save(user);
    }

    public void delete(User user) {
        User userFromDb = userRepository.findByUsername(user.getUsername());
        if (userFromDb == null) {
            System.out.println("Пользователь не найден");
        }
        userRepository.delete(userFromDb);
    }

}
