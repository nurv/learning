package org.fenixedu.learning.core.api;

import org.fenixedu.learning.core.domain.Post;
import org.fenixedu.learning.core.domain.PostRepository;
import org.fenixedu.learning.core.domain.Profile;
import org.fenixedu.learning.core.domain.ProfileRepository;
import org.fenixedu.learning.core.services.PostService;
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
 * Created by nurv on 08/03/16.
 */
@RestController
@RequestMapping("/api/post")
@PreAuthorize("hasRole('USER')")
public class PostResource {

    private PostService postService;
    private PostRepository postRepository;
    private ProfileRepository profileRepository;

    @Autowired
    public PostResource(PostService postService, PostRepository postRepository, ProfileRepository profileRepository) {
        this.postService = postService;
        this.postRepository = postRepository;
        this.profileRepository = profileRepository;
    }

    @RequestMapping(value="/create", method = RequestMethod.POST)
    public ResponseEntity<?> follow(Principal principal, @RequestParam String content){
        Optional<Profile> userOpt = profileRepository.findByUserId(principal.getName());
        if (userOpt.isPresent()){
            postService.post(userOpt.get(),content);
            return ResponseEntity.ok(userOpt.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value="/delete", method = RequestMethod.POST)
    public ResponseEntity<?> follow(Principal principal, @RequestParam Long postId){
        Post post = postRepository.findOne(postId);
        if (post != null){
            postService.deletePost(post);
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
