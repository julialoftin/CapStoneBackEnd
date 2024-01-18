package org.launchcode.capstonebackend.models.data;

import org.launchcode.capstonebackend.models.WatchList;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WatchListRepository extends CrudRepository<WatchList, Integer> {
}
