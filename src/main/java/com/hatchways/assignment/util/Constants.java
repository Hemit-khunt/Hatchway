package com.hatchways.assignment.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Constants {
    private Constants() {
    }
    public static final String ID="id";
    public static final String READS="reads";
    public static final String LIKES="likes";
    public static final String POPULARITY="popularity";
    public static final String DESC="desc";
    public static final String ASC="asc";
    public static final Set<String> canSortBy= new HashSet<>(Arrays.asList(ID,READS,LIKES,POPULARITY,null));
    public static final Set<String> hasDirection= new HashSet<>(Arrays.asList(DESC,ASC,null));
    public static final String CACHE_NAME="CACHING_POSTS";



}
