package com.outfit7.fun7gamebackend.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ServiceStatus {
    private String multiplayer;
    private String userSupport;
    private String ads;
}
