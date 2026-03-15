package mikl.perso.springjwtauth.security;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import mikl.perso.springjwtauth.entity.User;

@Service
public class JwtService {

	private final Key key;
	private final long expirationTime;

	public JwtService(@Value("${jwt.secret}") String secretKey, @Value("${jwt.expiration}") long expiration) {
		this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
		this.expirationTime = expiration;
	}

	public String generateToken(User user) {
		return Jwts.builder().setSubject(user.getEmail()) //
				.setIssuedAt(new Date()) //
				.setExpiration(new Date(System.currentTimeMillis() + expirationTime)) //
				.signWith(key) //
				.compact();
	}

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
		return claimsResolver.apply(claims);
	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	private boolean isTokenExpired(String token) {
		return extractClaim(token, Claims::getExpiration).before(new Date());
	}

}