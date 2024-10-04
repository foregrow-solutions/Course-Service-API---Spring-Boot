package com.winggs.course.service.impl;

import com.winggs.course.service.UploadService;
import com.winggs.course.util.FileUploadUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class UploadServiceImpl implements UploadService {

    @Value("${common.media.path}")
    String path;

    @Override
    public String upload(MultipartFile file) throws IOException {
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        //Save File in Local dir
        FileUploadUtils.saveFile(filename, file);
        String url = path;
        return url + filename;
    }

    @Override
    public void remove(String id) throws IOException {
        //TODO: pass filename
        try {
            FileUploadUtils.removeFile(id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
