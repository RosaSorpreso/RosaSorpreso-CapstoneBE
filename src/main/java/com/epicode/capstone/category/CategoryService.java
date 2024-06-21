package com.epicode.capstone.category;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @PostConstruct
    public void initCategories() {

        Category avventura = new Category();
        avventura.setName("Avventura");
        avventura.setDescription("Avventura");

        Category relax = new Category();
        relax.setName("Relax");
        relax.setDescription("Relax");

        Category cultura = new Category();
        cultura.setName("Cultura");
        cultura.setDescription("Cultura");

        Category aTema = new Category();
        aTema.setName("A Tema");
        aTema.setDescription("A Tema");

        if (categoryRepository.findAll().isEmpty()) {
            categoryRepository.save(avventura);
            categoryRepository.save(relax);
            categoryRepository.save(cultura);
            categoryRepository.save(aTema);
        }
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Response getCategoryById(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException("Category with id " + id + " not found");
        }
        Category entity = categoryRepository.findById(id).get();
        Response response = new Response();
        BeanUtils.copyProperties(entity, response);
        return response;
    }

    public Response createCategory(Request request) {
        Category entity = new Category();
        BeanUtils.copyProperties(request, entity);
        Response response = new Response();
        BeanUtils.copyProperties(entity, response);
        categoryRepository.save(entity);
        return response;
    }

    public String deleteCategoryById(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException("Category with id " + id + " not found");
        }
        categoryRepository.deleteById(id);
        return "Category with id " + id + " deleted successfully";
    }
}
