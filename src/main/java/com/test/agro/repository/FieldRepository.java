package com.test.agro.repository;

import com.test.agro.entity.FieldEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FieldRepository {
    private final MongoTemplate mongoTemplate;

    private static final String NAME = "name";
    private static final String ID = "id";
    private static final String TYPE = "type";
    private static final String AREA = "area";
    private static final String CREATED_BY = "createdBy";
    private static final String MODIFIED_BY = "modifiedBy";

    @Autowired
    public FieldRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
        if (!mongoTemplate.collectionExists(FieldEntity.class)) {
            mongoTemplate.createCollection(FieldEntity.class);
        }
    }

    public FieldEntity save(FieldEntity fieldEntity) {
        if (fieldEntity.getId() == null || fieldEntity.getId().isEmpty()) {
            return mongoTemplate.save(fieldEntity);
        } else {
            Query query = new Query();
            query.addCriteria(Criteria.where(ID).is(fieldEntity.getId()));

            Update update = new Update();
            update.set(ID, fieldEntity.getId());
            update.set(NAME, fieldEntity.getName());
            update.set(TYPE, fieldEntity.getType());
            update.set(AREA, fieldEntity.getArea());
            update.set(CREATED_BY, fieldEntity.getCreatedBy());
            update.set(MODIFIED_BY, fieldEntity.getModifiedBy());

            mongoTemplate.upsert(query, update, FieldEntity.class);

            return fieldEntity;
        }
    }

    public FieldEntity get(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where(ID).is(id));

        return mongoTemplate.findOne(query, FieldEntity.class);
    }

    public List<FieldEntity> getByCreatedBy(String createdBy) {
        Query query = new Query();
        query.addCriteria(Criteria.where(CREATED_BY).is(createdBy));

        return mongoTemplate.find(query, FieldEntity.class);
    }

    public List<FieldEntity> getAll() {
        return mongoTemplate.findAll(FieldEntity.class);
    }
}
