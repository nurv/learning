package org.fenixedu.learning.core.domain;

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

    @ManyToOne
    private Page page;

    @ManyToOne
    private Group group;

    protected Post(){}

    public Post(Profile profile, String content) {
        this.content = content;
        this.creator = profile;
    }

    public Post(Profile profile, String content, Page page) {
        this(profile,content);
        this.page = page;
    }

    public Post(Profile profile, String content, Group group) {
        this(profile,content);
        this.group = group;
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

    public Page getPage() {
        return page;
    }

    public Group getGroup() {
        return group;
    }
}
