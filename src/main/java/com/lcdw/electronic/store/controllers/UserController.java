package com.lcdw.electronic.store.controllers;


import com.lcdw.electronic.store.dtos.ApiResponseMessage;
import com.lcdw.electronic.store.dtos.ImageResponse;
import com.lcdw.electronic.store.dtos.UserDto;
import com.lcdw.electronic.store.services.FileService;
import com.lcdw.electronic.store.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    @Value("${user.profile.image.path}")
    private String imageUploadPath;
    //create

    @PostMapping
   public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
       UserDto user= userService.createUser(userDto);
       return new ResponseEntity<>(user , HttpStatus.CREATED);
   }


    //update

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("userId") String userId,@Valid @RequestBody UserDto
                                              userDto){

        UserDto userDto1=userService.updateUser(userDto,userId);
        return new ResponseEntity<>(userDto1,HttpStatus.OK);

    }

    //delete

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable String userId){
        userService.deleteUser(userId);
        ApiResponseMessage message= ApiResponseMessage.builder().message("User is deleted Successfully !!").success(true).status(HttpStatus.OK).build();
        return new ResponseEntity<>(message,HttpStatus.OK);
    }
    //getall


    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUser(@RequestParam(value="pageNumber", defaultValue="0", required =false) int pageNumber,
                                                    @RequestParam(value="pageSize", defaultValue="10", required =false) int pageSize,
                                                    @RequestParam(value="sortBy", defaultValue="name", required =false) String sortBy,
                                                    @RequestParam(value="sortDir", defaultValue="asc", required =false) String sortDir){
        return new ResponseEntity<>(userService.getAllUser(pageNumber,pageSize, sortBy,sortDir),HttpStatus.OK);

    }
    //getsingle

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable String userId){
        return new ResponseEntity<>(userService.getUserById(userId),HttpStatus.OK);

    }


    //get email
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        return new ResponseEntity<>(userService.getUserByEmail(email), HttpStatus.OK);

    }
        //search

        @GetMapping("/search/{keyword}")
        public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keyword){
            return new ResponseEntity<>(userService.searchUser(keyword), HttpStatus.OK);


    }

    @PostMapping("/images/{userId}")
    public ResponseEntity<ImageResponse> uploadUserImage(@RequestParam("userImage") MultipartFile image, @PathVariable String userId) throws IOException{
       String imageName= fileService.uploadFile(image,imageUploadPath);
       UserDto user = userService.getUserById(userId);
       user.setUserImage(imageName);
       UserDto userDto= userService.updateUser(user,userId);
       ImageResponse imageResponse =ImageResponse.builder().ImageName(imageName).success(true).message("image is uploaded succesfully ").status(HttpStatus.CREATED).build();
       return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);

    }

}
