package com.example.users;

import com.example.users.model.User;
import com.example.users.rest.UserController;
import org.junit.jupiter.api.Test;

public class UsersApplicationTests  {

    @Test
    public void testAddUser() {
	UserController userController = new UserController();
	User user = new User();
	user.setAge(2);
	user.setCountry("England");
	user.setEmail("lilayefsah@test.com");
	user.setPhone("02665588977");
	userController.registerUser(user, "user");
    }

}
