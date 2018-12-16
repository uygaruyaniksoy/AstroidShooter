package com.group6.termproject.controllers;

import com.group6.termproject.dto.*;
import com.group6.termproject.repositories.AuthenticationRepository;
import com.group6.termproject.repositories.PlayerRepository;
import com.group6.termproject.tables.Authentication;
import com.group6.termproject.tables.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Optional;

@RestController
public class PlayerRestController {
	private final PlayerRepository playerRepository;
	private final AuthenticationRepository authenticationRepository;

	// SHA-256 hasher object
	private MessageDigest digest;

	@Autowired
	public PlayerRestController(PlayerRepository playerRepository, AuthenticationRepository authenticationRepository) {
		try {
			digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		this.playerRepository = playerRepository;
		this.authenticationRepository = authenticationRepository;
	}

	/**
	 * Takes a dto with user's nickname, email and password. Creates a new Player object in the database if given email is not being used.
	 *
	 * Hashes the password and stores the result. Doesn't store the password as it is.
	 *
	 * @param playerDTO user's nickname, email and password
	 * @return ResponseEntity with http request status (401 if given email is used, 200 if the player created succesfully.)
	 */
	@PostMapping(path = "/player/sign_up", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity signUp(@RequestBody PlayerSignUpDTO playerDTO) {
		byte[] hash = digest.digest(playerDTO.getPassword().getBytes(StandardCharsets.UTF_8));
		String passwordHash = Base64.getEncoder().encodeToString(hash);

		Optional<Player> optionalPlayer = playerRepository.findByEmail(playerDTO.getEmail());
		Optional<Player> optionalPlayer2  = playerRepository.findByNickName(playerDTO.getNickName());

		if (optionalPlayer.isPresent() || optionalPlayer2.isPresent()) return new ResponseEntity(HttpStatus.NO_CONTENT);


		Player player = new Player(playerDTO.getNickName(), playerDTO.getEmail(), passwordHash);
		playerRepository.save(player);

		return new ResponseEntity(HttpStatus.OK);
	}

	/**
	 * Takes a dto with user's email and password, Queries for the user email and hashed password.
	 *
	 * @param playerDTO user's email and password
	 * @return Player's info with 200 code if the user is found, 401 if not found. Also returns a validation token which will be used by player's further queries.
	 */
	@PostMapping(path = "/player/sign_in", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PlayerAuthenticationDTO> signIn(@RequestBody PlayerSignInDTO playerDTO) {
		String password = playerDTO.getPassword();
		String email = playerDTO.getEmail();

		byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
		String passwordHash = Base64.getEncoder().encodeToString(hash);

		Player player = playerRepository.findByEmailAndPassword(email, passwordHash).orElse(null);

		if (player != null) {
			String playerLoginInfo = email + "||" + password;
			hash = digest.digest(playerLoginInfo.getBytes(StandardCharsets.UTF_8));
			String playerLoginInfoHash = Base64.getEncoder().encodeToString(hash);

			PlayerAuthenticationDTO playerAuthenticationDTO = new PlayerAuthenticationDTO(playerLoginInfoHash);
			authenticationRepository.save(new Authentication(playerLoginInfoHash, player));
			return new ResponseEntity<>(playerAuthenticationDTO, HttpStatus.OK);
		}

		return new ResponseEntity<>( (PlayerAuthenticationDTO) null, HttpStatus.NO_CONTENT);
	}

	/**
	 * Takes a dto with user's new password and current token. Queries for the given token to identify the player. If the given token is validated for a user, updates the user's
	 * password with queried password by hashing it. Stores the new hashed password.
	 *
	 * @param playerDTO user's new password and current token
	 * @return status code 401 if user token is not valid, status code 200 if password is changed successfully.
	 */

	@PutMapping(path = "/player/change_password", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity changePassword(@RequestBody PlayerChangePasswordDTO playerDTO) {
		Player player = playerRepository.findByToken(playerDTO.getToken()).orElse(null);

		byte[] hash = digest.digest(playerDTO.getPassword().getBytes(StandardCharsets.UTF_8));
		String passwordHash = Base64.getEncoder().encodeToString(hash);

		if (player != null) {
			player.setPassword(passwordHash);
			playerRepository.save(player);
			return new ResponseEntity(HttpStatus.OK);
		}
		return new ResponseEntity(HttpStatus.UNAUTHORIZED);
	}

	/**
	 * Takes the authentication token of the player as argument and queries for it. If successfull; deletes the token, invalidating the token and essentially signing the player out.
	 * If the token is not found nothing happens.
	 *
	 * @param token user's authenticaiton token
	 * @return http status code 200 if successfully logged out, code 401 if log out is failed.
	 */

	@DeleteMapping(path = "/player/sign_out", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> sign_out(@RequestHeader("x-access-token") String token) {
		Optional<Authentication> auth = authenticationRepository.getByToken(token);
		if(auth != null && auth.isPresent()) {
			authenticationRepository.delete(auth.get());
			return new ResponseEntity<>("Signed out",HttpStatus.OK);
		}
		else{
			return new ResponseEntity<>("First sign in", HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Takes the players authentication token by argument and queries with it to find the player id and essentially the player object.
	 * If the player cannot be found it fails.
	 *
	 * @param token user's authentication token
	 * @return the player with the given authentication token and http code 200 if found, else code 401.
	 */
	@GetMapping(path = "/player/me/{token}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Player> me(@PathVariable String token) {
		Player player = playerRepository.findByToken(token).orElse(null);
		HttpStatus status = player != null ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;

		return new ResponseEntity<>(player, status);
	}
}
