package com.ea.libmanagement.infrastructure.configuration;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class SettingsHash {
    public String secret = "d41d8cd98f00b204e9800998ecf8427e";
}
