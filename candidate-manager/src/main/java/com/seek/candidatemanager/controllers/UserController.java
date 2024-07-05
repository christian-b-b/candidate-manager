package com.seek.candidatemanager.controllers;


import com.seek.candidatemanager.constants.CandidateManagerConstants;
import com.seek.candidatemanager.services.LoginUserService;
import com.seek.candidatemanager.tdos.request.LoginUserRequestDTO;
import com.seek.candidatemanager.tdos.response.LoginUserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(CandidateManagerConstants.API_VERSION + CandidateManagerConstants.RESOURCE_USERS)
public class UserController {

    @Autowired
    private LoginUserService loginUserService;

    @PostMapping(CandidateManagerConstants.RESOURCE_LOGIN)
    ResponseEntity<LoginUserResponseDTO> login(@RequestBody LoginUserRequestDTO loginUserRequestDTO){

        return new ResponseEntity<LoginUserResponseDTO>(loginUserService.login(loginUserRequestDTO),null, HttpStatus.OK);
    }

}
