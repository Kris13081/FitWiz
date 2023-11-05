package uni.graduate.fitwiz.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("api/users/login")
public class UserLoginController {

    @GetMapping()
    public ModelAndView getLoginPage() {
        return new ModelAndView("login");
    }

}
