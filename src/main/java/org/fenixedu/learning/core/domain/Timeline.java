package org.fenixedu.learning.core.domain;


import org.springframework.data.jpa.domain.AbstractPersistable;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

/**
 * Created by nurv on 07/03/16.
 */

@Entity
public class Timeline extends AbstractPersistable<Long> {

    @ManyToMany
    private List<Post> posts = new ArrayList<Post>();

    public Timeline(){}

    public List<Post> getPosts() {
        return posts;
    }
}
