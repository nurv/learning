package org.fenixedu.learning.core.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by nurv on 11/03/16.
 */
@RestController
@RequestMapping("/api/friends")
@PreAuthorize("hasRole('USER')")
public class PageRepository {


}
