package uni.graduate.fitwiz.service;

import org.springframework.http.ResponseEntity;
import uni.graduate.fitwiz.model.dto.BlogEntityDto;
import uni.graduate.fitwiz.model.entity.BlogEntity;

import java.io.IOException;
import java.util.List;

public interface BlogService {
    ResponseEntity<String> createBlog(BlogEntityDto blogEntityDto) throws IOException;

    List<BlogEntity> getBlogs();
}
