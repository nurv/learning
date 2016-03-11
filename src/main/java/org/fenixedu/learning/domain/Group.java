package org.fenixedu.learning.domain;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import java.util.List;

/**
 * Created by nurv on 11/03/16.
 */
@Entity
public class Group extends AbstractPersistable<Long> {
    private String name;

    protected Group(){
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

    public Timeline getTimeline() {
        return timeline;
    }
}
