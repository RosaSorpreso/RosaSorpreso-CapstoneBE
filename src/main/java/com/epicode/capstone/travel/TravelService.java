package com.epicode.capstone.travel;

import com.epicode.capstone.category.Category;
import com.epicode.capstone.category.CategoryRepository;
import com.epicode.capstone.continent.Continent;
import com.epicode.capstone.continent.ContinentRepository;
import com.epicode.capstone.photo.PhotoRepository;
import com.epicode.capstone.security.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TravelService {

    private final TravelRepository travelRepository;
    private final CategoryRepository categoryRepository;
    private final ContinentRepository continentRepository;
    private final UserRepository userRepository;

    public List<Travel> getAllTravels() {
        return travelRepository.findAll();
    }

    public Travel getTravelById(Long id) {
        if (!travelRepository.existsById(id)) {
            throw new EntityNotFoundException("Travel with id " + id + " not found");
        }
        return travelRepository.findById(id).get();
    }

    @Transactional
    public Response createTravel(Request request ) {
        if (!categoryRepository.existsById(request.getIdCategories())){
            throw new EntityNotFoundException("Category with id " + request.getIdCategories() + " not found");
        }

        if (!continentRepository.existsById(request.getIdContinent())){
            throw new EntityNotFoundException("Continent with id " + request.getIdContinent() + " not found");
        }

        Travel entity = new Travel();
        BeanUtils.copyProperties(request,entity);
        Category category = categoryRepository.findById(request.getIdCategories()).get();
        Continent continent = continentRepository.findById(request.getIdContinent()).get();
        entity.setCategory(category);
        entity.setContinent(continent);
        Response response = new Response();
        travelRepository.save(entity);
        BeanUtils.copyProperties(entity, response);
        return response;
    }

    public Response updateTravel(Long id, Request request) {
        if (!travelRepository.existsById(id)) {
            throw new EntityNotFoundException("Travel with id " + id + " not found");
        }
        if (!categoryRepository.existsById(request.getIdCategories())){
            throw new EntityNotFoundException("Category with id " + request.getIdCategories() + " not found");
        }
        if (!continentRepository.existsById(request.getIdContinent())){
            throw new EntityNotFoundException("Continent with id " + request.getIdContinent() + " not found");
        }
        Travel entity = travelRepository.findById(id).get();
        Category category = categoryRepository.findById(request.getIdCategories()).get();
        Continent continent = continentRepository.findById(request.getIdContinent()).get();
        BeanUtils.copyProperties(request,entity);
        entity.setCategory(category);
        entity.setContinent(continent);
        travelRepository.save(entity);
        Response response = new Response();
        BeanUtils.copyProperties(entity, response);
        return response;
    }

    public String deleteTravel(Long id) {
        if (!travelRepository.existsById(id)) {
            throw new EntityNotFoundException("Travel with id " + id + " not found");
        }
        travelRepository.deleteById(id);
        return "Travel with id " + id + " deleted successfully";
    }
}
