package com.animesgun;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.animesgun.entities.Anime;
import com.animesgun.repositories.AnimeRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class AnimeRepositoryTests {

	@Autowired
	AnimeRepository animeRepository;

	@Test
	void createAnimeTest() {
		Anime anime = new Anime();
		anime.setName("dummy");
		anime.setDescription("description");
		assertEquals(animeRepository.save(anime), anime);
	}

}
