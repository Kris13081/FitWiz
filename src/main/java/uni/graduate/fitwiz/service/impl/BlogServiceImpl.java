package uni.graduate.fitwiz.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uni.graduate.fitwiz.model.dto.BlogDisplayDto;
import uni.graduate.fitwiz.model.dto.BlogEntityDto;
import uni.graduate.fitwiz.model.dto.BlogUpdateDto;
import uni.graduate.fitwiz.model.entity.BlogEntity;
import uni.graduate.fitwiz.repository.BlogRepository;
import uni.graduate.fitwiz.service.BlogService;
import uni.graduate.fitwiz.service.GcsService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

        if (optionalBlog.isPresent()) {
            return new ResponseEntity<>("Blog with this name already exist!", HttpStatus.FOUND);
        }

        BlogEntity blog = mapDtoToEntity(blogEntityDto);
        blogRepository.save(blog);

        return new ResponseEntity<>(" Blog created!", HttpStatus.CREATED);
    }

    @Override
    public List<BlogDisplayDto> getBlogs() {
        List<BlogEntity> allBlogs = blogRepository.findAll();
        List<BlogDisplayDto> dtoList = new ArrayList<>();

        allBlogs.forEach(blog -> dtoList.add(mapEntityToDto(blog)));

        return dtoList;
    }

    @Override
    public void delete(Long id) {
        Optional<BlogEntity> optionalBlog = blogRepository.findById(id);

        if (optionalBlog.isPresent()) {
            blogRepository.delete(optionalBlog.get());
        } else {
            throw new EntityNotFoundException("Blog with ID " + id + " not found");
        }
    }

    @Override
    public HttpStatus updateBlog(Long id, BlogUpdateDto blogUpdateDto) {
        Optional<BlogEntity> optionalBlog = blogRepository.findById(id);

        if (optionalBlog.isPresent()) {
            BlogEntity blogEntity = getBlogEntity(blogUpdateDto, optionalBlog);

            blogRepository.save(blogEntity);
            return HttpStatus.OK;
        } else {
            return HttpStatus.BAD_REQUEST;
        }
    }

    private static BlogEntity getBlogEntity(BlogUpdateDto blogUpdateDto, Optional<BlogEntity> optionalBlog) {
        BlogEntity blog = optionalBlog.get();

        // Update fields from the DTO
        if (blogUpdateDto.getName() != null) {
            blog.setName(blogUpdateDto.getName());
        }

        if (blogUpdateDto.getTitle() != null) {
            blog.setTitle(blogUpdateDto.getTitle());
        }

        if (blogUpdateDto.getDescription() != null) {
            blog.setDescription(blogUpdateDto.getDescription());
        }

        if (blogUpdateDto.getUrl() != null) {
            blog.setUrl(blogUpdateDto.getUrl());
        }
        return blog;
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

    private BlogDisplayDto mapEntityToDto(BlogEntity entity) {

        BlogDisplayDto dto = new BlogDisplayDto();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setUrl(entity.getUrl());
        dto.setImagePath(entity.getImagePath());

        return dto;
    }
}
