package com.seek.candidatemanager.services;


import com.seek.candidatemanager.tdos.request.LoginUserRequestDTO;
import com.seek.candidatemanager.tdos.response.LoginUserResponseDTO;

public interface LoginUserService {
    LoginUserResponseDTO login(LoginUserRequestDTO loginUserRequestDTO);
}
