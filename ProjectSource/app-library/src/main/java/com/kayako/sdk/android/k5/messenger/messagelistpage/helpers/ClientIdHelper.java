package com.kayako.sdk.android.k5.messenger.messagelistpage.helpers;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Helper class to generate a client id
 */
public class ClientIdHelper {

    private final String PREFIX = "android-%s-%s";
    private final String UUID_FOR_THIS_SESSION = UUID.randomUUID().toString();
    private AtomicInteger counter = new AtomicInteger(0);

    public ClientIdHelper() {
        counter = new AtomicInteger(0);
    }

    /**
     * @return a unique client id to use for messages
     */
    public String generateClientId() {
        return String.format(PREFIX, UUID_FOR_THIS_SESSION, counter.incrementAndGet());
    }
}
