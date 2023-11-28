package uni.graduate.fitwiz.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "blogs")
public class BlogEntity extends BaseEntity{

    @NotEmpty
    @Column(unique = true)
    private String name;

    @NotEmpty
    private String title;

    @NotEmpty
    private String description;

    @NotEmpty
    private String url;

    @NotEmpty
    private String imagePath;

    private LocalDateTime created;



}
