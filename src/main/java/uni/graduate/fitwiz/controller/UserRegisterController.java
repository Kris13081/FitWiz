package uni.graduate.fitwiz.controller;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uni.graduate.fitwiz.model.dto.UserEntityDto;
import uni.graduate.fitwiz.service.UserService;

import java.io.IOException;

@RestController
@RequestMapping("/api/users")
public class UserRegisterController {

    private final UserService userService;

    public UserRegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public ModelAndView getRegisterPage() {
        return new ModelAndView("register");
    }

    @PostMapping("/registration")
    public ModelAndView registerUser(@Valid UserEntityDto userEntityDto,
                                            BindingResult bindingResult,
                                            RedirectAttributes rAtt) throws IOException {

        if (bindingResult.hasErrors()) {
            rAtt.addFlashAttribute("userEntityDto", userEntityDto);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.userEntityDto", bindingResult);

            return new ModelAndView("register");
        }

        boolean alreadyExists = userService.create(userEntityDto);

        return alreadyExists ? new ModelAndView("redirect:/api/users/login") : new ModelAndView("register");
    }

}
