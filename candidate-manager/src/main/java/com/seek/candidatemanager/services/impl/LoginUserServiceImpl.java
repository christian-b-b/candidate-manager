package com.seek.candidatemanager.services.impl;


import com.seek.candidatemanager.constants.CandidateManagerConstants;
import com.seek.candidatemanager.domains.Role;
import com.seek.candidatemanager.domains.User;
import com.seek.candidatemanager.domains.UserPassword;
import com.seek.candidatemanager.domains.UserRole;
import com.seek.candidatemanager.errorhandler.CandidateManagerGenericClientException;
import com.seek.candidatemanager.repositories.UserPasswordRepository;
import com.seek.candidatemanager.repositories.UserRepository;
import com.seek.candidatemanager.repositories.UserRoleRepository;
import com.seek.candidatemanager.security.JwtTokenProvider;
import com.seek.candidatemanager.services.LoginUserService;
import com.seek.candidatemanager.tdos.request.LoginUserRequestDTO;
import com.seek.candidatemanager.tdos.response.LoginUserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoginUserServiceImpl implements LoginUserService {

    @Value("${jwt.token.time}")
    private Long tokenTime;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserPasswordRepository userPasswordRepository;
    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Override
    public LoginUserResponseDTO login(LoginUserRequestDTO loginUserRequestDTO) {
        User user = userRepository.findByNameAndState(loginUserRequestDTO.getName(),
                CandidateManagerConstants.ACTIVE_STATE).orElseThrow( ()-> new CandidateManagerGenericClientException(
                "User not found", HttpStatus.NOT_FOUND, null, null));


        UserPassword userPassword = userPasswordRepository.findUserPasswordByUserAndState(user,
                CandidateManagerConstants.ACTIVE_STATE).orElseThrow(()-> new CandidateManagerGenericClientException(
                "User password not found", HttpStatus.NOT_FOUND, null, null));

        if(!isMatchPassword(userPassword,loginUserRequestDTO)){
            throw new CandidateManagerGenericClientException("Invalid Credentials", HttpStatus.UNAUTHORIZED, null, null);
        }


        List<Role> roles = mapRoles(userRoleRepository.findUserRoleByUser(user));
        String token = jwtTokenProvider.createToken(user.getName(),roles);

        LocalDateTime dateTime = LocalDateTime.now();
        LoginUserResponseDTO loginUserResponseDTO = LoginUserResponseDTO.builder()
                .tokenType(CandidateManagerConstants.TOKEN_TYPE).accessToken(token)
                .expiresIn(Instant.now().toEpochMilli() + tokenTime)
                .issuedAt(dateTime.toString()).build();

        return loginUserResponseDTO;
    }

    private boolean isMatchPassword(UserPassword userPassword,LoginUserRequestDTO loginUserRequestDTO){
        return userPassword.getPassword().equals(loginUserRequestDTO.getPassword());
    }

    private List<Role> mapRoles(List<UserRole> userRoles){
        return userRoles.stream().map(userRole -> userRole.getRole()).collect(Collectors.toList());
    }
}
