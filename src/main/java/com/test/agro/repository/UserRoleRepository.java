package com.test.agro.repository;

import com.test.agro.entity.UserRoleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class UserRoleRepository {

    private final MongoTemplate mongoTemplate;

    private static final String ID = "id";
    private static final String TITLE = "title";

    @Autowired
    public UserRoleRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
        if (!mongoTemplate.collectionExists(UserRoleEntity.class)) {
            mongoTemplate.createCollection(UserRoleEntity.class);
        }
    }

    public UserRoleEntity save(UserRoleEntity userRoleEntity) {
        if (userRoleEntity.getId() == null || userRoleEntity.getId().isEmpty()) {
            return mongoTemplate.save(userRoleEntity);
        } else {
            Query query = new Query();
            query.addCriteria(Criteria.where(ID).is(userRoleEntity.getId()));

            Update update = new Update();
            update.set(ID, userRoleEntity.getId());
            update.set(TITLE, userRoleEntity.getTitle());

            mongoTemplate.upsert(query, update, UserRoleEntity.class);

            return userRoleEntity;
        }
    }

    public UserRoleEntity get(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where(ID).is(id));

        return mongoTemplate.findOne(query, UserRoleEntity.class);
    }
}
