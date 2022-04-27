package com.example.studentcoursedemoproject.utils;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class MultipleDocuments implements Serializable {

    private List<MultipartFile> files;

    public Map<Integer, String> validate(Integer maxSize, String[] fileType) {
        Map<Integer, String> errors = new HashMap<>();

        for (int i = 0; i < files.size(); i++) {

            if (files.get(i).getSize() > maxSize) {
                errors.put(i, " Max allowed file size: " + maxSize);
            }

            var fileExtension = FilenameUtils.getExtension(files.get(i).getOriginalFilename());
            List<String> fileTypes = Arrays.asList(fileType);

            if (!fileTypes.contains(fileExtension)) {
                errors.put(i, "." + fileExtension + " file not allowed");
            }
        }

        return errors;
    }
}

