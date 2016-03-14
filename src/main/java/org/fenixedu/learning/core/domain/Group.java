package org.fenixedu.learning.core.domain;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by nurv on 11/03/16.
 */
@Entity
public class Group extends AbstractPersistable<Long> {
    private String name;

    protected Group(){
    }

    @ManyToMany
    private Set<Profile> followers = new HashSet<>();

    @ManyToMany
    private Set<Profile> admins = new HashSet<>();

    @ManyToMany
    private Set<Profile> users = new HashSet<>();

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

    public Set<Profile> getFollowers() {
        return followers;
    }

    public Set<Profile> getAdmins() {
        return admins;
    }

    public Set<Profile> getUsers() {
        return users;
    }
}
