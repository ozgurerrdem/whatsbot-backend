package com.whatsbot.security;

import java.io.Serializable;

public record PrincipalUser(String username, String tenantKey) implements Serializable {}
