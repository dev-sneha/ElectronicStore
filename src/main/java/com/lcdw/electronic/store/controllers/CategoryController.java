package com.lcdw.electronic.store.controllers;

import com.lcdw.electronic.store.dtos.ProductDto;
import com.lcdw.electronic.store.dtos.CategoryDto;
import com.lcdw.electronic.store.dtos.ApiResponseMessage;
import com.lcdw.electronic.store.dtos.PageableResponse;


import com.lcdw.electronic.store.services.CategoryService;
import com.lcdw.electronic.store.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired

    private ProductService productService;
    //create

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
        //call service to save object
        CategoryDto categoryDto1 =categoryService.create(categoryDto);
        return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED);
    }


    //update
    @PutMapping("/{categoryId}")

    public ResponseEntity<CategoryDto> updateCategory(@PathVariable String categoryId,@RequestBody CategoryDto categoryDto){
      CategoryDto updatedCategory=  categoryService.update(categoryDto,categoryId);

      return new ResponseEntity<>(updatedCategory,HttpStatus.OK);
    }

    //delete

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponseMessage> deleteCategory(@PathVariable String categoryId){
          categoryService.delete(categoryId);
        ApiResponseMessage message= ApiResponseMessage.builder().message("Category is deleted Successfully !!").success(true).status(HttpStatus.OK).build();
        return new ResponseEntity<>(message,HttpStatus.OK);

    }

    //get all

    @GetMapping
    public ResponseEntity<PageableResponse<CategoryDto>> getAllUser(@RequestParam(value="pageNumber", defaultValue="0", required =false) int pageNumber,
                                                                    @RequestParam(value="pageSize", defaultValue="10", required =false) int pageSize,
                                                                    @RequestParam(value="sortBy", defaultValue="name", required =false) String sortBy,
                                                                    @RequestParam(value="sortDir", defaultValue="asc", required =false) String sortDir){
        PageableResponse<CategoryDto> pageableResponse=categoryService.getAll(pageNumber,pageSize, sortBy,sortDir);
        return new ResponseEntity<>(pageableResponse, HttpStatus.OK);

    }

    //get single

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getSingle(@PathVariable String categoryId){
        CategoryDto categoryDto= categoryService.get(categoryId);
        return  ResponseEntity.ok(categoryDto);
    }


    //create product with category
    @PostMapping("/{categoryId}/{products}")
    public ResponseEntity<ProductDto> createProductWithCategory(@PathVariable("categoryId") String categoryId, @RequestBody ProductDto dto){
        ProductDto productWithCategory=  productService.createWithCategory(dto, categoryId);
        return new ResponseEntity<>(productWithCategory, HttpStatus.OK);

    }

    //update category of product

    @PutMapping("/{categoryId}/products/{productId}")
   public ResponseEntity<ProductDto> updateCategoryOfProduct(@PathVariable String categoryId, @PathVariable String productId){
        ProductDto productDto= productService.updateCategory(productId,categoryId);
        return new ResponseEntity<>(productDto,HttpStatus.OK);
    }

    @GetMapping("/{categoryId}/products")
    public ResponseEntity<PageableResponse<ProductDto>> getProductsOfCategory(@PathVariable String categoryId,
                                                                              @RequestParam(value="pageNumber", defaultValue="0", required =false) int pageNumber,
                                                                              @RequestParam(value="pageSize", defaultValue="10", required =false) int pageSize,
                                                                              @RequestParam(value="sortBy", defaultValue="name", required =false) String sortBy,
                                                                              @RequestParam(value="sortDir", defaultValue="asc", required =false) String sortDir)
    {
        PageableResponse<ProductDto> response=productService.getAllOfCategory(categoryId,pageNumber,pageSize, sortBy,sortDir);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
