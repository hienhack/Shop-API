package com.example.tutorial.dto.Type;

import com.example.tutorial.entity.Type;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TypeDTO {
    private Integer id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String image;

    public TypeDTO(Type entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.image = entity.getImage();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TypeDTO)) {
            return false;
        }

        return ((TypeDTO) o).getName().equals(this.getName());
    }
}
