package com.example.tutorial.dto.Category;

import com.example.tutorial.entity.Category;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    private Integer id;

    @NotEmpty
    private String name;

    public CategoryDTO(Category entity) {
        this.id = entity.getId();
        this.name = entity.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CategoryDTO)) {
            return false;
        }

        return ((CategoryDTO) o).name.compareToIgnoreCase(this.name) == 0;
    }
}
