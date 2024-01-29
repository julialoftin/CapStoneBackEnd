package org.launchcode.capstonebackend.controllers;

import jakarta.validation.Valid;
import org.launchcode.capstonebackend.models.FriendsList;
import org.launchcode.capstonebackend.models.WatchList;
import org.launchcode.capstonebackend.models.data.FriendsListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("friends")
public class FriendsListController {
    @Autowired
    private FriendsListRepository friendsListRepository;

    @GetMapping("get-friendslist")
    public ResponseEntity<List<FriendsList>> getAllFriends() {

        List<FriendsList> friendsList = (List<FriendsList>) friendsListRepository.findAll();

        if (friendsList.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(friendsList);
        }
    }
    @PostMapping("add-friend")
    public ResponseEntity<FriendsList> processAddNewFriendForm(@RequestBody @Valid FriendsList newFriendsList, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        try {
            FriendsList savedFriendsList = friendsListRepository.save(newFriendsList);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedFriendsList);
        } catch (Exception exception) {
            System.out.println("Error saving FriendsList to database: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}