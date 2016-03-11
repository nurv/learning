package org.fenixedu.learning.domain;

import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.util.ClassUtils;

import javax.persistence.*;
import java.util.List;

/**
 * Created by nurv on 07/03/16.
 */
@Entity
public class Page extends AbstractPersistable<Long> {
    private String name;

    protected Page(){
    }

    @ManyToMany
    private List<Profile> followers;

    @OneToOne(cascade = CascadeType.ALL)
    private Timeline timeline;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Profile> getFollowers() {
        return followers;
    }
}
