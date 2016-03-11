package org.fenixedu.learning.api;

import org.fenixedu.learning.domain.Profile;
import org.fenixedu.learning.domain.ProfileRepository;
import org.fenixedu.learning.services.TimelineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.security.Principal;
import java.util.Optional;

/**
 * Created by nurv on 07/03/16.
 */
@RestController
@RequestMapping("/api/timeline")
@PreAuthorize("hasRole('USER')")
public class TimelineResource {

    private ProfileRepository userRepository;

    private TimelineService timelineService;

    @Autowired
    public TimelineResource(ProfileRepository userRepository, TimelineService timelineService) {
        this.userRepository = userRepository;
        this.timelineService = timelineService;
    }

    @RequestMapping(value="/own", method = RequestMethod.GET)
    public ResponseEntity<?> own(Principal principal){
        Optional<Profile> userOpt = userRepository.findByUserId(principal.getName());
        if(userOpt.isPresent()){
            return ResponseEntity.ok(timelineService.getPosts(userOpt.get().getOwnTimeline()));
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value="/own/{userId}", method=RequestMethod.GET)
    public ResponseEntity<?> usertimeline(Principal principal, @PathVariable("userId") String userId){
        Optional<Profile> userOpt = userRepository.findByUserId(userId);
        if(userOpt.isPresent()){
            return ResponseEntity.ok(timelineService.getPosts(userOpt.get().getOwnTimeline()));
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value="/home", method=RequestMethod.GET)
    public ResponseEntity<?> home(Principal principal){
        Optional<Profile> userOpt = userRepository.findByUserId(principal.getName());

        if(userOpt.isPresent()){
            return ResponseEntity.ok(timelineService.getPosts(userOpt.get().getHomeTimeline()));
        }else{
            return ResponseEntity.notFound().build();
        }

    }
}
