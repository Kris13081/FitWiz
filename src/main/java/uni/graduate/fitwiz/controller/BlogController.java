package uni.graduate.fitwiz.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uni.graduate.fitwiz.model.dto.BannerUpdateDto;
import uni.graduate.fitwiz.model.dto.BlogEntityDto;
import uni.graduate.fitwiz.model.dto.BlogUpdateDto;
import uni.graduate.fitwiz.service.BlogService;

import java.io.IOException;

@RestController
@RequestMapping("/api/admins/management")
public class BlogController {

    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @PostMapping("/add-blogcard")
    public ResponseEntity<String> create(BlogEntityDto blogEntityDto) throws IOException {
       return blogService.createBlog(blogEntityDto);
    }

    @PostMapping("/blog/delete/{id}")
    public ModelAndView deleteBlog(@PathVariable @RequestParam Long id) {
        blogService.delete(id);

        return new ModelAndView("redirect:/api/admins/management/homepage-manager");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/blog/update/{id}")
    public HttpStatus updateBlog(@PathVariable Long id, @RequestBody BlogUpdateDto blogUpdateDto) {
        return blogService.updateBlog(id, blogUpdateDto);
    }
}
