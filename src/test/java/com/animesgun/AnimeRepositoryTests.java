package com.animesgun;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.animesgun.entities.Anime;
import com.animesgun.repositories.AnimeRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public
class AnimeRepositoryTests {

	@Autowired
	private AnimeRepository animeRepository;

	public Anime createAnime() {
		Anime anime = new Anime();
		anime.setName("dummy");
		anime.setDescription("description");
		return anime;
	}

	@Test
	void createAnimeTest() {
		Anime anime = createAnime();
		assertEquals(animeRepository.save(anime), anime);
	}

	@Test
	void getAnimeTest() {
		Anime anime = createAnime();
		animeRepository.save(anime);
		assertFalse(animeRepository.findAll().isEmpty());
	}
}
