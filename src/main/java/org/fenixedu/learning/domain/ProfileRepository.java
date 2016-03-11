package org.fenixedu.learning.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by nurv on 07/03/16.
 */
@Repository
public interface ProfileRepository extends CrudRepository<Profile, String>{

    Optional<Profile> findByUserId(String userId);
}
