package com.e_commerce.modern.services.images;

import com.e_commerce.modern.dto.imageDto;
import com.e_commerce.modern.exceptions.resourceNotFoundException;
import com.e_commerce.modern.model.image;
import com.e_commerce.modern.model.product;
import com.e_commerce.modern.services.product.IproductService;
import com.e_commerce.modern.services.repository.imageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class imageService implements IImageService {
    private imageRepository imagerepo;
    private IproductService productService;

    @Override
    public image getImageById(Long id) {
        return imagerepo.findById(id)
                .orElseThrow(()-> new resourceNotFoundException("Image not found"+id));
    }

    @Override
    public void deleteImageById(Long id) {
        imagerepo.findById(id).ifPresentOrElse(imagerepo::delete, ()->{
            throw new resourceNotFoundException("Image not found"+id);
        });

    }

    @Override
    public List<imageDto> saveImages(List<MultipartFile> files, Long productid) {
        product product = productService.getproductById(productid);
        List<imageDto> savedImageDto = new ArrayList<>();
        for(MultipartFile file : files) {
            try {
                //new object from image model
                image image = new image();

                //set file details ,there methods created by setters
                // and getters in the model class with @Setter,@Getter annotations
                image.setFileName(file.getOriginalFilename());
                image.setFileName(file.getOriginalFilename());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                //here we set the url download before the system save it into the database ,
                // these url comming from my machine not  actually in the database
                String buildDownloadUrl ="/api/v1/images/image/download";
                String downloadurl=buildDownloadUrl +image.getId();
                image.setDownloadUrl(downloadurl);

                //here we pick the image from the database
                image savedimage = imagerepo.save(image);

                //here we get the actual url from my database
                savedimage.setDownloadUrl(buildDownloadUrl +savedimage.getId());
                imagerepo.save(savedimage);

                //after pick the final url from our database we set there properties in dto object that
                // I still don't know its  purpose
                imageDto imagedtos = new imageDto();
                imagedtos.setImagename(savedimage.getFileName());
                imagedtos.setImageid(savedimage.getId());
                imagedtos.setDownloadurl(savedimage.getDownloadUrl());

                //push image in the list above the for loop
                savedImageDto.add(imagedtos);

            } catch (SQLException | IOException e) {
                throw new RuntimeException(e.getMessage());
            }

        }
        return savedImageDto;
    }

    @Override
    public image updateImage(MultipartFile file, Long imageid) {
        image image = getImageById(imageid);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileName(file.getOriginalFilename());
            image.setImage(new SerialBlob(file.getBytes()));
            imagerepo.save(image);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

        return null;
    }
}
