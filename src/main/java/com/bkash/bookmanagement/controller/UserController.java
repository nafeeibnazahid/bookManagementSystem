package com.bkash.bookmanagement.controller;


import com.bkash.bookmanagement.common.Authority;
import com.bkash.bookmanagement.dto.auth.*;
import com.bkash.bookmanagement.entity.auth.RefreshToken;
import com.bkash.bookmanagement.entity.auth.UserInfo;
import com.bkash.bookmanagement.entity.auth.UserRole;
import com.bkash.bookmanagement.services.auth.*;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;


import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    @Autowired
    UserService userService;
    @Autowired
    RefreshTokenService refreshTokenService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRoleService userRoleService;



    @PreAuthorize(Authority.Plan.ADMIN)
    @PutMapping(value = "/update")
    public ResponseEntity updateUser(@Valid @RequestBody UserUpdateRequest userUpdateRequest) {
        UserInfo userInfo = userService.findFirstById(userUpdateRequest.getId());
        StringBuilder stringBuilder = new StringBuilder();
        if (userInfo == null) {
            stringBuilder.append("User not found with id " + userUpdateRequest.getId());
        }
        Set<UserRole> roles = new HashSet<UserRole>();
        for (Integer role : userUpdateRequest.getRoles()) {
            UserRole curUserRole = userRoleService.findById(Long.valueOf(role));
            if (curUserRole == null ) {
                stringBuilder.append("Role not found with id " + role);
            } else {
                roles.add(curUserRole);
            }
        }
        if (stringBuilder.length() > 0) {
            throw new RuntimeException(stringBuilder.toString());
        }
        UserInfo savedUserInfo = userService.updateRoles(userInfo, roles);
        return ResponseEntity.ok(savedUserInfo);
    }



    @PostMapping(value = "/save")
    public ResponseEntity saveUser(@RequestBody NewUserSaveRequest userRequest) {
        try {
            UserResponse userResponse = userService.saveUser(userRequest);
            return ResponseEntity.ok(userResponse);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    @GetMapping("/users")
    @GetMapping()
    public ResponseEntity getAllUsers() {
        try {
            List<UserResponse> userResponses = userService.getAllUser();
            return ResponseEntity.ok(userResponses);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @PostMapping("/profile")
    public ResponseEntity<UserResponse> getUserProfile() {
        try {
            UserResponse userResponse = userService.getUser();
            return ResponseEntity.ok().body(userResponse);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/test")
    public String test() {
        try {
            return "Welcome";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/login")
    public JwtResponseDTO AuthenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
        if (authentication.isAuthenticated()) {
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequestDTO.getUsername());
            String accessToken = jwtService.GenerateToken(authRequestDTO.getUsername());
            logger.info("accessToken = " + accessToken);
            return JwtResponseDTO.builder()
                    .accessToken(accessToken)
                    .token(refreshToken.getToken()).build();

        } else {
            throw new UsernameNotFoundException("invalid user request..!!");
        }

    }


    @PostMapping("/refreshToken")
    public JwtResponseDTO refreshToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO) {
        return refreshTokenService.findByToken(refreshTokenRequestDTO.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUserInfo)
                .map(userInfo -> {
                    String accessToken = jwtService.GenerateToken(userInfo.getUsername());
                    return JwtResponseDTO.builder()
                            .accessToken(accessToken)
                            .token(refreshTokenRequestDTO.getToken()).build();
                }).orElseThrow(() -> new RuntimeException("Refresh Token is not in DB..!!"));
    }

}
