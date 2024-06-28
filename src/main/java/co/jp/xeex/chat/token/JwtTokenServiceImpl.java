package co.jp.xeex.chat.token;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import co.jp.xeex.chat.exception.BusinessException;

import java.util.*;
import java.util.function.Function;

/**
 * JwtTokenServiceImpl
 * 
 * @author v_long, q_thinh
 */
@Service
public class JwtTokenServiceImpl implements JwtTokenService {
    private static final String TOKEN_ERROR_CLAIM_NULL = "TOKEN_ERROR_CLAIM_NULL";
    private static final String TOKEN_ERROR_CLAIM_INVALID = "TOKEN_ERROR_CLAIM_INVALID";
    private static final String TOKEN_ERROR_INVALID = "TOKEN_ERROR_INVALID";
    private static final String TOKEN_ERROR_EXPIRED = "TOKEN_ERROR_EXPIRED";

    /**
     * Token expiration time in seconds
     */
    @Value("${jwt.expiration.access}")
    private int JWT_EXPIRATION_TIME_ACCESS;

    /**
     * Token expiration time in seconds
     */
    @Value("${jwt.expiration.refesh}")
    private int JWT_EXPIRATION_TIME_REFRESH;

    @Value("${jwt.secret}")
    private String SECRET;

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = decodeToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Decode token
     * 
     * @param token token
     * @return Claims
     */
    private Claims decodeToken(String token) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException,
            SignatureException, IllegalArgumentException {
        try {
            String tk = token.startsWith("Bearer") ? token.replace("Bearer", "").trim() : token;
            return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(tk).getBody();
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException
                | IllegalArgumentException e) {
            throw e;
        }
    }

    @Override
    public String createToken(TokenClaimData claimDto) {
        if (null == claimDto) {
            throw new IllegalArgumentException(TOKEN_ERROR_CLAIM_NULL);
        }
        // Boolean isValid = claimDto.getUserId() != null //
        // && claimDto.getUserName() != null //
        // && claimDto.getLang() != null//
        // && claimDto.getRoleIdList() != null//
        // && !claimDto.getRoleIdList().isEmpty();

        Boolean isValid = claimDto.getUserName() != null
                && claimDto.getLang() != null;

        if (!isValid) {
            throw new IllegalArgumentException(TOKEN_ERROR_CLAIM_INVALID);
        }

        Map<String, Object> claims = new HashMap<>();
        // list role as id/code
        // String roles = "";
        // if (claimDto.getRoleIdList() != null && !claimDto.getRoleIdList().isEmpty())
        // {
        // roles = claimDto.getRoleIdList();
        // claims.put(TokenConsts.CLAIM_KEY_ROLE_IDs, roles);
        // }
        claims.put(TokenConsts.CLAIM_KEY_SUBJECT, claimDto.getUserName());
        // claims.put(TokenConsts.CLAIM_KEY_USER_ID, claimDto.getUserId());
        claims.put(TokenConsts.CLAIM_KEY_USER_NAME, claimDto.getUserName());
        claims.put(TokenConsts.CLAIM_KEY_EMAIL, claimDto.getEmail());
        claims.put(TokenConsts.CLAIM_KEY_FULL_NAME, claimDto.getFullName());
        claims.put(TokenConsts.CLAIM_KEY_LANG, claimDto.getLang());
        claims.put(TokenConsts.TOKEN_TYPE, claimDto.getTokenType());
        if (claimDto.getTokenType() == TokenType.ACCESS) {
            return Jwts.builder().setClaims(claims).setSubject(claimDto.getUserName())
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_TIME_ACCESS * 1000))
                    .signWith(SignatureAlgorithm.HS512, SECRET).compact();
        } else if (claimDto.getTokenType() == TokenType.REFRESH) {
            return Jwts.builder().setClaims(claims).setSubject(claimDto.getUserName())
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_TIME_REFRESH * 1000))
                    .signWith(SignatureAlgorithm.HS512, SECRET).compact();
        }
        return null;
    }

    @Override
    public boolean validateToken(String token) throws BusinessException {
        if (null == token || token.isEmpty()) {
            throw new BusinessException(TOKEN_ERROR_CLAIM_NULL);
        }

        String subject = getSubject(token);
        if (null == subject)
            throw new BusinessException(TOKEN_ERROR_INVALID);

        if (isTokenExpired(token)) {
            throw new BusinessException(TOKEN_ERROR_EXPIRED);
        }

        Claims claims = decodeToken(token);
        if (null == claims)
            throw new BusinessException(TOKEN_ERROR_CLAIM_INVALID);
        return true;
    }

    @Override
    public String getSubject(String token) {
        try {
            return getClaimFromToken(token, Claims::getSubject);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Long> getUserRoleIds(String token) {
        try {
            String roles = getClaimFromToken(token, claims -> (String) claims.get(TokenConsts.CLAIM_KEY_ROLE_IDs));
            //
            return Arrays.stream(roles.split(",")).map(s -> Long.parseLong(s)).toList();

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean isTokenExpired(String token) {
        try {
            return getClaimFromToken(token, Claims::getExpiration).before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    @Override
    public Date getExpirationDate(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    @Override
    public String getLang(String token) {
        try {
            return getClaimFromToken(token, claims -> (String) claims.get(TokenConsts.CLAIM_KEY_LANG));
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public TokenClaimData getClaimData(String token) {
        Claims claims = decodeToken(token);
        //
        return new TokenClaimData() {
            @Override
            public String getLang() {
                return (String) claims.get(TokenConsts.CLAIM_KEY_LANG);
            }

            // @Override
            // public String getRoleIdList() {
            // return (String) claims.get(TokenConsts.CLAIM_KEY_ROLE_IDs);
            // }

            @Override
            public String getUserName() {
                return (String) claims.get(TokenConsts.CLAIM_KEY_USER_NAME);
            }

            @Override
            public String getEmail() {
                return (String) claims.get(TokenConsts.CLAIM_KEY_EMAIL);
            }

            @Override
            public String getFullName() {
                return (String) claims.get(TokenConsts.CLAIM_KEY_FULL_NAME);
            }

            @Override
            public String getUserId() {
                // get user id from claim
                return (String) claims.get(TokenConsts.CLAIM_KEY_USER_ID);
            }

            @Override
            public TokenType getTokenType() {
                String type = claims.get(TokenConsts.TOKEN_TYPE).toString();
                return TokenType.valueOf(type);
            }
        };
    }

    @Override
    public String getUsernameFromToken(String token) {
        return this.getSubject(token);
    }

    @Override
    public UserDetails getUserDetailsFromToken(String token) {
        TokenClaimData claimData = getClaimData(token);
        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public String getPassword() {
                return null;
            }

            @Override
            public String getUsername() {
                return claimData.getUserName();
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };
    }
}
