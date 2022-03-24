package com.viktor.skype.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProfileManager {
    private final Environment environment;

    public List<String> getActiveProfiles(){
        return Arrays.asList(environment.getActiveProfiles());
    }

    public boolean isProfileActive(String profile){
        return getActiveProfiles().contains(profile);
    }
}
