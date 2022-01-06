package com.hatchways.assignment.response;

import com.hatchways.assignment.data.Posts;
import lombok.*;

import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Blog {
    List<Posts> posts;
}
