package com.axmor.services;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@RequiredArgsConstructor
@Value
public class ServiceMessages {
    private StatusMessage statusMessage;
    private String message;
}
