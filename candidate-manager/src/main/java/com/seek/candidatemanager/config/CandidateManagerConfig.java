package com.seek.candidatemanager.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@Configuration
public class CandidateManagerConfig {

    final
    Environment env;

    public CandidateManagerConfig(Environment env) {
        this.env = env;
    }

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT-5"));
    }

}
