package com.epicode.capstone.continent;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContinentService {

    private final ContinentRepository continentRepository;

    @PostConstruct
    public void initContinents() {

        Continent africa = new Continent();
        africa.setName("Africa");

        Continent nordAmerica = new Continent();
        nordAmerica.setName("Nord America");

        Continent sudAmerica = new Continent();
        sudAmerica.setName("Sud America");

        Continent asia = new Continent();
        asia.setName("Asia");

        Continent europa = new Continent();
        europa.setName("Europa");

        Continent oceania = new Continent();
        oceania.setName("Oceania");

        Continent antartide = new Continent();
        antartide.setName("Antartide");

        if (continentRepository.findAll().isEmpty()){
            continentRepository.save(africa);
            continentRepository.save(nordAmerica);
            continentRepository.save(sudAmerica);
            continentRepository.save(asia);
            continentRepository.save(europa);
            continentRepository.save(oceania);
            continentRepository.save(antartide);
        }
    }

    private List<Continent> getAllContinents() {
        return continentRepository.findAll();
    }

    private Response getContinentById(Long id) {
        if (!continentRepository.existsById(id)) {
            throw new EntityNotFoundException("Continent with id " + id + " not found");
        }
        Continent entity = continentRepository.findById(id).get();
        Response response = new Response();
        BeanUtils.copyProperties(entity, response);
        return response;
    }
}
