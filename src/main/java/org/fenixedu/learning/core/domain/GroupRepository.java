package org.fenixedu.learning.core.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Created by nurv on 11/03/16.
 */
@Repository
public interface GroupRepository extends CrudRepository<Group,Long> {
    @Query("SELECT following.follower FROM Following following JOIN following.follower WHERE following.followee = :profile")
    Set<Profile> getFollowers(@Param("profile") Profile profile);
}
