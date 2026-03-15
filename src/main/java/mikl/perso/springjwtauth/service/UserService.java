package mikl.perso.springjwtauth.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import mikl.perso.springjwtauth.entity.User;
import mikl.perso.springjwtauth.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public User register(String email, String password) {
		User user = User.builder().email(email).password(passwordEncoder.encode(password)).build();

		return userRepository.save(user);
	}

	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

		return org.springframework.security.core.userdetails.User //
				.withUsername(user.getEmail()) //
				.password(user.getPassword()) //
				.authorities("USER") //
				.build();
	}

}