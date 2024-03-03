package com.example.tutorial.dto.Size;

import com.example.tutorial.entity.ProductSize;
import com.example.tutorial.entity.SizeDescription;
import com.example.tutorial.enumeration.Size;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SizeDTO {
    private Integer id;
    @NotNull
    private Size size;
    private Map<String, String> sizeGuide = new HashMap<>();

    public SizeDTO(ProductSize entity) {
        this.id = entity.getId();
        this.size = entity.getSize();
        for (SizeDescription des : entity.getSizeDescriptions()) {
            sizeGuide.put(des.getAttribute(), des.getValue());
        }
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof SizeDTO)) {
            return false;
        }

        return ((SizeDTO)other).size.equals(this.size);
    }
}