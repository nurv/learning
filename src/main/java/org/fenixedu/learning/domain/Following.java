package org.fenixedu.learning.domain;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;

/**
 * Created by nurv on 09/03/16.
 */
@Entity
public class Following extends AbstractPersistable<Long>{

    @ManyToOne
    private Profile follower;

    @ManyToOne
    private Profile followee;

    private boolean friend;

    protected Following(){

    }

    public Following(Profile follower, Profile followee) {
        this.follower = follower;
        this.followee = followee;
    }

    public Profile getFollower() {
        return follower;
    }

    public Profile getFollowee() {
        return followee;
    }

    public void setFriend(boolean friend) {
        this.friend = friend;
    }

    public boolean isFriend() {
        return friend;
    }
}
