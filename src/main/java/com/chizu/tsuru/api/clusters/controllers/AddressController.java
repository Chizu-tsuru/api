package com.chizu.tsuru.api.clusters.controllers;


import com.chizu.tsuru.api.clusters.dto.GetAddressDTO;
import com.chizu.tsuru.api.clusters.services.AddressService;
import com.chizu.tsuru.api.shared.services.ResponseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/addresses")
public class AddressController {
    private final ResponseService responseService;
    private final AddressService addressService;

    public AddressController(ResponseService responseService, AddressService addressService) {
        this.responseService = responseService;
        this.addressService = addressService;
    }


    @GetMapping("/{idLocation}")
    public GetAddressDTO getLocation(@PathVariable("idLocation") Integer idAddress) {
        return this.responseService
                .getAddressDTO(this.addressService.getAddress(idAddress));
    }
}
