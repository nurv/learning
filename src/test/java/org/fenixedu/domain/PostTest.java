package org.fenixedu.domain;

import org.fenixedu.learning.FenixeduLearningApplication;
import org.fenixedu.learning.domain.*;
import org.fenixedu.learning.services.FollowingService;
import org.fenixedu.learning.services.PostService;
import org.fenixedu.learning.services.TimelineService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;
import java.util.stream.StreamSupport;

/**
 * Created by nurv on 09/03/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = FenixeduLearningApplication.class)
@WebAppConfiguration
public class PostTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private FollowingService followingService;

    @Autowired
    private FollowingRepository followingRepository;

    @Autowired
    private ProfileRepository profileRepository;
    private Profile u1;
    private Profile u2;

    @Autowired
    private TimelineService timelineService;

    @Autowired
    private TimelineRepository timelineRepository;

    @Before
    public void setup(){
        u1 = profileRepository.findByUserId("nurv").get();
        u2 = profileRepository.findByUserId("admin").get();
        followingRepository.deleteAll();
        StreamSupport.stream(postRepository.findAll().spliterator(), false).forEach(postService::deletePost);

    }

    @Test
    public void testPost(){
        followingService.follow(u2,u1);
        postService.post(u1, "Foobar");

        List<Post> u1OwnTimeline = timelineService.getPosts(u1.getOwnTimeline());
        List<Post> u2OwnTimeline = timelineService.getPosts(u2.getOwnTimeline());

        List<Post> u1HomeTimeline = timelineService.getPosts(u1.getHomeTimeline());
        List<Post> u2HomeTimeline = timelineService.getPosts(u2.getHomeTimeline());

        assert u1OwnTimeline.size() == 1;
        assert u2OwnTimeline.size() == 0;

        assert u1HomeTimeline.size() == 1;
        assert u2HomeTimeline.size() == 1;
    }

    @Test
    public void testOrder() {
        followingService.follow(u2, u1);

        for (int i=0; i<10; i++){
            postService.post(u1, "" + i);
        }

        Post post = postService.post(u1, "Baz");

        List<Post> u1OwnTimeline = timelineService.getPosts(u1.getOwnTimeline());

        for (int i=0; i<10; i++){
            u1OwnTimeline.get(0).getContent().equals("" + (100 - i));
        }

    }

    @Test
    public void testDeletePost(){
        followingService.follow(u2,u1);
        postService.post(u1, "Foo");
        postService.post(u1, "Bar");
        Post post = postService.post(u1, "Baz");

        List<Post> u1OwnTimeline = timelineService.getPosts(u1.getOwnTimeline());
        List<Post> u2OwnTimeline = timelineService.getPosts(u2.getOwnTimeline());

        List<Post> u1HomeTimeline = timelineService.getPosts(u1.getHomeTimeline());
        List<Post> u2HomeTimeline = timelineService.getPosts(u2.getHomeTimeline());

        assert u1OwnTimeline.size() == 3;
        assert u2OwnTimeline.size() == 0;

        assert u1HomeTimeline.size() == 3;
        assert u2HomeTimeline.size() == 3;

        postService.deletePost(post);

        u1OwnTimeline = timelineService.getPosts(u1.getOwnTimeline());
        u2OwnTimeline = timelineService.getPosts(u2.getOwnTimeline());

        u1HomeTimeline = timelineService.getPosts(u1.getHomeTimeline());
        u2HomeTimeline = timelineService.getPosts(u2.getHomeTimeline());

        assert u1OwnTimeline.size() == 2;
        assert u2OwnTimeline.size() == 0;

        assert u1HomeTimeline.size() == 2;
        assert u2HomeTimeline.size() == 2;
    }

}
