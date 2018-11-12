package com.group26.termproject.controllers;

import com.group26.termproject.dto.*;
import com.group26.termproject.repositories.AuthenticationRepository;
import com.group26.termproject.repositories.PlayerRepository;
import com.group26.termproject.tables.Authentication;
import com.group26.termproject.tables.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Optional;

@RestController
class PlayerRestController {
	@Autowired
	PlayerRepository playerRepository;
	@Autowired
	AuthenticationRepository authenticationRepository;

	MessageDigest digest;

	public PlayerRestController() {
		try {
			digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return;
		}
	}

	@GetMapping("/players/{id}")
	Optional<Player> getPlayer(@PathVariable Integer id) {
		return playerRepository.findById(id);
		//or else throw
	}

	Optional<Authentication> getToken(@PathVariable Integer id) {
		return authenticationRepository.findById(id);
	}

	@GetMapping("/test")
    Collection<LocalDate> testQuery() {
		LocalDate now = LocalDate.now();
		ArrayList<LocalDate> objects = new ArrayList<>();
		objects.add(now);
		return objects;
	}

	@PostMapping(path = "/player/sign_up", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void signUp(@RequestBody PlayerSignUpDTO playerDTO) {
		byte[] hash = digest.digest(playerDTO.getPassword().getBytes(StandardCharsets.UTF_8));
		String passwordHash = Base64.getEncoder().encodeToString(hash);

		Player player = new Player(playerDTO.getNickName(), playerDTO.getEmail(), passwordHash);
		playerRepository.save(player);
	}


	@PostMapping(path = "/player/sign_in", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public PlayerAuthenticationDTO signIn(@RequestBody PlayerSignInDTO playerDTO) {
		String password = playerDTO.getPassword();
		String email = playerDTO.getEmail();
		// TODO: in the future receive hashed password, don't hash it here
		byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
		String passwordHash = Base64.getEncoder().encodeToString(hash);

		Player player = playerRepository.findByEmailAndPassword(email, passwordHash).orElse(null);

		if (player != null) {
			String playerLoginInfo = email + "||" + password;
			hash = digest.digest(playerLoginInfo.getBytes(StandardCharsets.UTF_8));
			String playerLoginInfoHash = Base64.getEncoder().encodeToString(hash);

			PlayerAuthenticationDTO playerAuthenticationDTO = new PlayerAuthenticationDTO(playerLoginInfoHash);
			authenticationRepository.save(new Authentication(playerLoginInfoHash, player.getId()));
			return playerAuthenticationDTO;
		}
		return null;

	}

	@PutMapping(path = "/player/change_password", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void changePassword(@RequestBody PlayerChangePasswordDTO playerDTO) {
		Player player = playerRepository.findByToken(playerDTO.getToken()).orElse(null);

		byte[] hash = digest.digest(playerDTO.getPassword().getBytes(StandardCharsets.UTF_8));
		String passwordHash = Base64.getEncoder().encodeToString(hash);

		if (player != null) {
			player.setPassword(passwordHash);
			playerRepository.save(player);
			return;
		}
	}
}
