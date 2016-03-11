package org.fenixedu.learning.domain;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by nurv on 07/03/16.
 */
@Entity
public class Post extends AbstractPersistable<Long> {

    @ManyToMany(mappedBy = "posts")
    private Set<Timeline> timelines;

    @ManyToOne
    private Profile creator;

    protected Post(){}

    public Post(Profile profile, String content) {
        this.content = content;
        this.creator = profile;
    }

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Profile getCreator() {
        return creator;
    }
}
