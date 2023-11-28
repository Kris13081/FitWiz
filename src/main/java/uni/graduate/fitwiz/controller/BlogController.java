package uni.graduate.fitwiz.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uni.graduate.fitwiz.model.dto.BlogEntityDto;
import uni.graduate.fitwiz.service.BlogService;

import java.io.IOException;

@RestController
@RequestMapping("/api/admins")
public class BlogController {

    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @PostMapping("/management/add-blogcard")
    public ResponseEntity<String> create(BlogEntityDto blogEntityDto) throws IOException {
       return blogService.createBlog(blogEntityDto);
    }
}
