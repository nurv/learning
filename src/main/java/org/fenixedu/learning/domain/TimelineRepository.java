package org.fenixedu.learning.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by nurv on 07/03/16.
 */
@Repository
public interface TimelineRepository extends CrudRepository<Timeline, Long> {

    @Query("select timeline.posts from Timeline timeline where timeline = :timeline")
    List<Post> getPosts(@Param("timeline") Timeline timeline);
}
