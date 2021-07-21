package com.example.restapi.controller;

import com.example.restapi.model.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static java.util.Arrays.asList;


@RestController
public class UserClient {

    public static final String REQRES_URL = "https://reqres.in/api/users?page=1";
    public static final int TIMEOUT = 5000;
    public static final String KEY = "data";

    /**
     * Method that calls list of users from the URL
     */
    private static HttpURLConnection con = null;

    @GetMapping("/users")
    public List<UserDTO> queryUsers() throws IOException {
        BufferedReader reader;
        String inputLine;
        StringBuilder response = new StringBuilder();
        try {
            URL url = new URL(REQRES_URL);
            con = (HttpURLConnection) url.openConnection();
            // Request setup
            con.setRequestMethod("GET");
            con.setConnectTimeout(TIMEOUT);
            con.setReadTimeout(TIMEOUT);
            int status = con.getResponseCode();
            // System.out.println(status);
            if (status > 299) {   // To check if the url is active or not
                reader = new BufferedReader(
                        new InputStreamReader(con.getErrorStream()));
            } else {
                reader = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
            }
            while ((inputLine = reader.readLine()) != null) {
                response.append(inputLine);
            }
            reader.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } finally {
            con.disconnect();
        }

        JSONObject obj_JSONObject = new JSONObject(response.toString());
        JSONArray array = obj_JSONObject.getJSONArray(KEY);
        ObjectMapper mapper = new ObjectMapper();
        List<UserDTO> userDTOList = asList(mapper.readValue(array.toString(), UserDTO[].class));
        for (UserDTO userDTO : userDTOList) {
            System.out.println(userDTO.getFirst_name() + "  " + userDTO.getLast_name()); // Prints the First name and Last name from 1st page.
        }
        return userDTOList;
    }

}
