package com.mrc.chat.serviceimpl;


import com.mrc.chat.config.PasswordEncoder;
import com.mrc.chat.dto.UserDTO;
import com.mrc.chat.enumirators.Role;
import com.mrc.chat.exception.UserException;
import com.mrc.chat.exception.UserNotFoundException;
import com.mrc.chat.mapper.UserMapper;
import com.mrc.chat.model.User;
import com.mrc.chat.repository.UserRepository;
import com.mrc.chat.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = UserMapper.toEntity(userDTO);
        user.setPassword(passwordEncoder.bCryptPasswordEncoder().encode(userDTO.getPassword()));
        return UserMapper.toDTO(userRepository.save(user));
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        existingUser.setUsername(userDTO.getUsername());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setPassword(passwordEncoder.bCryptPasswordEncoder().encode(userDTO.getPassword()));
        existingUser.setFirstName(userDTO.getFirstName());
        existingUser.setLastName(userDTO.getLastName());
        existingUser.setPhoneNumber(userDTO.getPhoneNumber());
        existingUser.setRole(userDTO.getRole());
        existingUser.setEnabled(userDTO.isEnabled());
        existingUser.setProfileImage(userDTO.getProfileImage());
        return UserMapper.toDTO(userRepository.save(existingUser));

    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        userRepository.delete(user);
    }

    @Override
    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(UserMapper::toDTO)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(UserMapper::toDTO)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(UserMapper::toDTO)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
    }
    @Override
    public void registerAccount(UserDTO userDTO, Role role) {
        boolean userExists = userRepository.findByEmail(userDTO.getEmail()).isPresent();
        if (userExists) {
            throw new UserException("A user already exists with the same email");
        }
        User user = UserMapper.toEntity(userDTO);
        user.setPassword(passwordEncoder.bCryptPasswordEncoder().encode(userDTO.getPassword()));
        user.setRole(role);
        user.setEnabled(false);
        userRepository.save(user);
    }

    @Override
    public List<User> getHrs() {
        return userRepository.findByRole(Role.HR);
    }


}
