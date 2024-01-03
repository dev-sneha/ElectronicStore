package com.lcdw.electronic.store.services;

import com.lcdw.electronic.store.dtos.CategoryDto;
import com.lcdw.electronic.store.dtos.PageableResponse;

public interface CategoryService {

    CategoryDto create (CategoryDto categoryDto);

    CategoryDto update (CategoryDto categoryDto, String categoryId);

    void delete(String categoryId);

    PageableResponse<CategoryDto> getAll(int pageNumber,int pageSize, String sortBy, String sortDir);

    //get single category details
    CategoryDto get(String categoryId);







}
