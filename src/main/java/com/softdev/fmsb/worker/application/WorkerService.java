package com.softdev.fmsb.worker.application;

import com.softdev.fmsb.auth.application.JwtService;
import com.softdev.fmsb.auth.application.TwoFactorAuthenticationService;
import com.softdev.fmsb.auth.infraestructure.TokenRepository;
import com.softdev.fmsb.auth.infraestructure.UserRepository;
import com.softdev.fmsb.worker.infraestructure.UserEssentialsProjection;
import com.softdev.fmsb.worker.infraestructure.dto.*;
import com.softdev.fmsb.auth.model.Token;
import com.softdev.fmsb.auth.model.TokenType;
import com.softdev.fmsb.auth.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkerService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final TwoFactorAuthenticationService tfaService;
    private final TokenRepository tokenRepository;

    private final String ERROR = "error";
    private final boolean SUCCESFUL = true;
    private final boolean UNSUCCESFUL = false;

    public List<UserEssentials> getAllUsers(){
        List <User> users = userRepository.findAll();
        List<UserEssentials> userEssentialsList = users.stream()
                .map(user -> new UserEssentials(user.getFirstName(), user.getLastName(), user.getRfc()))
                .collect(Collectors.toList());

        return userEssentialsList;
    }

    public GetUserResponse getUser(String rfc) {
        Optional<User> optionalUser = userRepository.findUserByRfc(rfc);
        GetUserResponse response;

        if(optionalUser.isPresent()){
            User user = optionalUser.get();

            response = GetUserResponse.builder()
                    .firstname(user.getFirstName())
                    .lastname(user.getLastName())
                    .userNumber(user.getUserNumber())
                    .email(user.getEmail())
                    .rfc(user.getRfc())
                    .role(user.getRole())
                    .build();
        } else {
            response = GetUserResponse.builder()
                    .rfc(ERROR)
                    .build();
        }

        return response;
    }


    public boolean deleteUser(String rfc){
        Optional<User> userToDelete = userRepository.findUserByRfc(rfc);
        boolean isDeleted;

        if (userToDelete.isPresent()){
            userRepository.delete(userToDelete.get());
            isDeleted = SUCCESFUL;
        } else{
            isDeleted = UNSUCCESFUL;
        }

        return isDeleted;
    }

    public boolean updateUser(ModifyRequest updatedUser) {
        Optional<User> optionalUser = userRepository.findUserByRfc(updatedUser.getRfc());
        boolean isModified;

        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            existingUser.setFirstName(updatedUser.getFirstname());
            existingUser.setLastName(updatedUser.getLastname());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setRole(updatedUser.getRole());

            userRepository.save(existingUser);
            isModified = true;
        } else {
            isModified = false;
        }

        return isModified;
    }


    public RegisterResponse register(RegisterRequest request) {

        User user = User.builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .email(request.getEmail())
                .rfc(request.getRfc())
                .userNumber(request.getUserNumber())
                .password(passwordEncoder.encode(setPassword(request.getFirstname(), request.getRfc())))
                .role(request.getRole())
                .mfaEnabled(request.isMfaEnabled())
                .build();

        //if MFA enabled -> Generate Secret
        if(request.isMfaEnabled()) {
            user.setSecret(tfaService.generateNewSecret());
        }

        var savedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        saveUserToken(savedUser, jwtToken);

        return RegisterResponse.builder()
                .secretImageUri(tfaService.generateQrCodeImageUri(user.getSecret()))
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .mfaEnabled(user.isMfaEnabled())
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();

        tokenRepository.save(token);
    }

    private String setPassword(String name, String rfc){
        String password;

        if(name == null || rfc == null || name.isEmpty() || rfc.isEmpty()) {
            password = ERROR;
        }
        else{
            String namePrefix = name.substring(0, Math.min(name.length(), 3));
            String rfcPrefix = rfc.substring(0, Math.min(rfc.length(), 3));

            password = namePrefix + rfcPrefix;
        }

        return password;
    }

    public boolean verifyUserNumber(String userNumber){
        Optional<User> optionalUser = userRepository.findUserByUserNumber(userNumber);

        return optionalUser.isPresent();
    }

    public boolean verifyRfc(String rfc) {
        Optional<User> optionalUser = userRepository.findUserByRfc(rfc);

        return optionalUser.isPresent();
    }

}
