package com.lcdw.electronic.store.services.impl;

import com.lcdw.electronic.store.dtos.CategoryDto;
import com.lcdw.electronic.store.dtos.PageableResponse;
import com.lcdw.electronic.store.entities.Category;
import com.lcdw.electronic.store.exceptions.ResourceNotFoundException;
import com.lcdw.electronic.store.helper.Helper;
import com.lcdw.electronic.store.repositories.CategoryRepository;
import com.lcdw.electronic.store.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper mapper;
    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        String categoryId= UUID.randomUUID().toString();
        categoryDto.setCategoryId(categoryId);
        Category category=mapper.map(categoryDto , Category.class);
        Category saveCategory= categoryRepository.save(category);

        return mapper.map(saveCategory,CategoryDto.class);
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto, String categoryId) {
        //get category of given id
      Category category=  categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(" Category not found  !!"));
      //update category details
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(categoryDto.getCoverImage());
        Category updateCategory= categoryRepository.save(category);
        return mapper.map(updateCategory,CategoryDto.class);

    }

    @Override
    public void delete(String categoryId) {
        Category category= categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException(" category not found  !!"));
        categoryRepository.delete(category);
    }

    @Override
    public PageableResponse<CategoryDto> getAll(int pageNumber,int pageSize, String sortBy, String sortDir) {

       Pageable pageable = PageRequest.of(pageNumber,pageSize);
        Page<Category> page=categoryRepository.findAll(pageable);
        PageableResponse<CategoryDto> pageableResponse= Helper.getPageableResponse(page, CategoryDto.class);
        return pageableResponse;

    }

    @Override
    public CategoryDto get(String categoryId) {
        Category category=categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category not found with the given id !!"));

        return mapper.map(category,CategoryDto.class);
    }
}

