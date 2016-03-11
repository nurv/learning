package org.fenixedu.learning.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by nurv on 11/03/16.
 */
@Repository
public interface GroupRepository extends CrudRepository<Group,Long> {
}
