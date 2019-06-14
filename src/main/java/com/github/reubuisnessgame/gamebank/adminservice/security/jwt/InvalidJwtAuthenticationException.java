package com.github.reubuisnessgame.gamebank.adminservice.security.jwt;

import org.springframework.security.core.AuthenticationException;

class InvalidJwtAuthenticationException extends AuthenticationException {
    InvalidJwtAuthenticationException(String e) {
        super(e);
    }
}
