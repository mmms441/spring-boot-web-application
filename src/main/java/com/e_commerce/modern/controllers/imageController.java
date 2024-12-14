package com.e_commerce.modern.controllers;

import com.e_commerce.modern.dto.imageDto;
import com.e_commerce.modern.exceptions.resourceNotFoundException;
import com.e_commerce.modern.model.image;
import com.e_commerce.modern.response.apiResponse;
import com.e_commerce.modern.services.images.IImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/images")
public class imageController {
    private final IImageService imageService;

    //method with th type post for uploding images request
    @PostMapping("/upload")
    public ResponseEntity<apiResponse> saveImages(@RequestParam List<MultipartFile> files , @RequestParam Long productId) {
        try {
            List<imageDto> imageDtos = imageService.saveImages(files, productId);
            return ResponseEntity.ok(new apiResponse("success uploaded " , imageDtos));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new apiResponse("upload failed !"  ,e.getMessage()));
        }
    }

    //here the method get for retreve image
    @GetMapping("/image/download/{imageId}")
    public ResponseEntity<Resource> downloadImage(@PathVariable Long imageId) throws SQLException {
        image image = imageService.getImageById(imageId);
        ByteArrayResource resource =new ByteArrayResource(image.getImage().getBytes(1 ,(int) image.getImage().length()));
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; file=\"" +image.getFileName()  ).body(resource);



    }
    //this method is used to update image , its request method from the type put to update the image
    @PutMapping("/image/{imageId}/update")
    public ResponseEntity<apiResponse> updateImage(@PathVariable Long imageId, @RequestParam MultipartFile file) {
        try {
            image image = imageService.getImageById(imageId);
            if(image != null){
                imageService.updateImage( file,imageId );
                return ResponseEntity.ok().body(new apiResponse("success updated " , null));
            }
        } catch (resourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new apiResponse("update failed !" ,null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new apiResponse("update failed !"  ,INTERNAL_SERVER_ERROR));
    }

    //method from the type delete to request to delete image
    @DeleteMapping("/image/{imageId}/delete")
    public ResponseEntity<apiResponse> deleteImage(@PathVariable Long imageId ) {
        try {
            image image = imageService.getImageById(imageId);
            if(image != null){
                imageService.deleteImageById( imageId );
                return ResponseEntity.ok().body(new apiResponse("success deleted " , null));
            }
        } catch (resourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new apiResponse("delete failed !" ,null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new apiResponse("delete failed !"  ,INTERNAL_SERVER_ERROR));
    }
}
