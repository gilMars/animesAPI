package com.animesgun.controllers;

import java.util.List;
import java.util.Optional;

import com.animesgun.entities.Author;
import com.animesgun.exceptions.ResourceNotFoundException;
import com.animesgun.exceptions.ResponseError;
import com.animesgun.repositories.AuthorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorRepository authorRepository;
    
    @GetMapping
    public @ResponseBody List<Author> getAuthors() {
        return authorRepository.findAll();
    }

    @GetMapping("/{id}")
    public Author getAuthor(Long id) {
        Optional<Author> author = authorRepository.findById(id);
        if (!author.isPresent()) {
            throw new ResourceNotFoundException(String.format("The author %d not found!", id));
        }
        return author.get();
    }

    @PostMapping
    public @ResponseBody Author registerAuthor(@RequestBody Author author) {
        return authorRepository.save(author);
    }

    @PutMapping("/{id}")
    public @ResponseBody Author updateAnime(@PathVariable Long id, @RequestBody Author anime) {
        Optional<Author> searchedAuthor = authorRepository.findById(id);

        if (!searchedAuthor.isPresent()) {
            throw new ResourceNotFoundException(String.format("The anime with Id %d not found!", id));
        }

        Author authorToUpdate = searchedAuthor.get();
        authorToUpdate.setName(anime.getName());
        authorToUpdate.setAge(anime.getAge());

        return authorRepository.saveAndFlush(authorToUpdate);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleException(ResourceNotFoundException exception) {
        ResponseError error = new ResponseError(HttpStatus.NOT_FOUND, exception.getMessage());
        return new ResponseEntity<Object>(error, new HttpHeaders(), error.getStatus());
    }

}
