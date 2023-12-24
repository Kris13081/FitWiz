package uni.graduate.fitwiz.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uni.graduate.fitwiz.model.dto.BlogDisplayDto;
import uni.graduate.fitwiz.model.dto.BlogEntityDto;
import uni.graduate.fitwiz.model.dto.BlogUpdateDto;

import java.io.IOException;
import java.util.List;

public interface BlogService {
    ResponseEntity<String> createBlog(BlogEntityDto blogEntityDto) throws IOException;

    List<BlogDisplayDto> getBlogs();

    void delete(Long id);

    HttpStatus updateBlog(Long id, BlogUpdateDto blogUpdateDto);
}
