package com.epicode.capstone.photo;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PhotoService {

    private final PhotoRepository photoRepository;

    public List<Photo> getAllPhotos() {
        return photoRepository.findAll();
    }

    public Response getPhotoById(Long id) {
        if (!photoRepository.existsById(id)) {
            throw new EntityNotFoundException("Photo with id " + id + " not found");
        }
        Photo entity = photoRepository.findById(id).get();
        Response response = new Response();
        BeanUtils.copyProperties(entity, response);
        return response;
    }

    public Response savePhoto(Request request) {
        Photo entity = new Photo();
        BeanUtils.copyProperties(request, entity);
        Response response = new Response();
        BeanUtils.copyProperties(entity, response);
        photoRepository.save(entity);
        return response;
    }

    public String deletePhoto(Long id) {
        if (!photoRepository.existsById(id)) {
            throw new EntityNotFoundException("Photo with id " + id + " not found");
        }
        photoRepository.deleteById(id);
        return "Photo with id " + id + " deleted successfully";
    }
}
