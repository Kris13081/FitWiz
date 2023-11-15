package uni.graduate.fitwiz.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

    @GetMapping("/menu")
    public ModelAndView getAdminMenuPage(){
        return new ModelAndView( "/admin/admin-menu");
    }
}
