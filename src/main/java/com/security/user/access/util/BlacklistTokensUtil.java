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

    private static final Map<Long, String> blacklistToken = new ConcurrentHashMap<>();
    private final DateTimeUtil dateTimeUtil;

    public BlacklistTokensUtil(DateTimeUtil dateTimeUtil) {
        this.dateTimeUtil = dateTimeUtil;
    }

    public void addToken(Long key, String token) {
        log.info("Key : {}, token : {}", key, token);
        blacklistToken.put(key, token);
    }

    public boolean isTokenBlocked(String token) {
        log.info("given token : {}", token);
        return blacklistToken.containsValue(token);
    }

    /*
     * this cron expression runs on every 1 hour 0 min 0 sec
     * */
    @Scheduled(cron = "0 * * * * ?")
    private void deleteToken() {
        blacklistToken.keySet().removeIf(key -> key < dateTimeUtil.getEpochMilli(0l));
    }
}
