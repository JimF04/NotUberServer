package com.apiproyect.NotUberServer.Controller;

import com.apiproyect.NotUberServer.Model.Driver;
import com.apiproyect.NotUberServer.Model.Employee;
import com.apiproyect.NotUberServer.Model.LogIn;
import com.apiproyect.NotUberServer.Model.FriendRequest;
import com.apiproyect.NotUberServer.XMLHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


/**
 * Controlador que gestiona las operaciones de amigos para conductores y empleados.
 */
@RestController
@RequestMapping("/api")
public class FriendController {

    @Autowired
    XMLHandler xmlHandler;

    @PostMapping("/addfriend")
    public ResponseEntity addFriend(@RequestBody FriendRequest friendRequest) {
        String friendname = friendRequest.getFriend();
        String email = friendRequest.getEmail();

        xmlHandler.addFriend(friendname, email);
        return new ResponseEntity<>("friend added successfully", HttpStatus.OK);
    }

    @PostMapping("/friends")
    public String getFriends(@RequestBody String email) {
        return xmlHandler.getFriends(email);
    }
}
