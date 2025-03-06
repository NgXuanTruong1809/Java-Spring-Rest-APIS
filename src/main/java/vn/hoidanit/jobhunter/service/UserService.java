package vn.hoidanit.jobhunter.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.validation.ConstraintValidatorContext;
import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.domain.dto.Pagination.Meta;
import vn.hoidanit.jobhunter.domain.dto.Pagination.ResultPaginationDTO;
import vn.hoidanit.jobhunter.domain.dto.UserDTO.UserDTO;
import vn.hoidanit.jobhunter.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User handleCreateUser(User user) {
        return this.userRepository.save(user);
    }

    public void handleDeleteUser(Long id) {
        this.userRepository.deleteById(id);
    }

    public User fetchUserById(Long id) {
        Optional<User> user = this.userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        }
        return null;
    }

    public ResultPaginationDTO fetchAllUser(Specification<User> specification, Pageable pageable) {
        Page<User> pageUser = this.userRepository.findAll(specification, pageable);
        ResultPaginationDTO rsDTO = new ResultPaginationDTO();
        Meta meta = new Meta();

        meta.setCurrent(pageable.getPageNumber() + 1);
        meta.setPageSize(pageable.getPageSize());

        meta.setPages(pageUser.getTotalPages());
        meta.setTotal(pageUser.getTotalElements());

        rsDTO.setMeta(meta);

        List<UserDTO> userList = new ArrayList<>();
        for (User user : pageUser.getContent()) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setAddress(user.getAddress());
            userDTO.setAge(user.getAge());
            userDTO.setUpdatedAt(user.getUpdatedAt());
            userDTO.setUpdatedBy(user.getUpdatedBy());
            userDTO.setCreatedAt(user.getCreatedAt());
            userDTO.setCreatedBy(user.getCreatedBy());
            userDTO.setEmail(user.getEmail());
            userDTO.setGender(user.getGender());
            userDTO.setName(user.getName());
            userList.add(userDTO);
        }
        rsDTO.setResult(userList);

        // remove sensitive data
        // List<ResUserDTO> listUser = pageUser.getContent()
        // .stream().map(item -> new ResUserDTO(
        // item.getId(),
        // item.getEmail(),
        // item.getName(),
        // item.getGender(),
        // item.getAddress(),
        // item.getAge(),
        // item.getUpdatedAt(),
        // item.getCreatedAt()))
        // .collect(Collectors.toList());

        // rs.setResult(listUser);

        return rsDTO;
    }

    public User handleUpdateUser(User requestUser) {
        User currentUser = this.fetchUserById(requestUser.getId());
        if (currentUser != null) {
            currentUser.setGender(requestUser.getGender());
            currentUser.setName(requestUser.getName());
            currentUser.setAge(requestUser.getAge());
            currentUser.setAddress(requestUser.getAddress());
            currentUser = this.userRepository.save(currentUser);
        }
        return currentUser;
    }

    public User fetchUserByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public boolean isValid(String email) {
        return this.userRepository.existsByEmail(email); // Kiểm tra email có tồn tại không
    }

    public void handleSaveToken(String token, String email) {
        User currentUser = this.userRepository.findByEmail(email);
        if (currentUser != null) {
            currentUser.setRefreshToken(token);
            this.userRepository.save(currentUser);
        }
    }

    public User fetchUserByRefreshTokenAndEmail(String email, String token) {
        return this.userRepository.findByEmailAndRefreshToken(email, token);
    }

}
