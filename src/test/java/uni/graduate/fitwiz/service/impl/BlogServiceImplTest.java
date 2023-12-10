package uni.graduate.fitwiz.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uni.graduate.fitwiz.model.dto.BlogEntityDto;
import uni.graduate.fitwiz.model.dto.BlogUpdateDto;
import uni.graduate.fitwiz.model.entity.BlogEntity;
import uni.graduate.fitwiz.repository.BlogRepository;
import uni.graduate.fitwiz.service.GcsService;

import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

@SpringBootTest
class BlogServiceImplTest {

    @Autowired
    private BlogServiceImpl blogService;

    @MockBean
    private BlogRepository blogRepository;

    @MockBean
    private GcsService gcsService;

    @Test
    void testCreateBlogBlogAlreadyExists() throws IOException {
        // Given
        BlogEntityDto blogEntityDto = new BlogEntityDto();
        blogEntityDto.setName("Existing Blog");
        blogEntityDto.setTitle("Test Title");
        blogEntityDto.setDescription("Test Description");
        blogEntityDto.setUrl("http://testurl.com");

        when(blogRepository.findByName("Existing Blog")).thenReturn(Optional.of(new BlogEntity()));

        // When
        ResponseEntity<String> response = blogService.createBlog(blogEntityDto);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.getBody()).isEqualTo("Blog with this name already exists!");
    }


    @Test
    void testDeleteNonexistentBlog() {
        // Given
        Long nonExistentBlogId = 100L;
        when(blogRepository.findById(nonExistentBlogId)).thenReturn(Optional.empty());

        // When/Then
        assertThatThrownBy(() -> blogService.delete(nonExistentBlogId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Blog with ID 100 not found");
    }

    @Test
    void testUpdateNonexistentBlog() {
        // Given
        Long nonExistentBlogId = 100L;
        BlogUpdateDto blogUpdateDto = new BlogUpdateDto();
        blogUpdateDto.setName("Updated Blog Name");

        when(blogRepository.findById(nonExistentBlogId)).thenReturn(Optional.empty());

        // When
        HttpStatus result = blogService.updateBlog(nonExistentBlogId, blogUpdateDto);

        // Then
        assertThat(result).isEqualTo(HttpStatus.BAD_REQUEST);
    }

}