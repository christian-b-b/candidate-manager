package com.seek.candidatemanager.services;




import com.seek.candidatemanager.constants.CandidateManagerConstants;
import com.seek.candidatemanager.domains.User;
import com.seek.candidatemanager.domains.UserPassword;
import com.seek.candidatemanager.domains.UserRole;
import com.seek.candidatemanager.errorhandler.CandidateManagerGenericClientException;
import com.seek.candidatemanager.repositories.UserPasswordRepository;
import com.seek.candidatemanager.repositories.UserRepository;
import com.seek.candidatemanager.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CandidateManagerDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserPasswordRepository userPasswordRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String documentNumber) throws UsernameNotFoundException {
        User user = userRepository.findByNameAndState(documentNumber,
                CandidateManagerConstants.ACTIVE_STATE).orElseThrow(
                ()-> new CandidateManagerGenericClientException("User not found",
                        HttpStatus.NOT_FOUND, null,null));

        UserPassword userPassword = userPasswordRepository.findUserPasswordByUserAndState(user,
                CandidateManagerConstants.ACTIVE_STATE).orElseThrow(
                ()-> new CandidateManagerGenericClientException("UserPasswor not found",
                        HttpStatus.NOT_FOUND, null,null));

        List<UserRole> userRoles = userRoleRepository.findUserRoleByUser(user);

        UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(documentNumber)
                .password(userPassword.getPassword())
                .authorities(mapAuthorities(userRoles))
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();

        return userDetails;
    }

    private List<GrantedAuthority> mapAuthorities(List<UserRole> userRoles) {
        return userRoles.stream().map(userRole -> {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(
                    userRole.getRole().getCode());
            return grantedAuthority;
        }).collect(Collectors.toList());
    }

}
