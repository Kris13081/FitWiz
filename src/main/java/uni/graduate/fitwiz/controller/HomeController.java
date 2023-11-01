package uni.graduate.fitwiz.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/api/home")
public class HomeController {

    @GetMapping()
    public ModelAndView homePage() {
        return new ModelAndView("home");
    }

}
