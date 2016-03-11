package org.fenixedu.learning.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Created by nurv on 09/03/16.
 */
@Repository
public interface FollowingRepository extends CrudRepository<Following,Long> {

    @Query("SELECT following.follower FROM Following following JOIN following.follower WHERE following.followee = :profile")
    Set<Profile> getFollowers(@Param("profile") Profile profile);

    @Query("SELECT following.follower FROM Following following JOIN following.follower WHERE following.friend = true AND following.followee = :profile")
    Set<Profile> getFriends(@Param("profile") Profile profile);

    @Query("SELECT following FROM Following following WHERE following.followee = :followee AND following.follower = :follower")
    Optional<Following> getFollowing(@Param("follower") Profile follower, @Param("followee") Profile followee);

}

