package com.hatchways.assignment.data;

import com.fasterxml.jackson.annotation.JsonTypeId;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Posts {

    private int id;
    private String author;
    private int authorId;
    private int likes;
    private float popularity;
    private long reads;
    private String[] tags;
}
