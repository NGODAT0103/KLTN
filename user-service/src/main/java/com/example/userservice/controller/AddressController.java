package com.example.userservice.controller;
import com.example.userservice.dto.address.AddressDto;
import com.example.userservice.dto.address.AddressResponseDto;
import com.example.userservice.persistence.entity.Customer;
import com.example.userservice.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.security.auth.login.AccountNotFoundException;
import java.util.UUID;
@RestController
@RequestMapping(value = "/address")
public class AddressController {

    private static final String CUSTOMER_UUID_HEADER = "X-Customer-Uuid";

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createAddress(@RequestBody @Valid AddressDto addressDto,
                              @RequestHeader (value = CUSTOMER_UUID_HEADER) UUID xCusomerUuid) throws AccountNotFoundException {
        addressService.createAddress(xCusomerUuid,addressDto);
    }
    @GetMapping
    public AddressResponseDto getAddresses(@RequestHeader (value = CUSTOMER_UUID_HEADER)  UUID xCusomerUuid) throws AccountNotFoundException {
        return addressService.getAddresses(xCusomerUuid);
    }
    @PutMapping(path = "/{addressUuid}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateAddress(@RequestBody @Valid AddressDto addressDto
            ,@RequestHeader (value = CUSTOMER_UUID_HEADER)  UUID xCusomerUuid, @PathVariable UUID addressUuid) throws AccountNotFoundException {
        addressService.updateAddress(xCusomerUuid,addressUuid,addressDto);
    }
    @DeleteMapping(path = "/{addressUuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAddress(@RequestHeader(value = CUSTOMER_UUID_HEADER) UUID xCusomerUuid
            , @PathVariable UUID addressUuid) throws AccountNotFoundException {
        addressService.deleteAddress(xCusomerUuid,addressUuid);
    }

}