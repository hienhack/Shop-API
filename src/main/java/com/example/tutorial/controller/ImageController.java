package com.example.tutorial.controller;

import com.example.tutorial.dto.Response.ResponseDTO;
import com.example.tutorial.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<String> uploadImage(@RequestPart(name = "image")MultipartFile image) {
        return ResponseDTO.of(imageService.upload(image));
    }

    @DeleteMapping("/{fileName}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteImage(@PathVariable(name = "fileName") String fileName) {
        imageService.delete(fileName);
    }
}
