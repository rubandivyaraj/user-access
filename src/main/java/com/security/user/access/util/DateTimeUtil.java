package com.security.user.access.util;

import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class DateTimeUtil {

    public long getEpochMilli(Long expiration) {
        Instant now = Instant.now();
        return now.plusSeconds(expiration).toEpochMilli();
    }

}
