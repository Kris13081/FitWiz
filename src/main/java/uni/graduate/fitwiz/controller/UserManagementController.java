package uni.graduate.fitwiz.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uni.graduate.fitwiz.model.dto.BannerUpdateDto;
import uni.graduate.fitwiz.model.dto.UserUpdateDto;
import uni.graduate.fitwiz.service.UserService;

@RestController
@RequestMapping("/api/admins/management")
public class UserManagementController {

    private final UserService userService;

    public UserManagementController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/delete/{id}")
    public ModelAndView deleteBanner(@PathVariable @RequestParam Long id) {
        userService.delete(id);

        return new ModelAndView("redirect:/api/admins/index");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/user/update/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UserUpdateDto userUpdateDto) {
        HttpStatus status = userService.updateUser(id, userUpdateDto);

        if (status == HttpStatus.OK) {
            return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
        }
    }
}
