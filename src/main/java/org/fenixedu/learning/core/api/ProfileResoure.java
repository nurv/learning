package org.fenixedu.learning.core.api;

import com.fasterxml.jackson.annotation.JsonView;
import org.fenixedu.learning.core.domain.Following;
import org.fenixedu.learning.core.domain.FollowingRepository;
import org.fenixedu.learning.core.domain.Profile;
import org.fenixedu.learning.core.domain.ProfileRepository;
import org.fenixedu.learning.core.services.FollowingService;
import org.fenixedu.learning.core.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Optional;

/**
 * Created by nurv on 08/03/16.
 */
@RestController
@RequestMapping("/api/profile")
@PreAuthorize("hasRole('USER')")
public class ProfileResoure {

    private ProfileService profileService;
    private ProfileRepository profileRepository;
    private FollowingService followingService;
    private FollowingRepository followingRepository;


    @Autowired
    public ProfileResoure(ProfileRepository profileRepository, ProfileService profileService, FollowingService followingService,
            FollowingRepository followingRepository) {
        this.profileRepository = profileRepository;
        this.profileService = profileService;
        this.followingService = followingService;
        this.followingRepository = followingRepository;
    }

    private class ProfileView {

        @JsonView()
        public final String url;
        @JsonView()
        public final String userId;
        @JsonView()
        public final Long timeline;
        @JsonView()
        public final boolean isYou;
        @JsonView()
        public final boolean isFollowing;
        @JsonView()
        public final boolean isFriend;

        public ProfileView(Profile user, Profile from) {
            this.url = user.getUrl();
            this.userId = user.getUserId();
            this.timeline = user.getOwnTimeline().getId();
            this.isYou = user.equals(from);
            Optional<Following> followOpt = followingRepository.getFollowing(from, user);

            this.isFollowing = followOpt.isPresent();
            this.isFriend = followOpt.map(x -> x.isFriend()).orElse(false);

        }
    }

    @RequestMapping("/{userId}")
    public ResponseEntity<?> profile(Principal principal, @PathVariable("userId") String userId){
        Optional<Profile> userOpt = profileRepository.findByUserId(principal.getName());
        Optional<Profile> refOpt = profileRepository.findByUserId(userId);
        if(refOpt.isPresent()){
            return ResponseEntity.ok(new ProfileView(refOpt.get(),userOpt.get()));
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
