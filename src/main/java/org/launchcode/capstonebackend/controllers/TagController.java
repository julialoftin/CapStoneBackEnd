package org.launchcode.capstonebackend.controllers;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.launchcode.capstonebackend.models.MediaItem;
import org.launchcode.capstonebackend.models.Tag;
import org.launchcode.capstonebackend.models.User;
import org.launchcode.capstonebackend.models.data.MediaItemRepository;
import org.launchcode.capstonebackend.models.data.TagRepository;
import org.launchcode.capstonebackend.models.data.UserRepository;
import org.launchcode.capstonebackend.models.dto.MediaItemDTO;
import org.launchcode.capstonebackend.models.dto.TagDTO;
import org.launchcode.capstonebackend.models.dto.TagMediaItemCombinedDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/tag")
public class TagController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MediaItemRepository mediaItemRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    AuthenticationController authenticationController;

    private MediaItem convertMediaItemDTOToEntity(MediaItemDTO mediaItemDTO) {
        Optional<MediaItem> existingMediaItem = mediaItemRepository.findById(mediaItemDTO.getTmdbId());
        return existingMediaItem.orElseGet(() -> new MediaItem(mediaItemDTO.getTmdbId(), mediaItemDTO.getMediaType()));
    }

    private Tag convertTagDTOToEntity(TagDTO tagDTO) {
        Optional<Tag> existingTag = tagRepository.findByTagName(tagDTO.getName());
        return existingTag.orElseGet(() -> new Tag(tagDTO.getName()));
    }

    // Creates tag, but does not associate it with a media item
    @PostMapping("/create")
    public ResponseEntity<Tag> createTag(@RequestBody @Valid TagDTO tagDTO,
                                      Errors errors, HttpSession session) {
        try {
            if (errors.hasErrors()) {
                return ResponseEntity.badRequest().build();
            }
            User user = authenticationController.getUserFromSession(session);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            // Checks if a tag of the same name exists
            Optional<Tag> existingTag = tagRepository.findByTagName(tagDTO.getName());
            if (existingTag.isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }

            // Creates a new tag
            Tag tag = convertTagDTOToEntity(tagDTO);
            user.addTag(tag);
            userRepository.save(user);
            tagRepository.save(tag);

            return ResponseEntity.ok().body(tag);
        } catch (Exception exception) {
            System.out.println("Error saving tag: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PostMapping("/add-tag-to-media-item")
    public ResponseEntity<List<Tag>> addTagToMediaItem(@RequestBody @Valid TagMediaItemCombinedDTO tagMediaItemCombinedDTO,
                                                       Errors errors, HttpSession session) {
        try {
            if (errors.hasErrors()) {
                return ResponseEntity.badRequest().build();
            }
            User user = authenticationController.getUserFromSession(session);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            Tag tag = convertTagDTOToEntity(tagMediaItemCombinedDTO.getTagDTO());
            MediaItem mediaItem = convertMediaItemDTOToEntity(tagMediaItemCombinedDTO.getMediaItemDTO());

            tag.addMediaItem(mediaItem);
            tagRepository.save(tag);

            mediaItem.addTag(tag);
            mediaItemRepository.save(mediaItem);

            return ResponseEntity.ok().body(mediaItem.getTags());
        } catch (Exception exception) {
            System.out.println("Error saving tag: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
