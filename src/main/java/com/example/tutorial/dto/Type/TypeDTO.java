package com.example.tutorial.dto.Type;

import com.example.tutorial.entity.Type;
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
    private String name;
    private String image;

    public TypeDTO(Type entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.image = entity.getImage();
    }
}
