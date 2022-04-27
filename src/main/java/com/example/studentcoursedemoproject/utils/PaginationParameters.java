package com.example.studentcoursedemoproject.utils;

import java.util.List;
import java.util.Map;

public class PaginationParameters {

    public static void getdata(Map<String, Object> map, Integer page, Long total, Integer size, List<?> lists) {

        Integer nextPage = ((page + 1) < total) ? page + 1 : 0;
        Integer previousPage = ((page - 1) >= 0) ? page - 1 : 0;
        map.put("currentPage", page);
        map.put("nextPage", nextPage);
        map.put("previousPage", previousPage);
        map.put("size", size);
        map.put("total", total);
        map.put("lists", lists);
    }

}