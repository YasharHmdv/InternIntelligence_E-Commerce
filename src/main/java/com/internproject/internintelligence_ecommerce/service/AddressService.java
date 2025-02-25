package com.internproject.internintelligence_ecommerce.service;


import com.internproject.internintelligence_ecommerce.dto.AddressDTO;
import com.internproject.internintelligence_ecommerce.entity.Address;

import java.util.List;

public interface AddressService {

    AddressDTO createAddress(AddressDTO addressDTO);

    List<AddressDTO> getAddresses();

    AddressDTO getAddress(Long addressId);

    AddressDTO updateAddress(Long addressId, Address address);

    String deleteAddress(Long addressId);
}