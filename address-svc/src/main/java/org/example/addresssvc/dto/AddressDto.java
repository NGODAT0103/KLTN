package org.example.addresssvc.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;
@Getter
@Setter
public class AddressDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID uuid ;
    @NotNull
    private String name;
    @NotNull
    private String cityName;
    @NotNull
    private String phoneNumber;
    @NotNull
    private String province;
    @NotNull String street;
    @NotNull
    private String ward;
    @NotNull
    private String district;
    @NotNull
    private String addressType;


}
