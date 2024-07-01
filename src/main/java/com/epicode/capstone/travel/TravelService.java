package com.epicode.capstone.travel;

import com.cloudinary.Cloudinary;
import com.epicode.capstone.category.Category;
import com.epicode.capstone.category.CategoryRepository;
import com.epicode.capstone.continent.Continent;
import com.epicode.capstone.continent.ContinentRepository;
import com.epicode.capstone.email.EmailService;
import com.epicode.capstone.security.User;
import com.epicode.capstone.security.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TravelService {

    private final TravelRepository travelRepository;
    private final CategoryRepository categoryRepository;
    private final ContinentRepository continentRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final Cloudinary cloudinary;

    //GET
    public List<CompleteResponse> getAllTravels() {
        List<Travel> travels = travelRepository.findAll();
        return TravelMapper.INSTANCE.travelsToCompleteResponses(travels);
    }

    // GET BY ID
    public CompleteResponse getTravelById(Long id) {
        if (!travelRepository.existsById(id)) {
            throw new EntityNotFoundException("Travel with id " + id + " not found");
        }
        Travel travel = travelRepository.findById(id).get();
        return TravelMapper.INSTANCE.travelToCompleteResponse(travel);
    }

    //POST
    @Transactional
    public Response createTravel(Request request, MultipartFile[] files ) throws IOException {
        if (!categoryRepository.existsById(request.getIdCategories())){
            throw new EntityNotFoundException("Category with id " + request.getIdCategories() + " not found");
        }

        if (!continentRepository.existsById(request.getIdContinent())){
            throw new EntityNotFoundException("Continent with id " + request.getIdContinent() + " not found");
        }

        List<String> urls = new ArrayList<>();

        for (MultipartFile file : files) {
            var uploadResult = cloudinary.uploader().upload(file.getBytes(),
                    com.cloudinary.utils.ObjectUtils.asMap("public_id", request.getName() + "_avatar_" + UUID.randomUUID().toString()));
            String url = uploadResult.get("url").toString();
            urls.add(url);
        }

        Travel entity = new Travel();
        entity.setPhotos(urls);
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

    public List<Travel> findTravelsByMonth(Integer month) {
        return travelRepository.findByMonth(month);
    }

    //PURCHASE A TRAVEL AND REMOVE IT FROM THE WISHLIST IF PRESENT
    @Transactional
    public String purchaseTravel(Long travelId, Long userId) {
        if (!travelRepository.existsById(travelId)) {
            throw new EntityNotFoundException("Travel with id " + travelId + " not found");
        }
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("User with id " + userId + " not found");
        }
        Travel travel = travelRepository.findById(travelId).get();
        User user = userRepository.findById(userId).get();
        if (travel.getAvailableSeats() <= 0){
            throw new IllegalStateException("Travel with id " + travelId + " has no available seats");
        }
        travel.setAvailableSeats(travel.getAvailableSeats() - 1);
        travel.getTravelers().add(user);
        if (travel.getAvailableSeats() == 0){
            travel.setSoldOut(true);
        }
        if (user.getWishlist().contains(travel)){
            user.getWishlist().remove(travel);
        }
        travelRepository.save(travel);
        userRepository.save(user);
        emailService.sendConfirmMail(user, travel);
        return "Travel with id " + travelId + " purchased successfully";
    }

    //ADD TRAVEL TO WISHLIST
    @Transactional
    public String addTravelToWishlist(Long travelId, Long userId) {
        if (!travelRepository.existsById(travelId)) {
            throw new EntityNotFoundException("Travel with id " + travelId + " not found");
        }
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("User with id " + userId + " not found");
        }

        Travel travel = travelRepository.findById(travelId).get();
        User user = userRepository.findById(userId).get();

        if (!user.getWishlist().contains(travel)) {
            user.getWishlist().add(travel);
            userRepository.save(user);
            return "Travel with id " + travelId + " added to wishlist successfully";
        } else {
            return "Travel with id " + travelId + " is already in the wishlist";
        }
    }

}
