package com.example.restapi;

import com.example.restapi.controller.UserClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class RestapiApplicationTests {
    UserClient userClient = new UserClient();

    /** To check if  returns the expected amount of users
     *
     * @throws IOException
     */
    @Test
    void getUsers() throws IOException {

        assertEquals(userClient.queryUsers().size(), 6);
    }

    /** To check if the first name of the first user in the list is “George”
     *
     * @throws IOException
     */

     @Test
    void checkFirstName() throws IOException {
        assertEquals(userClient.queryUsers().get(0).getFirst_name(), "George");
    }
}
