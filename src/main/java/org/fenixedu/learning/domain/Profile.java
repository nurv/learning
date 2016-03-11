package org.fenixedu.learning.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.util.ClassUtils;

import javax.persistence.*;
import java.util.List;

/**
 * Created by nurv on 07/03/16.
 */

@Entity
public class Profile{

    @Id
    private String userId;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private Timeline homeTimeline;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private Timeline ownTimeline;

    protected Profile(){}

    public Profile(String username){
        this.userId = username;
        this.ownTimeline = new Timeline();
        this.homeTimeline = new Timeline();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userid) {
        this.userId = userid;
    }

    public Timeline getHomeTimeline() {
        return homeTimeline;
    }

    public void setHomeTimeline(Timeline homeTimeline) {
        this.homeTimeline = homeTimeline;
    }

    public Timeline getOwnTimeline() {
        return ownTimeline;
    }

    public void setOwnTimeline(Timeline ownTimeline) {
        this.ownTimeline = ownTimeline;
    }

    @Override
    public boolean equals(Object obj) {

        if (null == obj) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        if (!getClass().equals(ClassUtils.getUserClass(obj))) {
            return false;
        }

        Profile that = (Profile) obj;

        return null == this.getUserId() ? false : this.getUserId().equals(that.getUserId());
    }

    @Override public int hashCode() {
        return getUserId().hashCode();
    }

    @JsonProperty("url")
    public String getUrl(){
        return "/user/" + userId;
    }
}
