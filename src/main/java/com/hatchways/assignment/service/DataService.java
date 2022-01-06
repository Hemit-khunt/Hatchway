package com.hatchways.assignment.service;

import com.hatchways.assignment.exception.ApiException;
import com.hatchways.assignment.mapper.DataMapper;
import com.hatchways.assignment.data.Posts;
import com.hatchways.assignment.util.Constants;
import com.hatchways.assignment.util.DataRetriever;
import com.hatchways.assignment.util.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
public class DataService {

    @Autowired
    DataRetriever dataRetriever;

    @Autowired
    DataMapper dataMapper;

    public List<Posts> getPost(String tags,String sortBy,String direction) throws ApiException, ExecutionException, InterruptedException {
        dataValidator(tags,sortBy,direction);
        List<String> listOfTags= Arrays.asList(tags.split(","));
        listDataValidator(listOfTags);
        log.info("Data Successfully validated");
        List<Posts> posts=dataRetriever.getDataFromTheExternalAPI(listOfTags);
        log.info("Data Retrieve from the api");
        posts=dataMapper.sortAccordingToSortBy(posts,sortBy);
        posts=dataMapper.getListAccordingToDirection(posts,direction);
        log.info("Data set according to the sorting method and direction method");
        return  posts;
    }

    private static void dataValidator(String tags,String sortBy,String direction) {
        if(tags==null) {
            log.error("tag is null");
            throw new ApiException(ErrorMessage.TAG_PARAMETER_REQUIRED);
        }

        if(!Constants.canSortBy.contains(sortBy)){
            log.error(ErrorMessage.SORT_BY_PARAMETER_INVALID);
            throw new ApiException(ErrorMessage.SORT_BY_PARAMETER_INVALID);
        }
        if(!Constants.hasDirection.contains(direction)) {
            log.error(ErrorMessage.DIRECTION_PARAMETER_INVALID);
            throw new ApiException(ErrorMessage.DIRECTION_PARAMETER_INVALID);
        }

    }
    private static void listDataValidator(List<String> listOfTags )
    {
        if(listOfTags.size()==1 && listOfTags.get(0).equals("")) {
            log.error(ErrorMessage.EMPTY_TAG_STRING_NOT_ALLOWED);
            throw new ApiException(ErrorMessage.EMPTY_TAG_STRING_NOT_ALLOWED);
        }
    }
}
