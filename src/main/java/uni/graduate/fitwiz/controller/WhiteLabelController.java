package uni.graduate.fitwiz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WhiteLabelController {

    @GetMapping("/error")
    public String redirectToHomePage() {
        return "redirect:/api/home";
    }
}