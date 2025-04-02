package com.security.user.access.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author ruban
 * @author {@link <a href="https://www.linkedin/in/rubandivyaraj">...</a>}
 * @apiNote To generate sequence unique id.
 * @implNote Not an exact twitter snow flake algorithm but it helps to generate
 * unique sequence id.
 */
@Component
@Slf4j
public class SequenceIdGenerator {
    private static String hostname;
    private static long sequenceId = 1000L;
    private static long lastTimestamp = -1L;

    public static synchronized String generateSeqId() {
        long currentTimestamp = getTimestamp();
        if (currentTimestamp != lastTimestamp) {
            lastTimestamp = currentTimestamp;
            sequenceId = 1000;
        }
        return String.valueOf(currentTimestamp) + getMachineId() + getSequenceId();
    }

    private static String getMachineId() {
        if (hostname == null) {
            try {
                // If system required numeric
                // hostname = Math.abs(InetAddress.getLocalHost().getHostName().hashCode() % 1024);
                hostname = InetAddress.getLocalHost().getHostName().toUpperCase();
            } catch (UnknownHostException e) {
                log.error("UnknownHostException : {0}", e);
                hostname = "01A";
            }
        }
        return hostname;
    }

    private static long getSequenceId() {
        if (sequenceId < 10000) {
            try {
                Thread.sleep(1);
                sequenceId = 1000;
            } catch (InterruptedException e) {
                log.error("InterruptedException : {0}", e);
            }
        }
        return sequenceId++;
    }

    private static long getTimestamp() {
        return System.currentTimeMillis();
    }

}