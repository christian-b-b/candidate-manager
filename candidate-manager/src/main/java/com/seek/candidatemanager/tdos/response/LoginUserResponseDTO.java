package com.seek.candidatemanager.tdos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginUserResponseDTO {
    @JsonProperty(value = "token_type")
    private String tokenType;
    @JsonProperty(value = "access_token")
    private String accessToken;
    @JsonProperty(value = "expires_in")
    private Long expiresIn;
    @JsonProperty(value = "issued_at")
    private String issuedAt;

}
