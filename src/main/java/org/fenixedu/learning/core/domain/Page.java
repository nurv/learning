package org.fenixedu.learning.core.domain;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by nurv on 07/03/16.
 */
@Entity
public class Page extends AbstractPersistable<Long> {
    private String name;

    protected Page(){

    }

    public Page(String name, Set<Profile> admins){
    }

    @ManyToMany
    private Set<Profile> followers = new HashSet<>();

    @ManyToMany
    private Set<Profile> admins = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    private Timeline timeline;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Profile> getFollowers() {
        return followers;
    }

    public Set<Profile> getAdmins() {
        return admins;
    }
}
