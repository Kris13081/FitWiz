package uni.graduate.fitwiz.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping()
public class HomeController {

    @GetMapping("/api/home")
    public ModelAndView homePage() {
        return new ModelAndView("home");
    }


    @GetMapping("/error-continue")
    public ModelAndView home() {
        return new ModelAndView("home");
    }
}
