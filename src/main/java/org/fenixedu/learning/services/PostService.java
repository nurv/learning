package org.fenixedu.learning.services;

import org.fenixedu.learning.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by nurv on 08/03/16.
 */
@Service
public class PostService {

    private FollowingService followingService;

    private PostRepository postRepository;

    private TimelineRepository timelineRepository;

    @Autowired
    public PostService(FollowingService followingService, PostRepository postRepository, TimelineRepository timelineRepository) {
        this.followingService = followingService;
        this.postRepository = postRepository;
        this.timelineRepository = timelineRepository;
    }

    @Transactional
    public Post post(Profile profile, String content){
        Post post = new Post(profile, content);
        postRepository.save(post);

        Timeline own = timelineRepository.findOne(profile.getHomeTimeline().getId());
        own.getPosts().add(0,post);
        timelineRepository.save(own);

        Timeline home = timelineRepository.findOne(profile.getOwnTimeline().getId());
        home.getPosts().add(0,post);
        timelineRepository.save(home);

        for(Profile follower : followingService.getFollowers(profile)){
            home = timelineRepository.findOne(follower.getHomeTimeline().getId());
            home.getPosts().add(0,post);
            timelineRepository.save(home);
        }
        return post;
    }

    @Transactional
    public void deletePost(Post post){
        postRepository.getTimelines(post).stream().forEach(x -> x.getPosts().remove(post));
        postRepository.delete(post);
    }
}
