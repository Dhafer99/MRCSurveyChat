package com.mrc.chat.controller;


import com.mrc.chat.dto.RegisterRequest;
import com.mrc.chat.dto.RegisterResponse;
import com.mrc.chat.dto.UserDTO;
import com.mrc.chat.enumirators.Role;
import com.mrc.chat.exception.UserException;
import com.mrc.chat.model.User;
import com.mrc.chat.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/gethr")
    public List<User> getHrs(){

        return userService.getHrs();

    }

    @PostMapping("/create-hr")
    public ResponseEntity<RegisterResponse> registerHR(@Valid @RequestBody RegisterRequest registerRequest, BindingResult result) {
        RegisterResponse registerResponse = new RegisterResponse();
        if (result.hasErrors()) {
            registerResponse.setMessageResponse("Validation failed");
            registerResponse.setEmailResponse(registerRequest.getEmail());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(registerResponse);
        }
        try {
            UserDTO userDTO = UserDTO.builder()
                    .username(registerRequest.getUsername())
                    .email(registerRequest.getEmail())
                    .firstName(registerRequest.getFirstName())
                    .lastName(registerRequest.getLastName())
                    .phoneNumber(registerRequest.getPhoneNumber())
                    .password(registerRequest.getPassword())
                    .build();
            userService.registerAccount(userDTO, Role.HR);
            registerResponse.setEmailResponse(registerRequest.getEmail());
            registerResponse.setMessageResponse("Account Created");
            return ResponseEntity.status(HttpStatus.CREATED).body(registerResponse);
        } catch (UserException e) {
            registerResponse.setMessageResponse(e.getMessage());
            registerResponse.setEmailResponse(registerRequest.getEmail());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(registerResponse);
        } catch (Exception e) {
            registerResponse.setMessageResponse(e.getMessage());
            registerResponse.setEmailResponse(registerRequest.getEmail());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(registerResponse);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(userService.updateUser(id, userDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    /*@PostMapping("/profile/image")
    public ResponseEntity<Void> uploadImage(@RequestParam MultipartFile file, Authentication authentication) throws IOException {
        UserDTO userDTO = userService.getUserByEmail(authentication.getName());
        userDTO.setProfileImage(file.getBytes());
        userService.updateUser(userDTO.getId(), userDTO);
        return ResponseEntity.ok().build();
    }*/
}
