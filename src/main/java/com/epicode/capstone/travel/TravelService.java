package com.epicode.capstone.travel;

import com.epicode.capstone.category.Category;
import com.epicode.capstone.category.CategoryRepository;
import com.epicode.capstone.continent.Continent;
import com.epicode.capstone.continent.ContinentRepository;
import com.epicode.capstone.photo.PhotoRepository;
import com.epicode.capstone.security.User;
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

    //GET
    public List<Travel> getAllTravels() {
        return travelRepository.findAll();
    }

    // GET BY ID
    public Travel getTravelById(Long id) {
        if (!travelRepository.existsById(id)) {
            throw new EntityNotFoundException("Travel with id " + id + " not found");
        }
        return travelRepository.findById(id).get();
    }

    //POST
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

    //PUT
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

    //DELETE
    public String deleteTravel(Long id) {
        if (!travelRepository.existsById(id)) {
            throw new EntityNotFoundException("Travel with id " + id + " not found");
        }
        travelRepository.deleteById(id);
        return "Travel with id " + id + " deleted successfully";
    }


    //PERSONALIZED METHODS
    public List<Travel> findTravelsByContinent(Continent continent) {
        return travelRepository.findByContinent(continent);
    }

    public List<Travel> findTravelsByCategory(Category category) {
        return travelRepository.findByCategory(category);
    }

    public List<Travel> findTravelsByIsSoldOut(boolean isSoldOut) {
        return travelRepository.findByIsSoldOut(isSoldOut);
    }

    public List<Travel> findTravelsByPassportIsRequired(boolean passportIsRequired) {
        return travelRepository.findByPassportIsRequired(passportIsRequired);
    }

    public List<Travel> findTravelsByMonth(int month) {
        return travelRepository.findByMonth(month);
    }

    //PURCHASE
    @Transactional
    public String purchaseTravel(Long travelId, User userId) {
        if (!travelRepository.existsById(travelId)) {
            throw new EntityNotFoundException("Travel with id " + travelId + " not found");
        }
        if (!userRepository.existsById(userId.getId())) {
            throw new EntityNotFoundException("User with id " + userId.getId() + " not found");
        }
        Travel travel = travelRepository.findById(travelId).get();
        User user = userRepository.findById(userId.getId()).get();
        if (travel.getAvailableSeats() <= 0){
            throw new IllegalStateException("Travel with id " + travelId + " has no available seats");
        }
        travel.setAvailableSeats(travel.getAvailableSeats() - 1);
        travel.getTravelers().add(user);
        if (travel.getAvailableSeats() == 0){
            travel.setSoldOut(true);
        }
        travelRepository.save(travel);
        return "Travel with id " + travelId + " purchased successfully";
    }

}
