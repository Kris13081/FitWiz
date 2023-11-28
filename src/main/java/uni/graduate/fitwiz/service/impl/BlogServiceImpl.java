package uni.graduate.fitwiz.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uni.graduate.fitwiz.model.dto.BlogEntityDto;
import uni.graduate.fitwiz.model.entity.BlogEntity;
import uni.graduate.fitwiz.repository.BlogRepository;
import uni.graduate.fitwiz.service.BlogService;
import uni.graduate.fitwiz.service.GcsService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;
    private final GcsService gcsService;

    public BlogServiceImpl(BlogRepository blogRepository,
                           GcsService gcsService) {
        this.blogRepository = blogRepository;
        this.gcsService = gcsService;
    }


    @Override
    public ResponseEntity<String> createBlog(BlogEntityDto blogEntityDto) throws IOException {

        Optional<BlogEntity> optionalBlog = blogRepository.findByName(blogEntityDto.getName());

        if (optionalBlog.isPresent()){
            return new ResponseEntity<>("Blog with this name already exist!", HttpStatus.FOUND);
        }

        BlogEntity blog = mapDtoToEntity(blogEntityDto);
        blogRepository.save(blog);

        return new ResponseEntity<>(" Blog created!", HttpStatus.CREATED);
    }

    @Override
    public List<BlogEntity> getBlogs() {
        return blogRepository.findAll();
    }

    private BlogEntity mapDtoToEntity(BlogEntityDto blogEntityDto) throws IOException {

        BlogEntity newBlog = new BlogEntity();
        String blogImage = gcsService.uploadBlogImage("fitwiz_images_bucket", blogEntityDto.getImage());

        newBlog.setName(blogEntityDto.getName());
        newBlog.setTitle(blogEntityDto.getTitle());
        newBlog.setDescription(blogEntityDto.getDescription());
        newBlog.setUrl(blogEntityDto.getUrl());
        newBlog.setImagePath(blogImage);
        newBlog.setCreated(LocalDateTime.now());

        return newBlog;
    }
}
