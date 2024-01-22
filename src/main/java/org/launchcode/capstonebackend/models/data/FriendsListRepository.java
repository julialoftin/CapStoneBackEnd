package org.launchcode.capstonebackend.models.data;

import org.launchcode.capstonebackend.models.FriendsList;
import org.springframework.data.repository.CrudRepository;

public interface FriendsListRepository extends CrudRepository<FriendsList, Integer> {
}
