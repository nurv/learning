package org.fenixedu.learning.core.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by nurv on 07/03/16.
 */
@Repository
public interface PageRepository extends CrudRepository<Page,Long> {
}
