package org.fenixedu.learning.core.services;

import org.fenixedu.learning.core.domain.Page;
import org.fenixedu.learning.core.domain.PageRepository;
import org.fenixedu.learning.core.domain.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * Created by nurv on 11/03/16.
 */
@Service
public class PageService {

    private PageRepository pageRepository;

    @Autowired
    public PageService(PageRepository pageRepository) {
        this.pageRepository = pageRepository;
    }

    @Transactional
    public void createPage(String name, Set<Profile> admins){
        Page page = new Page(name, admins);
        page.getAdmins().addAll(admins);
        pageRepository.save(page);
    }

    public void deletePage(Page page){
         pageRepository.delete(page);
    }

    public void addAdmin(Page page, Set<Profile> admins){
        page.getAdmins().addAll(admins);
        pageRepository.save(page);
    }

    public void removeAdmin(Page page, Set<Profile> admins){
        page.getAdmins().remove(admins);
        pageRepository.save(page);
    }

    public void follow(Profile profile, Page page){
        page.getFollowers().add(profile);
        pageRepository.save(page);
    }

    public void unfollow(Profile profile, Page page){
        page.getFollowers().remove(profile);
        pageRepository.save(page);
    }
}
