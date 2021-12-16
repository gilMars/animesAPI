package com.animesgun.controllers;

import java.util.List;
import java.util.Optional;

import com.animesgun.entities.Anime;
import com.animesgun.exceptions.ResourceNotFoundException;
import com.animesgun.exceptions.ResponseError;
import com.animesgun.repositories.AnimeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("animes")
public class AnimeController {

    @Autowired
    private AnimeRepository repository;

    @GetMapping
    public @ResponseBody List<Anime> getAnimes() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public @ResponseBody Optional<Anime> getAnime(@PathVariable Long id) {
        Optional<Anime> optionalValue = repository.findById(id);
        if (!optionalValue.isPresent()) {
            throw new ResourceNotFoundException(String.format("The anime with Id %d not found!", id));
        }
        return optionalValue;
    }

    @PostMapping
    public @ResponseBody Anime registerAnime(@RequestBody Anime anime) {
        return repository.save(anime);
    }

    @PutMapping("/{id}")
    public @ResponseBody Anime updateAnime(@PathVariable Long id, @RequestBody Anime anime) {
        Optional<Anime> searchedAnime = repository.findById(id);

        if (!searchedAnime.isPresent()) {
            throw new ResourceNotFoundException(String.format("The anime with Id %d not found!", id));
        }

        Anime animeToUpdate = searchedAnime.get();
        animeToUpdate.setName(anime.getName());
        animeToUpdate.setRate(anime.getRate());
        animeToUpdate.setDescription(anime.getDescription());

        return repository.saveAndFlush(animeToUpdate);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleException(ResourceNotFoundException exception) {
        ResponseError error = new ResponseError(HttpStatus.NOT_FOUND, exception.getMessage());
        return new ResponseEntity<Object>(error, new HttpHeaders(), error.getStatus());
    }

}