package mikl.perso.springjwtauth.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import mikl.perso.springjwtauth.dto.AuthRequest;
import mikl.perso.springjwtauth.dto.AuthResponse;
import mikl.perso.springjwtauth.entity.User;
import mikl.perso.springjwtauth.security.JwtService;
import mikl.perso.springjwtauth.service.UserService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final UserService userService;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;

	@PostMapping("/register")
	public User register(@RequestBody AuthRequest request) {

		return userService.register(request.getEmail(), request.getPassword());
	}

	@PostMapping("/login")
	public AuthResponse login(@RequestBody AuthRequest request) {

		User user = userService.findByEmail(request.getEmail())
				.orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

		if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			throw new RuntimeException("Mot de passe incorrect");
		}

		String token = jwtService.generateToken(user);

		return new AuthResponse(token);
	}
}