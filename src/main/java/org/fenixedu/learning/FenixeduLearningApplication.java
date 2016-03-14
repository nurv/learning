package org.fenixedu.learning;

import org.fenixedu.learning.core.domain.Profile;
import org.fenixedu.learning.core.domain.ProfileRepository;
import org.fenixedu.learning.core.services.PostService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

@SpringBootApplication
//@EnableConnectIntegration(enableCors = true)
public class FenixeduLearningApplication implements InitializingBean {

    @Autowired
    private ProfileRepository userRepository;

    @Autowired
    private PostService postService;

    public static void main(String[] args) {
        SpringApplication.run(FenixeduLearningApplication.class, args);
    }

    @Override public void afterPropertiesSet() throws Exception {
        Profile user1 = new Profile("nurv");
        Profile user2 = new Profile("admin");
        userRepository.save(user1);
        userRepository.save(user2);
    }

    @Autowired
    protected void configureUsers(AuthenticationManagerBuilder auth) throws Exception {
        if (!auth.isConfigured()) {
            auth.inMemoryAuthentication().withUser("nurv").password("pass").roles("USER").and().withUser("admin")
                    .password("admin").roles("USER", "ADMIN");
        }
    }

}