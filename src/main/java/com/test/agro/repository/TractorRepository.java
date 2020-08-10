package com.test.agro.repository;

import com.test.agro.entity.TractorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TractorRepository {
    private final MongoTemplate mongoTemplate;

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String CREATED_BY = "createdBy";
    private static final String MODIFIED_BY = "modifiedBy";

    @Autowired
    public TractorRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
        if (!mongoTemplate.collectionExists(TractorEntity.class)) {
            mongoTemplate.createCollection(TractorEntity.class);
        }
    }

    public TractorEntity save(TractorEntity tractorEntity) {
        if (tractorEntity.getId() == null || tractorEntity.getId().isEmpty()) {
            return mongoTemplate.save(tractorEntity);
        } else {
            Query query = new Query();
            query.addCriteria(Criteria.where(ID).is(tractorEntity.getId()));

            Update update = new Update();
            update.set(ID, tractorEntity.getId());
            update.set(NAME, tractorEntity.getName());
            update.set(CREATED_BY, tractorEntity.getCreatedBy());
            update.set(MODIFIED_BY, tractorEntity.getModifiedBy());

            mongoTemplate.upsert(query, update, TractorEntity.class);

            return tractorEntity;
        }
    }

    public TractorEntity get(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where(ID).is(id));

        return mongoTemplate.findOne(query, TractorEntity.class);
    }

    public List<TractorEntity> getAll() {
        return mongoTemplate.findAll(TractorEntity.class);
    }
}
