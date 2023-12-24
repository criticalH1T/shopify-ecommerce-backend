package com.ecommerce.backend.services;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CookieService {
    public String getJwtFromRequest(HttpServletRequest request) {
        final String authHeader = request.getHeader("Authorization");
        // if authHeader does not contain bearer token, return null to indicate that next filter needs to be called
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        else {
            return authHeader.substring(7);
        }
    }

    public String getJwtFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return null;
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("jwt")) {
                return cookie.getValue();
            }
        }
        return null;
    }

    public String getJwtToken(HttpServletRequest request, boolean fromCookie) {
        if (fromCookie) return getJwtFromCookie(request);
        return getJwtFromRequest(request);
    }
}
