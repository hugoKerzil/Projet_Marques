package com.demo;

import com.dtos.MovieDto;
import com.dtos.PosterDto;
import com.entities.Authen;
import com.entities.Movie;
import com.entities.Poster;
import com.mappers.MovieMapper;
import com.mappers.PosterMapper;
import com.repositories.AuthenRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private AuthenRepository authenRepository;

	@Autowired
	private PosterMapper posterMapper;

	@Test
	void contextLoads() {
	}

	@Test
	void userPseudoShouldNotBeNull() {
		Authen user = new Authen();
		user.setPassword("1234");

		assertThrows(org.springframework.dao.DataIntegrityViolationException.class, () -> {
			authenRepository.save(user);
		});
	}

	@Test
	void testMovieMapper() {
		MovieDto dto = new MovieDto();
		dto.setTitle("Interstellar");

		Movie entity = MovieMapper.toEntity(dto);

		assertEquals("Interstellar", entity.getTitle());
	}

	@Test
	void testPosterMapper() {
		PosterDto dto = new PosterDto();
		dto.setDescription("Poster de test");

		Poster entity = posterMapper.toEntity(dto);

		assertEquals("Poster de test", entity.getDescription());
	}

}