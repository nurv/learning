package org.fenixedu.learning.services;

import org.fenixedu.learning.domain.Post;
import org.fenixedu.learning.domain.Timeline;
import org.fenixedu.learning.domain.TimelineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by nurv on 09/03/16.
 */
@Service
public class TimelineService {

    @Autowired
    private TimelineRepository timelineRepository;

    public List<Post> getPosts(Timeline timeline){
        return timelineRepository.getPosts(timeline);
    }
}
