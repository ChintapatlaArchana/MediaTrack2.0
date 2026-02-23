package com.cts.exception;

public class CampaignNotFound extends RuntimeException {
    public CampaignNotFound(String message) {
        super(message);
    }
}
