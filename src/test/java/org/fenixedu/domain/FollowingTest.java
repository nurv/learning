package org.fenixedu.domain;

import org.fenixedu.learning.FenixeduLearningApplication;
import org.fenixedu.learning.core.domain.Following;
import org.fenixedu.learning.core.domain.FollowingRepository;
import org.fenixedu.learning.core.domain.Profile;
import org.fenixedu.learning.core.domain.ProfileRepository;
import org.fenixedu.learning.core.services.FollowingService;
import org.fenixedu.learning.core.services.ProfileService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by nurv on 08/03/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = FenixeduLearningApplication.class)
@WebAppConfiguration
public class FollowingTest {

    @Autowired
    private ProfileService profileService;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private FollowingService followingService;
    @Autowired
    private FollowingRepository followingRepository;

    @Before
    public void setup(){
        followingRepository.deleteAll();
    }

    @After
    public void tearDown(){
        followingRepository.deleteAll();
    }

    @Test
    public void testBasic(){
        Profile u1 = profileRepository.findByUserId("nurv").get();
        Profile u2 = profileRepository.findByUserId("admin").get();

        Following f = followingService.follow(u2,u1);

        assert f.getFollowee().equals(u1);
        assert f.getFollower().equals(u2);
    }

    @Test
    public void testFriend(){
        Profile u1 = profileRepository.findByUserId("nurv").get();
        Profile u2 = profileRepository.findByUserId("admin").get();

        Following f1 = followingService.follow(u2,u1);

        assert f1.isFriend() == false;

        Following f2 = followingService.follow(u1,u2);

        assert followingRepository.findOne(f1.getId()).isFriend()  == true;
        assert f2.isFriend()  == true;
    }

    @Test
    public void testUnfriend(){
        Profile u1 = profileRepository.findByUserId("nurv").get();
        Profile u2 = profileRepository.findByUserId("admin").get();

        Following f1 = followingService.follow(u2,u1);
        followingService.follow(u1,u2);

        followingService.unfollow(u1,u2);

        assert followingRepository.findOne(f1.getId()).isFriend() == false;
    }

    @Test
    public void testNoFollowers() {
        Profile u1 = profileRepository.findByUserId("nurv").get();
        Profile u2 = profileRepository.findByUserId("admin").get();

        assert followingService.getFollowers(u1).size() == 0;
        assert followingService.getFollowers(u2).size() == 0;
    }

    @Test
    public void testOneFollower() {
        Profile u1 = profileRepository.findByUserId("nurv").get();
        Profile u2 = profileRepository.findByUserId("admin").get();

        followingService.follow(u2,u1);

        assert followingService.getFollowers(u1).size() == 1;
        assert followingService.getFollowers(u2).size() == 0;
    }

    @Test
    public void testMutual() {
        Profile u1 = profileRepository.findByUserId("nurv").get();
        Profile u2 = profileRepository.findByUserId("admin").get();

        followingService.follow(u2,u1);
        followingService.follow(u1,u2);

        assert followingService.getFollowers(u1).size() == 1;
        assert followingService.getFollowers(u2).size() == 1;
        assert followingService.getFriends(u1).size() == 1;
        assert followingService.getFriends(u2).size() == 1;
        assert followingService.getOnlyFollowers(u1).size() == 0;
        assert followingService.getOnlyFollowers(u2).size() == 0;
    }

    @Test
    public void testUnfollow() {
        Profile u1 = profileRepository.findByUserId("nurv").get();
        Profile u2 = profileRepository.findByUserId("admin").get();

        followingService.follow(u2,u1);
        followingService.follow(u1,u2);
        followingService.unfollow(u2,u1);

        assert followingService.getFollowers(u1).size() == 0;
        assert followingService.getFollowers(u2).size() == 1;
        assert followingService.getFriends(u1).size() == 0;
        assert followingService.getFriends(u2).size() == 0;
        assert followingService.getOnlyFollowers(u1).size() == 0;
        assert followingService.getOnlyFollowers(u2).size() == 1;


    }
}
