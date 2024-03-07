package com.example.tutorial.dto.Address;

import com.example.tutorial.entity.Address;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddressDTO {
    private Integer id;
    private @NotEmpty String province;
    private @NotEmpty String district;
    private @NotEmpty String ward;
    private String village;
    private @NotEmpty String address;

    public AddressDTO(Address entity) {
        this.id = entity.getId();
        this.province = entity.getProvince();
        this.district = entity.getDistrict();
        this.ward = entity.getWard();
        this.village = entity.getVillage();
        this.address = entity.getAddress();
    }
}
