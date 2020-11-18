package dev.sassine.oauth2.scribe.provider;

import static java.lang.String.format;
import static java.time.Duration.ZERO;
import static java.util.Collections.emptySet;
import static java.util.Optional.ofNullable;
import static org.springframework.web.util.WebUtils.getCookie;

import java.time.Duration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserRedisProvider {
	
	private final RedisTemplate<String, Object> redisTemplate;
    private final HttpServletRequest httpServletRequest;
    private final HttpServletResponse httpServletResponse;
    
    private static final String ASTERISK = "*";

    public void put(String key, Object value, Duration timeout) {
        redisTemplate.opsForValue().set(buildKey(key), value, timeout);
    }

    public Object get(String key) {
        return redisTemplate.opsForValue().get(buildKey(key));
    }

    public void invalidate() {
        HttpSession session = httpServletRequest.getSession();
        ofNullable(redisTemplate.keys(session.getId().concat(ASTERISK)))
        .orElse(emptySet())
        .forEach(key ->  redisTemplate.expire(key, ZERO));
        
        Cookie sessionCookie = getCookie(httpServletRequest, "JSESSIONID");
        sessionCookie.setMaxAge(0);
        httpServletResponse.addCookie(sessionCookie);
        
        session.invalidate();
    }

    private String buildKey(String key) {
        return format("%s-%s", httpServletRequest.getSession().getId(), key);
    }
}
