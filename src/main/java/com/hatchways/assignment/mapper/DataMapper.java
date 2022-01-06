package com.hatchways.assignment.mapper;

import com.hatchways.assignment.data.Posts;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.hatchways.assignment.util.Constants.*;

@Component
public class DataMapper {
    public List<Posts> sortAccordingToSortBy(List<Posts> posts,String sortBy) {
        if (sortBy==null)
            sortBy=ID;
        switch (sortBy) {
            case READS:
               return posts.stream().sorted((Comparator.comparing(Posts::getReads))).collect(Collectors.toList());
            case LIKES:
               return posts.stream().sorted((Comparator.comparing(Posts::getLikes))).collect(Collectors.toList());
            case POPULARITY:
                return posts.stream().sorted((Comparator.comparing(Posts::getPopularity))).collect(Collectors.toList());
            default:
                return posts.stream().sorted((Comparator.comparing(Posts::getId))).collect(Collectors.toList());
        }
    }

    public List<Posts> getListAccordingToDirection(List<Posts> posts,String direction) {
        if(direction==null)
            direction=ASC;

        if (DESC.equals(direction)) {
            Collections.reverse(posts);
            return posts;
        }
        return posts;
    }
}
