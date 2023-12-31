package com.BloodStream.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BloodStream.dao.BankRepository;
import com.BloodStream.dao.UserAddressRepository;
import com.BloodStream.dao.UserRepository;
import com.BloodStream.pojos.BloodBank;
import com.BloodStream.pojos.User;
import com.BloodStream.pojos.UserAddress;
import com.BloodStream.services.IUserAddressService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = { "${com.BloodStream.ORIGINS}" })
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BankRepository bankRepo;

	@Autowired
	private UserAddressRepository addressRepository;

	@Autowired
	private IUserAddressService addressService;

// update user profile by user id
	@PostMapping("/update/{id}")
	public ResponseEntity<?> updateProfile(@PathVariable Long id, @RequestBody User user) {
		user.setId(id);
		return ResponseEntity.ok(userRepository.save(user));
	}

//update user address by user id
	@PostMapping("/update/address/{id}")
	public ResponseEntity<?> updateAddress(@PathVariable Long id, @RequestBody UserAddress address) {
		// User user = userRepository.getById(id);
		// user.setId(id);
		// address.setUser(user);
		return ResponseEntity.ok(addressService.saveAddress(id, address));
	}

//fetch user address by user id
	@GetMapping("/address/{id}")
	public ResponseEntity<?> updateAddress(@PathVariable Long id) {
		return ResponseEntity.ok(addressRepository.findById(id));
	}

//fetch user address by blood bank id
	@GetMapping("/bloodbank/address/{id}")
	public ResponseEntity<?> getAddress(@PathVariable Long id) {
		BloodBank bank = bankRepo.findById(id).orElseThrow();

		return ResponseEntity.ok(addressRepository.findById(bank.getUserId()));
	}

}
