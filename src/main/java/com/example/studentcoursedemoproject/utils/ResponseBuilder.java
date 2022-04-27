package com.example.studentcoursedemoproject.utils;

import com.example.studentcoursedemoproject.constants.ResponseStatus;
import lombok.Data;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

@Data
public class ResponseBuilder implements Response {

    private final ResponseType type;
    private String status = null;
    private Object data = null;
    private Object markList = null;
    private Object meta = null;
    private String message = null;
    private Object errors = null;

    public ResponseBuilder(ResponseType type) {
        this.type = type;
    }

    public static ResponseBuilder success(Object data) {
        ResponseBuilder response = new ResponseBuilder(ResponseType.DATA);
        response.data = data;
        response.status = ResponseStatus.SUCCESS;
        return response;
    }

    public static ResponseBuilder success(Object data, String message) {
        ResponseBuilder response = new ResponseBuilder(ResponseType.DATA);
        response.data = data;
        response.message = message;
        response.status = ResponseStatus.SUCCESS;
        return response;
    }

    public static ResponseBuilder error(Object errors) {
        ResponseBuilder response = new ResponseBuilder(ResponseType.ERROR);
        response.errors = errors;
        response.status = ResponseStatus.ERROR;
        return response;
    }

    public static ResponseBuilder error(Object errors, String message) {
        ResponseBuilder response = new ResponseBuilder(ResponseType.ERROR);
        response.errors = errors;
        response.message = message;
        response.status = ResponseStatus.ERROR;
        return response;
    }

    @Override
    public JSONObject getJson() {

        Map<String, Object> maps = new HashMap<String, Object>();

        switch (this.type) {
            case DATA:
                maps.put("status", status);
                maps.put("data", data);
                maps.put("meta", meta);
                maps.put("message", message);
            case ERROR:
                maps.put("status", status);
                maps.put("message", message);
                maps.put("errors", errors);
        }

        return new JSONObject(maps);
    }

}
