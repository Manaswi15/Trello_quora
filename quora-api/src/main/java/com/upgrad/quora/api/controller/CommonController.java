package com.upgrad.quora.api.controller;

import com.upgrad.quora.api.model.UserDetailsResponse;
import com.upgrad.quora.service.business.SignupBusinessService;
import com.upgrad.quora.service.business.UserAdminBusinessService;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@RestController
@RequestMapping("/")
public class CommonController {

    @Autowired
    private UserAdminBusinessService userAdminBusinessService;

    @RequestMapping(method = RequestMethod.GET, path = "/userprofile/{userId}",  produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserDetailsResponse> userProfile(@PathVariable("userId") final String userUuid,@RequestHeader("authorization") final String accessToken) throws AuthorizationFailedException, UserNotFoundException {

        final UserEntity userEntity = userAdminBusinessService.getUser(userUuid,accessToken);
        UserDetailsResponse userDetailsResponse = new UserDetailsResponse().firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName()).userName(userEntity.getUserName()).emailAddress(userEntity.getEmail())
                .country(userEntity.getCountry()).aboutMe(userEntity.getAboutMe()).dob(userEntity.getDob()).contactNumber(userEntity.getContactNumber());
        return new ResponseEntity<UserDetailsResponse>(userDetailsResponse, HttpStatus.OK);
    }

}
