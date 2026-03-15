package mikl.perso.springjwtauth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import mikl.perso.springjwtauth.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	/**
	 * Find a user by email.
	 * 
	 * @param email the email to search for
	 * @return the user
	 */
	Optional<User> findByEmail(String email);

}