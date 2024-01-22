package org.launchcode.capstonebackend.controllers;

import jakarta.validation.Valid;
import org.launchcode.capstonebackend.models.FriendsList;
import org.launchcode.capstonebackend.models.data.FriendsListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("friends")
public class FriendsListController {
    @Autowired
    private FriendsListRepository friendsListRepository;

    @PostMapping("add")
    public String processAddFriendForm(@ModelAttribute @Valid FriendsList newFriend,
                                       Errors errors, Model model) {

        if (errors.hasErrors()) {
            return "friends/add";
        } else {
            friendsListRepository.save(newFriend);
        }

        return "redirect:";
    }
}