package org.fenixedu.learning.core.services;

import com.google.common.collect.Sets;
import org.fenixedu.learning.core.domain.Following;
import org.fenixedu.learning.core.domain.FollowingRepository;
import org.fenixedu.learning.core.domain.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

/**
 * Created by nurv on 09/03/16.
 */
@Service
public class FollowingService {

    @Autowired
    private FollowingRepository followingRepository;

    public Set<Profile> getFollowers(Profile profile){
        return followingRepository.getFollowers(profile);
    }

    public Set<Profile> getOnlyFollowers(Profile profile){
        return Sets.difference(getFollowers(profile),getFriends(profile));
    }

    public Set<Profile> getFriends(Profile profile){
        return followingRepository.getFriends(profile);
    }

    public Following follow(Profile follower, Profile followee){
        Optional<Following> following = followingRepository.getFollowing(follower, followee);
        if (!following.isPresent()){
            Following f = new Following(follower,followee);
            Optional<Following> reverse = followingRepository.getFollowing(followee, follower);
            if(reverse.isPresent()){
                f.setFriend(true);
                Following rev = reverse.get();
                rev.setFriend(true);
                followingRepository.save(rev);
            }
            followingRepository.save(f);
            return f;
        }else{
            return following.get();
        }
    }

    public void unfollow(Profile follower, Profile followee){
        Optional<Following> following = followingRepository.getFollowing(follower, followee);
        if(following.isPresent()){
            Optional<Following> reverse = followingRepository.getFollowing(followee, follower);
            if(reverse.isPresent()){
                Following rev = reverse.get();
                rev.setFriend(false);
                followingRepository.save(rev);
            }

            followingRepository.delete(following.get());
        }
    }


}
