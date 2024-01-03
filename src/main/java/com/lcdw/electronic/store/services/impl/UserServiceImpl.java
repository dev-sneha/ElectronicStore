package com.lcdw.electronic.store.services.impl;

import com.lcdw.electronic.store.dtos.UserDto;
import com.lcdw.electronic.store.entities.User;
import com.lcdw.electronic.store.repositories.UserRepository;
import com.lcdw.electronic.store.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;
    @Override
    public UserDto createUser(UserDto userDto) {
        String userId= UUID.randomUUID().toString();
        userDto.setUserId(userId);
        //dto--entity
        User user = dtoToEntity(userDto);
        User saveUser= userRepository.save(user);

        UserDto newDto= entityToDto(saveUser);


        return newDto;
    }



    @Override
    public UserDto updateUser(UserDto userDto, String userId) {

        User user=userRepository.findById(userId).orElseThrow(()-> new RuntimeException("userid not exist"));
       user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());
        user.setUserImage(userDto.getUserImage());
        user.setGender(userDto.getGender());

        User userUpdate= userRepository.save(user);
        UserDto updateDto= entityToDto(user);
        return updateDto;


    }

    @Override
    public void deleteUser(String userId) {
        User user=userRepository.findById(userId).orElseThrow(()-> new RuntimeException("userid not exist"));
        userRepository.delete(user);




    }

    @Override
    public List<UserDto> getAllUser(int pageNumber , int pageSize,String sortBy,String sortDir) {

        Sort sort= Sort.by(sortBy);
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);//i want the data according to the page size and number only not whole data is get
        Page<User> page= userRepository.findAll(pageable);
        List<User> users =page.getContent();
        List<UserDto> dtoList = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());

        return dtoList ;
    }

    @Override
    public UserDto getUserById(String userId) {
        User user=userRepository.findById(userId).orElseThrow(()-> new RuntimeException("userid not exist"));
        return entityToDto(user);



    }

    @Override
    public UserDto getUserByEmail(String email) {

        User user=userRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("email is not find in database"));
        return entityToDto(user);
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
        List<User> users =userRepository.findByNameContaining(keyword);
        List<UserDto> dtoList = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());



        return dtoList;
    }


    private UserDto entityToDto(User saveUser) {

//        UserDto userDto= UserDto.builder()
//                .userId(saveUser.getUserId())
//                .name(saveUser.getName())
//                .email(saveUser.getEmail())
//                .password(saveUser.getPassword())
//                .gender(saveUser.getGender())
//                .about(saveUser.getAbout())
//                .userImage(saveUser.getUserImage()).build();
        return mapper.map(saveUser,UserDto.class);
    }

    private User dtoToEntity(UserDto userDto) {

      /*  User user=User.builder().userId(userDto.getUserId())
                .name(userDto.getName())
                 .email(userDto.getEmail())
                .password(userDto.getPassword())
                .gender(userDto.getGender())
                .about(userDto.getAbout())
                .userImage(userDto.getUserImage()).build();*/

               return mapper.map(userDto, User.class);
    }
}
