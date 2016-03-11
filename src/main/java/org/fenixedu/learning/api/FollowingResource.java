package org.fenixedu.learning.api;

import org.fenixedu.learning.domain.Profile;
import org.fenixedu.learning.domain.ProfileRepository;
import org.fenixedu.learning.services.FollowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Optional;

/**
 * Created by nurv on 10/03/16.
 */
@RestController
@RequestMapping("/api/friends")
@PreAuthorize("hasRole('USER')")
public class FollowingResource {


    private FollowingService followingService;
    private ProfileRepository profileRepository;

    @Autowired
    public FollowingResource(FollowingService followingService, ProfileRepository profileRepository) {
        this.followingService = followingService;
        this.profileRepository = profileRepository;
    }

    @RequestMapping(value="/followers", method = RequestMethod.POST)
    public ResponseEntity<?> followers(Principal principal, @RequestParam String user){
        Optional<Profile> principalOpt = profileRepository.findByUserId(principal.getName());

        if (principalOpt.isPresent()){
            return ResponseEntity.ok(followingService.getFollowers(principalOpt.get()));
        }else{

            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value="/friends", method = RequestMethod.POST)
    public ResponseEntity<?> friends(Principal principal, @RequestParam String user){
        Optional<Profile> principalOpt = profileRepository.findByUserId(principal.getName());

        if (principalOpt.isPresent()){
            return ResponseEntity.ok(followingService.getFriends(principalOpt.get()));
        }else{

            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value="/follow", method = RequestMethod.POST)
    public ResponseEntity<?> following(Principal principal, @RequestParam String user){
        Optional<Profile> principalOpt = profileRepository.findByUserId(principal.getName());
        Optional<Profile> userOpt = profileRepository.findByUserId(user);

        if (userOpt.isPresent()){
            followingService.follow(principalOpt.get(), userOpt.get());
            return ResponseEntity.ok(userOpt.get());
        }else{

            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value="/unfollow", method = RequestMethod.POST)
    public ResponseEntity<?> unfollow(Principal principal, @RequestParam String user){
        Optional<Profile> principalOpt = profileRepository.findByUserId(principal.getName());
        Optional<Profile> userOpt = profileRepository.findByUserId(user);

        if (userOpt.isPresent()){
            followingService.unfollow(principalOpt.get(), userOpt.get());
            return ResponseEntity.ok(userOpt.get());
        }else{

            return ResponseEntity.notFound().build();
        }
    }
}
