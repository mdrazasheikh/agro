package com.test.agro.controller;

import com.test.agro.entity.*;
import com.test.agro.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;

@RestController
@RequestMapping("/mock")
public class MockDataController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserRoleRepository userRoleRepository;
    @Autowired
    FieldRepository fieldRepository;
    @Autowired
    TractorRepository tractorRepository;
    @Autowired
    FieldProcessingRepository fieldProcessingRepository;

    @PostMapping
    public void insert() {
        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setId("1");
        userRoleEntity.setTitle("admin");

        userRoleRepository.save(userRoleEntity);

        userRoleEntity = new UserRoleEntity();
        userRoleEntity.setId("2");
        userRoleEntity.setTitle("user");

        userRoleRepository.save(userRoleEntity);

        UserEntity userEntity = new UserEntity();
        userEntity.setId("1");
        userEntity.setUsername("admin_user");
        userEntity.setPassword("password");
        userEntity.setRole("admin");
        userEntity.setRoleId("1");

        userRepository.save(userEntity);

        userEntity = new UserEntity();
        userEntity.setId("2");
        userEntity.setUsername("user_user");
        userEntity.setPassword("password");
        userEntity.setRole("user");
        userEntity.setRoleId("2");

        userRepository.save(userEntity);

        FieldEntity fieldEntity = new FieldEntity();
        fieldEntity.setId("1");
        fieldEntity.setType("wheat");
        fieldEntity.setName("field_1");
        fieldEntity.setCreatedBy("2");
        fieldEntity.setArea(50.0);

        fieldRepository.save(fieldEntity);

        fieldEntity = new FieldEntity();
        fieldEntity.setId("2");
        fieldEntity.setType("broccoli");
        fieldEntity.setName("field_2");
        fieldEntity.setCreatedBy("2");
        fieldEntity.setArea(20.0);

        fieldRepository.save(fieldEntity);

        TractorEntity tractorEntity = new TractorEntity();
        tractorEntity.setId("1");
        tractorEntity.setName("tractor_1");
        tractorEntity.setCreatedBy("2");

        tractorRepository.save(tractorEntity);

        tractorEntity = new TractorEntity();
        tractorEntity.setId("2");
        tractorEntity.setName("tractor_2");
        tractorEntity.setCreatedBy("2");

        tractorRepository.save(tractorEntity);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 5);

        FieldProcessingEntity fieldProcessingEntity = new FieldProcessingEntity();
        fieldProcessingEntity.setId("1");
        fieldProcessingEntity.setFieldId("2");
        fieldProcessingEntity.setFieldName("field_2");
        fieldProcessingEntity.setTractorId("1");
        fieldProcessingEntity.setTractorName("tractor_1");
        fieldProcessingEntity.setCulture("broccoli");
        fieldProcessingEntity.setArea(20.0);
        fieldProcessingEntity.setIsProcessed(false);
        fieldProcessingEntity.setCreatedBy("2");
        fieldProcessingEntity.setDate(String.valueOf(calendar.getTime().toInstant().getEpochSecond()));

        fieldProcessingRepository.save(fieldProcessingEntity);

        fieldProcessingEntity = new FieldProcessingEntity();
        fieldProcessingEntity.setId("2");
        fieldProcessingEntity.setFieldId("1");
        fieldProcessingEntity.setFieldName("field_1");
        fieldProcessingEntity.setTractorId("1");
        fieldProcessingEntity.setTractorName("tractor_1");
        fieldProcessingEntity.setCulture("wheat");
        fieldProcessingEntity.setArea(20.0);
        fieldProcessingEntity.setIsProcessed(false);
        fieldProcessingEntity.setCreatedBy("2");
        fieldProcessingEntity.setDate(String.valueOf(calendar.getTime().toInstant().getEpochSecond()));

        fieldProcessingRepository.save(fieldProcessingEntity);
    }
}
