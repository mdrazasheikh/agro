package com.test.agro;

import com.test.agro.entity.*;
import com.test.agro.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
public class RepositoryTest {
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

    @Test
    public void createUserTest() {
        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setId("1");
        userRoleEntity.setTitle("admin");

        assert userRoleEntity.equals(userRoleRepository.save(userRoleEntity));
        assert userRoleEntity.equals(userRoleRepository.get("1"));

        userRoleEntity = new UserRoleEntity();
        userRoleEntity.setId("2");
        userRoleEntity.setTitle("user");

        assert userRoleEntity.equals(userRoleRepository.save(userRoleEntity));
        assert userRoleEntity.equals(userRoleRepository.get("2"));

        UserEntity userEntity = new UserEntity();
        userEntity.setId("1");
        userEntity.setUsername("admin_user");
        userEntity.setPassword("password");
        userEntity.setRole("admin");
        userEntity.setRoleId("1");

        assert userEntity.equals(userRepository.save(userEntity));
        assert userEntity.equals(userRepository.get("1"));

        userEntity = new UserEntity();
        userEntity.setId("2");
        userEntity.setUsername("user_user");
        userEntity.setPassword("password");
        userEntity.setRole("user");
        userEntity.setRoleId("2");

        assert userEntity.equals(userRepository.save(userEntity));
        assert userEntity.equals(userRepository.get("2"));
    }

    @Test
    public void createFieldTest() {
        FieldEntity fieldEntity = new FieldEntity();
        fieldEntity.setId("1");
        fieldEntity.setType("wheat");
        fieldEntity.setName("field_1");
        fieldEntity.setCreatedBy("2");
        fieldEntity.setArea(50.0);

        assert fieldEntity.equals(fieldRepository.save(fieldEntity));
        assert fieldEntity.equals(fieldRepository.get("1"));

        fieldEntity = new FieldEntity();
        fieldEntity.setId("2");
        fieldEntity.setType("broccoli");
        fieldEntity.setName("field_2");
        fieldEntity.setCreatedBy("2");
        fieldEntity.setArea(20.0);

        assert fieldEntity.equals(fieldRepository.save(fieldEntity));
        assert fieldEntity.equals(fieldRepository.get("2"));
    }

    @Test
    public void createTractorTest() {
        TractorEntity tractorEntity = new TractorEntity();
        tractorEntity.setId("1");
        tractorEntity.setName("tractor_1");
        tractorEntity.setCreatedBy("2");

        assert tractorEntity.equals(tractorRepository.save(tractorEntity));
        assert tractorEntity.equals(tractorRepository.get("1"));

        tractorEntity = new TractorEntity();
        tractorEntity.setId("2");
        tractorEntity.setName("tractor_2");
        tractorEntity.setCreatedBy("2");

        assert tractorEntity.equals(tractorRepository.save(tractorEntity));
        assert tractorEntity.equals(tractorRepository.get("2"));
        assertThat(tractorRepository.get("3")).isNull();
    }

    @Test
    public void createFieldProcessRequest() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 5);

        FieldProcessingEntity fieldProcessingEntity = new FieldProcessingEntity();
        fieldProcessingEntity.setId("1");
        fieldProcessingEntity.setFieldId("1");
        fieldProcessingEntity.setFieldName("field_1");
        fieldProcessingEntity.setTractorId("1");
        fieldProcessingEntity.setTractorName("tractor_1");
        fieldProcessingEntity.setCulture("wheat");
        fieldProcessingEntity.setArea(20.0);
        fieldProcessingEntity.setIsProcessed(false);
        fieldProcessingEntity.setCreatedBy("2");
        fieldProcessingEntity.setDate(String.valueOf(calendar.getTime().toInstant().getEpochSecond()));

        assert fieldProcessingEntity.equals(fieldProcessingRepository.save(fieldProcessingEntity));
        assertThat(fieldProcessingRepository.get("1")).isEqualTo(fieldProcessingEntity);
    }
}
