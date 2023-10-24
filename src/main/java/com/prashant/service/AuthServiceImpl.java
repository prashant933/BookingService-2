package com.prashant.service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.prashant.dao.IAuthDao;
import com.prashant.exception.BadRequestException;
import com.prashant.model.User;
import com.prashant.request.AuthServiceRequestObject;
import com.prashant.response.AuthServiceResponse;
import com.prashant.response.ResponseDetails;

import javax.crypto.SecretKey;

@Service
public class AuthServiceImpl implements IAuthService {
	
	@Autowired
	private IAuthDao dao;
	
	@Autowired
	private PasswordEncoder encoder;
	@Value("${spring.jwt_key}")
	private String JWT_KEY;

	private final long EXPIRATION_TIME = 300000; // 5 mins

	@Override
	public ResponseDetails signup(AuthServiceRequestObject request) {
		String email = request.getEmail();
		User user = dao.findByEmail(email);
		if(user != null) {
			throw new BadRequestException("User with given email: "+email 
					+ " already exists");
		}
		user = new User();
		user.setEmail(email);
		String hashedPassword = encoder.encode(request.getPassword());
		user.setPassword(hashedPassword);
		user.setCreatedAt(LocalDateTime.now());
		user.setUpdatedAt(LocalDateTime.now());
		dao.save(user);
		
		ResponseDetails response = new ResponseDetails();
		response.setMessage("Successfully saved the user");
		response.setSuccess(true);
		return response;
	}

	@Override
	public AuthServiceResponse signin(AuthServiceRequestObject request) {
		User user = dao.findByEmail(request.getEmail());
		if(user == null) {
			throw new UsernameNotFoundException("User with given email: "+request.getEmail()
					+ " does not exists");
		}
		if(encoder.matches(request.getPassword(), user.getPassword())) {
			String jwtToken = generateJwtToken(user.getId());
			AuthServiceResponse response = new AuthServiceResponse();
			response.setToken(jwtToken);
			response.setExpiresIn(EXPIRATION_TIME);
			return response;
		}
		else {
			throw new UsernameNotFoundException("Please enter correct password");
		}
	}

	private String generateJwtToken(Integer id)
	{
		SecretKey key = Keys.hmacShaKeyFor(JWT_KEY.getBytes(StandardCharsets.UTF_8));
		String jwt = Jwts.builder()
				.claim("id", id)
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + EXPIRATION_TIME))
				.signWith(key)
				.compact();
		return jwt;
	}

	@Override
	public Jws<Claims> validateToken(String token) {
		try {
			return Jwts.parserBuilder()
					.setSigningKey(JWT_KEY.getBytes(StandardCharsets.UTF_8))
					.build()
					.parseClaimsJws(token);
		}
		catch (JwtException e) {
			throw new BadRequestException("Token provided is not valid");
		}
	}

}
