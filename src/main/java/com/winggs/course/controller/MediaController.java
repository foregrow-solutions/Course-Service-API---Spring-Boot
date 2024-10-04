package com.winggs.course.controller;

import com.winggs.course.service.UploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Tag(name = "Upload Ex APIs", description = "API for Manage Media Operations")
@CrossOrigin
@Slf4j
@RequiredArgsConstructor
@RestController
public class MediaController {
    private final UploadService mediaService;

    @Operation(summary = "Upload Media Images")
    @PostMapping("/settings/upload")
    public String uploadImage(@RequestParam("file") MultipartFile[] file) throws IOException {
        return mediaService.upload(file[0]);
    }

    @Operation(summary = "Delete Given Media by cid")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/media/{publicId}")
    public void removeMedia(@PathVariable String publicId) throws IOException {
        mediaService.remove(publicId);
    }
}
