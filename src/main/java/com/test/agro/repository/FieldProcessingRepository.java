package com.test.agro.repository;

import com.mongodb.client.result.UpdateResult;
import com.test.agro.entity.AreaAggregatorEntity;
import com.test.agro.entity.FieldProcessingEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.query.UpdateDefinition;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Repository
public class FieldProcessingRepository {

    MongoTemplate mongoTemplate;

    private final static String ID = "id";
    private final static String TRACTOR_ID = "tractorId";
    private final static String TRACTOR_NAME = "tractorName";
    private final static String FIELD_ID = "fieldId";
    private final static String FIELD_NAME = "fieldName";
    private final static String CULTURE = "culture";
    private final static String AREA = "area";
    private final static String DATE = "date";
    private final static String IS_PROCESSED = "isProcessed";
    private final static String CREATED_BY = "createdBy";
    private final static String MODIFIED_BY = "modifiedBy";

    @Autowired
    public FieldProcessingRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
        if (!mongoTemplate.collectionExists(FieldProcessingEntity.class)) {
            mongoTemplate.createCollection(FieldProcessingEntity.class);
        }
    }

    public FieldProcessingEntity save(FieldProcessingEntity fieldProcessingEntity) {
        if (fieldProcessingEntity.getId() == null || fieldProcessingEntity.getId().isEmpty()) {
            return mongoTemplate.save(fieldProcessingEntity);
        } else {
            Query query = new Query();
            query.addCriteria(Criteria.where(ID).is(fieldProcessingEntity.getId()));

            Update update = new Update();
            update.set(ID, fieldProcessingEntity.getId());
            update.set(FIELD_ID, fieldProcessingEntity.getFieldId());
            update.set(FIELD_NAME, fieldProcessingEntity.getFieldName());
            update.set(TRACTOR_ID, fieldProcessingEntity.getTractorId());
            update.set(TRACTOR_NAME, fieldProcessingEntity.getTractorName());
            update.set(CULTURE, fieldProcessingEntity.getCulture());
            update.set(AREA, fieldProcessingEntity.getArea());
            update.set(DATE, fieldProcessingEntity.getDate());
            update.set(IS_PROCESSED, fieldProcessingEntity.getIsProcessed());
            update.set(CREATED_BY, fieldProcessingEntity.getCreatedBy());
            update.set(MODIFIED_BY, fieldProcessingEntity.getModifiedBy());

            mongoTemplate.upsert(query, update, FieldProcessingEntity.class);

            return fieldProcessingEntity;
        }
    }

    public AreaAggregatorEntity getTotalAreaByFieldIdDateIsProcessed(String fieldId, String date) {
        Criteria criteria = new Criteria().and(FIELD_ID).is(fieldId)
                .and(DATE).is(date)
                .and(IS_PROCESSED).is(true);
        Aggregation aggregation = Aggregation.newAggregation(Aggregation.match(criteria),
                Aggregation.group().sum("area").as("totalArea"));

        AggregationResults<AreaAggregatorEntity> aggregationResults = mongoTemplate.aggregate(aggregation, FieldProcessingEntity.class, AreaAggregatorEntity.class);

        Optional<AreaAggregatorEntity> optional = aggregationResults.getMappedResults().stream().findFirst();
        if (optional.isPresent()) {
            return optional.get();
        }

        return new AreaAggregatorEntity(0.0);
    }

    public Boolean isTractorAllocatedForDate(String tractorId, String date) {
        Criteria criteria = new Criteria().and(TRACTOR_ID).is(tractorId)
                .and(DATE).is(date)
                .and(IS_PROCESSED).is(true);
        Query query = new Query();
        query.addCriteria(criteria);

        return mongoTemplate.exists(query, FieldProcessingEntity.class);
    }

    public List<FieldProcessingEntity> getAll() {
        return mongoTemplate.findAll(FieldProcessingEntity.class);
    }

    public FieldProcessingEntity get(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where(ID).is(id));

        return mongoTemplate.findOne(query, FieldProcessingEntity.class);
    }

    public FieldProcessingEntity markProcessed(FieldProcessingEntity fieldProcessingEntity) {
        Query query = new Query();
        query.addCriteria(Criteria.where(ID).is(fieldProcessingEntity.getId()));

        Update update = new Update();
        update.set(IS_PROCESSED, true);

        mongoTemplate.updateFirst(query, update, FieldProcessingEntity.class);

        return get(fieldProcessingEntity.getId());
    }

    public List<FieldProcessingEntity> filter(String fieldName, String tractorName, String date, String culture) {
        Query query = new Query();
        if (fieldName != null && !fieldName.isEmpty()) {
            query.addCriteria(Criteria.where(FIELD_NAME).is(fieldName));
        }
        if (tractorName != null && !tractorName.isEmpty()) {
            query.addCriteria(Criteria.where(TRACTOR_NAME).is(tractorName));
        }
        if (date != null && !date.isEmpty()) {
            query.addCriteria(Criteria.where(DATE).is(date));
        }
        if (culture != null && !culture.isEmpty()) {
            query.addCriteria(Criteria.where(CULTURE).is(culture));
        }

        query.addCriteria(Criteria.where(IS_PROCESSED).is(true));

        query.fields().exclude(ID);
        query.fields().exclude(FIELD_ID);
        query.fields().exclude(TRACTOR_ID);
        query.fields().exclude(CREATED_BY);
        query.fields().exclude(MODIFIED_BY);
        query.fields().exclude(IS_PROCESSED);

        return mongoTemplate.find(query, FieldProcessingEntity.class);
    }

    public AreaAggregatorEntity aggregateProcessedArea(String fieldName, String tractorName, String date, String culture) {
        Criteria criteria = new Criteria();
        if (fieldName != null && !fieldName.isEmpty()) {
            criteria.and(FIELD_NAME).is(fieldName);
        }
        if (tractorName != null && !tractorName.isEmpty()) {
            criteria.and(TRACTOR_NAME).is(tractorName);
        }
        if (date != null && !date.isEmpty()) {
            criteria.and(DATE).is(date);
        }
        if (culture != null && !culture.isEmpty()) {
            criteria.and(CULTURE).is(culture);
        }

        criteria.and(IS_PROCESSED).is(true);

        Aggregation aggregation = Aggregation.newAggregation(Aggregation.match(criteria),
                Aggregation.group().sum("area").as("totalArea"));

        AggregationResults<AreaAggregatorEntity> aggregationResults = mongoTemplate.aggregate(aggregation, FieldProcessingEntity.class, AreaAggregatorEntity.class);

        Optional<AreaAggregatorEntity> optional = aggregationResults.getMappedResults().stream().findFirst();
        if (optional.isPresent()) {
            return optional.get();
        }

        return new AreaAggregatorEntity(0.0);
    }
}