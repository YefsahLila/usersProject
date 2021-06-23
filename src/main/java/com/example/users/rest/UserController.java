package com.example.users.rest;

import com.example.users.model.User;
import com.example.users.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class UserController {

    /**
     * This class is the rest controller
     */
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserRepository userRepository;

    /**
     * This ressource will return all the users registred in the database
     */
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * This ressource will return the details of a given
     * user according to his id
     */

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") String id)
            throws ResourceNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id: " + id));

        logger.info("find user by Id : {}", id);
        return ResponseEntity.ok().body(user);
    }

    /**
     * This ressource will allow to add (register) new user into the database
     * only if it respects contraints
     * User age must be bigger than 18
     * User must live in France to be able to register
     * <p>
     * Default parameter is defined by default for the lastName in case it's not defined
     * if the constraints are not respected 403 forbidden status will be returned
     * else created successfuly
     */
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user, @RequestParam(value = "lastName", defaultValue = "user") String lastName)
            throws ResourceNotFoundException {

        if (user.getAge() > 18 && user.getCountry().equals("France")) {
            User newUser = new User();
            newUser = userRepository.save(user);
            logger.info("create user with Id : {}", user.getId());
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } else {
            logger.info("User must respect contraints ! ");
            return new ResponseEntity<>(user, HttpStatus.FORBIDDEN);
        }
    }
}
