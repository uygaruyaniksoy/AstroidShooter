package com.group26.termproject.controllers;

import com.group26.termproject.dto.PlayerAuthenticationDTO;
import com.group26.termproject.dto.PlayerSignInDTO;
import com.group26.termproject.dto.PlayerSignUpDTO;
import com.group26.termproject.repositories.AuthenticationRepository;
import com.group26.termproject.repositories.PlayerRepository;
import com.group26.termproject.tables.Authentication;
import com.group26.termproject.tables.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;

@RestController
class PlayerRestController {
	@Autowired
	PlayerRepository playerRepository;
	@Autowired
	AuthenticationRepository authenticationRepository;

	@GetMapping("/test")
    Collection<LocalDate> testQuery() {
		LocalDate now = LocalDate.now();
		ArrayList<LocalDate> objects = new ArrayList<>();
		objects.add(now);
		return objects;
	}

	@PostMapping(path = "/player/sign_up", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void signUp(@RequestBody PlayerSignUpDTO playerDTO) {
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return;
		}
		byte[] hash = digest.digest(playerDTO.getPassword().getBytes(StandardCharsets.UTF_8));
		String passwordHash = Base64.getEncoder().encodeToString(hash);

		Player player = new Player(playerDTO.getNickName(), playerDTO.getEmail(), passwordHash);
		playerRepository.save(player);
	}


	@PostMapping(path = "/player/sign_in", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public PlayerAuthenticationDTO signIn(@RequestBody PlayerSignInDTO playerDTO) {
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}

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
			authenticationRepository.save(new Authentication(playerLoginInfoHash, player.getId()));
			return playerAuthenticationDTO;
		}
		return null;

	}
}
