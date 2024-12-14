package com.e_commerce.modern.services.images;

import com.e_commerce.modern.dto.imageDto;
import com.e_commerce.modern.model.image;
import com.e_commerce.modern.model.product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    image getImageById(Long id);
    void deleteImageById(Long id);
    List<imageDto> saveImages(List<MultipartFile> files , Long productid);
    image updateImage(MultipartFile image, Long imageid);
}
