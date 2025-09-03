package com.whatsbot.web.dto.auth;

public record LoginResponse(String token, UserSummaryDto user) {}