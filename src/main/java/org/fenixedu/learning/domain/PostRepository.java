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
public interface PostRepository extends CrudRepository<Post,Long> {

    @Query("select timeline from Timeline timeline inner join timeline.posts post where post = :post")
    List<Timeline> getTimelines(@Param("post") Post post);
}
