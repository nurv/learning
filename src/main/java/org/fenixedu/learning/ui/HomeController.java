package org.fenixedu.learning.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

/**
 * Created by nurv on 10/03/16.
 */
@Controller
@RequestMapping
public class HomeController {

    @RequestMapping("/")
    public String home(Principal principal, Model model){
        model.addAttribute("user", principal.getName());
        return "home";
    }
}
