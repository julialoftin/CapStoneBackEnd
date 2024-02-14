package org.launchcode.capstonebackend.models.data;

import org.launchcode.capstonebackend.models.Tag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends CrudRepository<Tag, Integer> {

    Optional<Tag> findByTagName(String name);

}
