package com.mrc.chat.service;


import com.mrc.chat.dto.UserDTO;
import com.mrc.chat.enumirators.Role;
import com.mrc.chat.model.User;

import java.util.List;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);
    UserDTO updateUser(Long id, UserDTO userDTO);
    void deleteUser(Long id);
    UserDTO getUserById(Long id);
    List<UserDTO> getAllUsers();
    UserDTO getUserByUsername(String username);
    UserDTO getUserByEmail(String email);

    void registerAccount(UserDTO userDTO, Role role);

  List<User> getHrs();

}
