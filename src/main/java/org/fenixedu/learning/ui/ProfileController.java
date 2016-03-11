package org.fenixedu.learning.ui;

import org.fenixedu.learning.domain.Following;
import org.fenixedu.learning.domain.FollowingRepository;
import org.fenixedu.learning.domain.Profile;
import org.fenixedu.learning.domain.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Optional;

/**
 * Created by nurv on 10/03/16.
 */
@Controller
@RequestMapping("/user")
public class ProfileController {

    @Autowired
    private FollowingRepository followingRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @RequestMapping("/{username}")
    public String profile(Principal principal, @PathVariable("username") String username, Model model){
        model.addAttribute("user",username);
        model.addAttribute("isYou",principal.getName().equals(username));

        Profile you = profileRepository.findOne(principal.getName());
        Profile other = profileRepository.findOne(username);
        Optional<Following> following = followingRepository.getFollowing(you, other);
        model.addAttribute("isFollowing", following.isPresent());
        model.addAttribute("isFriend", following.map(x->x.isFriend()).orElse(false));

        return "profile";
    }
}
