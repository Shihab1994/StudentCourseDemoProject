package com.example.studentcoursedemoproject.utils;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CommonDataHelper {

    public void getCommonData(Integer page, Integer size, Map<String, ?> searchResult, PaginatedResponse response, List<?> list) {

        Integer currentPage = (Integer) searchResult.get("currentPage");
        Integer nextPage = (Integer) searchResult.get("nextPage");
        Integer previousPage = (Integer) searchResult.get("previousPage");

        Map<String, Object> meta = new HashMap<>();
        meta.put("currentPage", currentPage);
        meta.put("nextPage", nextPage);
        meta.put("previousPage", previousPage);
        meta.put("size", searchResult.get("size"));
        meta.put("total", searchResult.get("total"));

        response.setList(list);
        response.setMeta(meta);
    }

}

