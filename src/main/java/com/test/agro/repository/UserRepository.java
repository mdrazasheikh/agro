package com.test.agro.repository;

import com.test.agro.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private final MongoTemplate mongoTemplate;

    private static final String ID = "id";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String EMAIL = "email";
    private static final String ROLE = "role";
    private static final String ROLE_ID = "roleId";

    @Autowired
    public UserRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
        if (!mongoTemplate.collectionExists(UserEntity.class)) {
            mongoTemplate.createCollection(UserEntity.class);
        }
    }

    public UserEntity save(UserEntity userEntity) {
        if (userEntity.getId() == null || userEntity.getId().isEmpty()) {
            return mongoTemplate.save(userEntity);
        } else {
            Query query = new Query();
            query.addCriteria(Criteria.where(ID).is(userEntity.getId()));

            Update update = new Update();
            update.set(ID, userEntity.getId());
            update.set(USERNAME, userEntity.getUsername());
            update.set(PASSWORD, userEntity.getPassword());
            update.set(EMAIL, userEntity.getEmail());
            update.set(ROLE, userEntity.getRole());
            update.set(ROLE_ID, userEntity.getRoleId());

            mongoTemplate.upsert(query, update, UserEntity.class);

            return userEntity;
        }
    }

    public UserEntity get(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where(ID).is(id));

        return mongoTemplate.findOne(query, UserEntity.class);
    }

    public UserEntity getByUsernameAndPassword(String username, String password) {
        Query query = new Query();
        query.addCriteria(Criteria.where(USERNAME).is(username));
        query.addCriteria(Criteria.where(PASSWORD).is(password));

        return mongoTemplate.findOne(query, UserEntity.class);
    }
}
