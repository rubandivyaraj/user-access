package com.security.user.access.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/*
 * Not storing any token in DB. instead maintaining in cache and delete at every hour based on the expiration time
 *
 * This will help only on small scale application. Better use redis for large volume
 * */
@Slf4j
@Component
@EnableScheduling
public class BlacklistTokensUtil {

    private static final Map<String, Long> blacklistToken = new ConcurrentHashMap<>();
    private final DateTimeUtil dateTimeUtil;

    public BlacklistTokensUtil() {
        this.dateTimeUtil = new DateTimeUtil();
    }

    public void addToken(Long key, String token) {
        log.info("Key : {}, token : {}", key, token);
        blacklistToken.put(token, key);
    }

    public boolean isTokenBlocked(String token) {
        return !blacklistToken.isEmpty() && blacklistToken.containsKey(token);
    }

    /*
     * this cron expression runs on every 1 hour 0 min 0 sec
     * */
    @Scheduled(cron = "0 * * * * ?")
    private void deleteToken() {
        blacklistToken.values().removeIf(key -> key < dateTimeUtil.getEpochMilli(0l));
    }
}
