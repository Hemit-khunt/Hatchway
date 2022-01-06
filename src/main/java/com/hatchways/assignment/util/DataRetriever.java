package com.hatchways.assignment.util;


import com.hatchways.assignment.response.Blog;
import com.hatchways.assignment.data.Posts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@Slf4j
public class DataRetriever {

    @Autowired
    RestTemplate restTemplate;

    @Value("${external.api}")
    String url;


    private Blog makeExternalApiCall(String url) {
        return restTemplate.getForObject(url, Blog.class);
    }

    public  List<Posts> getDataFromTheExternalAPI(List<String> tags) {
        Map<Integer,Posts> map=new HashMap<>();
        for (String tag:tags) {
            Blog blog=makeExternalApiCall(url+tag);
            List<Posts> tempPost = null;
            if(blog!=null) {
                tempPost=blog.getPosts();
            }
            else
                throw new NullPointerException();
            assert tempPost != null;
            tempPost.forEach(p->
                map.put(p.getId(),p));
        }

        List<Posts> posts=new ArrayList<>();
        map.forEach((k,v)->posts.add(v));
        return posts;
    }
}
