package com.internproject.internintelligence_ecommerce.controller;

import java.util.List;

import com.internproject.internintelligence_ecommerce.dto.AddressDTO;
import com.internproject.internintelligence_ecommerce.entity.Address;
import com.internproject.internintelligence_ecommerce.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/address")
@RequiredArgsConstructor
public class AddressController {
	
	private final AddressService addressService;
	
	@PostMapping("/create")
	public ResponseEntity<AddressDTO> createAddress(@Valid @RequestBody AddressDTO addressDTO) {
		AddressDTO savedAddressDTO = addressService.createAddress(addressDTO);
		
		return new ResponseEntity<AddressDTO>(savedAddressDTO, HttpStatus.CREATED);
	}
	
	@GetMapping("/get-all")
	public ResponseEntity<List<AddressDTO>> getAddresses() {
		List<AddressDTO> addressDTOs = addressService.getAddresses();
		
		return new ResponseEntity<List<AddressDTO>>(addressDTOs, HttpStatus.FOUND);
	}
	
	@GetMapping("/{addressId}")
	public ResponseEntity<AddressDTO> getAddress(@PathVariable Long addressId) {
		AddressDTO addressDTO = addressService.getAddress(addressId);
		
		return new ResponseEntity<AddressDTO>(addressDTO, HttpStatus.FOUND);
	}
	
	@PutMapping("/update/{addressId}")
	public ResponseEntity<AddressDTO> updateAddress(@PathVariable Long addressId, @RequestBody AddressDTO addressDTO) {
		addressService.updateAddress(addressId, addressDTO);
		
		return new ResponseEntity<AddressDTO>(addressDTO, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{addressId}")
	public ResponseEntity<String> deleteAddress(@PathVariable Long addressId) {
		String status = addressService.deleteAddress(addressId);
		
		return new ResponseEntity<String>(status, HttpStatus.OK);
	}
}