package com.hatchways.assignment.controller;


import com.hatchways.assignment.response.Blog;
import com.hatchways.assignment.exception.ApiException;
import com.hatchways.assignment.response.ErrorResponse;
import com.hatchways.assignment.response.Ping;
import com.hatchways.assignment.data.Posts;
import com.hatchways.assignment.service.DataService;
import com.hatchways.assignment.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@Slf4j
@RequestMapping("/api")
@CacheConfig(cacheNames = Constants.CACHE_NAME)
public class ApiController {


    @Autowired
    DataService service;

    @GetMapping("/ping")
    @ResponseBody
    public ResponseEntity<Ping> getPingResponse(){
        return new ResponseEntity<>(new Ping(true), HttpStatus.OK);
    }

    @GetMapping("/posts")
    @Cacheable
    @Async
    public CompletableFuture<ResponseEntity<Object>> getPosts(
            //put tags required field false so we can handle custom exception
            @RequestParam(required = false) String tags,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String direction)
    {
        log.info("Get Post Method Call");
        Blog blog;
        try {
            List<Posts> posts= service.getPost(tags,sortBy,direction);
            blog=new Blog(posts);
        } catch (ApiException e) {
            return  CompletableFuture.completedFuture(new ResponseEntity<>(new ErrorResponse(e.getMessage()),HttpStatus.BAD_REQUEST));
        } catch (ExecutionException | InterruptedException e) {
            return CompletableFuture.completedFuture(new ResponseEntity<>(new ErrorResponse("Unable to make API call"),HttpStatus.INTERNAL_SERVER_ERROR));
        }
        return CompletableFuture.completedFuture(new ResponseEntity<>(blog,HttpStatus.OK));
    }


}
