package com.group26.termproject;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.group26.termproject.controllers.PlayerRestController;
import com.group26.termproject.dto.PlayerChangePasswordDTO;
import com.group26.termproject.dto.PlayerSignInDTO;
import com.group26.termproject.repositories.AuthenticationRepository;
import com.group26.termproject.repositories.PlayerRepository;
import com.group26.termproject.tables.Authentication;
import com.group26.termproject.tables.Player;
import org.aspectj.apache.bcel.util.Play;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest
public class PlayerRestControllerTests {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private PlayerRepository playerRepository;
	@MockBean
	private AuthenticationRepository authenticationRepository;
	private ObjectMapper mapper;

	@Test
	public void contextLoads() {
	}

	@Before
	public void setUp() {
		mapper = new ObjectMapper();
	}

	@Test
	public void shouldPass() {
		Assert.assertTrue(true);
	}

	@Test
	public void shouldSignUp() throws Exception {
		Player player = new Player("test", "test", "1234");

		mockMvc.perform(post("/player/sign_up")
						.content(mapper.writeValueAsString(player))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		verify(playerRepository).save(any(Player.class));
	}

	@Test
	public void shouldNotSignUpIfGivenEmailAlreadyExist() throws Exception {
		Player player = new Player("test", "test", "1234");
		Optional<Player> optionalPlayer = Optional.of(player);

		when(playerRepository.findByEmail(any(String.class))).thenReturn(optionalPlayer);

		mockMvc.perform(post("/player/sign_up")
				.content(mapper.writeValueAsString(player))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError());
		verify(playerRepository, never()).save(any(Player.class));
	}

	@Test
	public void shouldNotLoginIfUsernameOrPasswordIsIncorrect() throws Exception {
		PlayerSignInDTO playerSignInDTO = new PlayerSignInDTO();
	    playerSignInDTO.setEmail("test");
		playerSignInDTO.setPassword("test");

		when(playerRepository.findByEmailAndPassword(any(String.class), any(String.class))).thenReturn(Optional.empty());

		mockMvc.perform(post("/player/sign_in")
				.content(mapper.writeValueAsString(playerSignInDTO))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError());
		verify(authenticationRepository, never()).save(any(Authentication.class));
	}

	@Test
	public void shouldLoginIfCredentialsAreCorrect() throws Exception {
		Player player = new Player("test", "test", "test");
		PlayerSignInDTO playerSignInDTO = new PlayerSignInDTO();
		playerSignInDTO.setEmail("test");
		playerSignInDTO.setPassword("test");

		when(playerRepository.findByEmailAndPassword(any(String.class), any(String.class))).thenReturn(Optional.of(player));

		mockMvc.perform(post("/player/sign_in")
				.content(mapper.writeValueAsString(playerSignInDTO))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.token", is(String.class)));

		verify(authenticationRepository, times(1)).save(any(Authentication.class));
	}

	@Test
	public void shouldNotChangePasswordIfTokenIsInvalid() throws Exception {
		PlayerChangePasswordDTO playerChangePasswordDTO = new PlayerChangePasswordDTO();
		playerChangePasswordDTO.setToken("invalidToken");
		playerChangePasswordDTO.setPassword("pass");

		when(playerRepository.findByToken(any(String.class))).thenReturn(Optional.empty());

		mockMvc.perform(put("/player/change_password")
				.content(mapper.writeValueAsString(playerChangePasswordDTO))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError());
		verify(playerRepository, never()).save(any(Player.class));
	}

	@Test
	public void shouldChangePasswordIfTokenIsValid() throws Exception {
		Player player = new Player("test", "test", "test");
		PlayerChangePasswordDTO playerChangePasswordDTO = new PlayerChangePasswordDTO();
		playerChangePasswordDTO.setToken("validToken");
		playerChangePasswordDTO.setPassword("pass");

		when(playerRepository.findByToken(any(String.class))).thenReturn(Optional.of(player));

		mockMvc.perform(put("/player/change_password")
				.content(mapper.writeValueAsString(playerChangePasswordDTO))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		verify(playerRepository, times(1)).save(any(Player.class));
	}

	@Test
	public void shouldReturnTheAuthenticatedPlayerIfTokenIsValid() throws Exception {
		Player player = new Player("test", "test", "test");

		when(playerRepository.findByToken(any(String.class))).thenReturn(Optional.of(player));

		mockMvc.perform(get("/player/me/token").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.nickName", is(String.class)))
				.andExpect(jsonPath("$.email", is(String.class)));
	}

	@Test
	public void shouldNotReturnTheAuthenticatedPlayerIfTokenIsInvalid() throws Exception {
		Player player = new Player("test", "test", "test");

		when(playerRepository.findByToken(any(String.class))).thenReturn(Optional.empty());

		mockMvc.perform(get("/player/me/token").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError());
	}
}

