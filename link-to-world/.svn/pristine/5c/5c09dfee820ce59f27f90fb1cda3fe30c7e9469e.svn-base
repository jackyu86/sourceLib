package com.caej.insurance.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.caej.insurance.api.enumtype.EnumTypeValueRequest;
import com.caej.insurance.api.enumtype.EnumValueResponse;
import com.google.common.collect.Lists;

import io.sited.db.MongoConfig;

/**
 * @author chi
 */
public class EnumValueService {
    @Inject
    MongoConfig mongoConfig;

    public EnumValueResponse create(String name, EnumTypeValueRequest request) {
        Document document = new Document("name", request.name)
            .append("value", request.value)
            .append("display_order", request.displayOrder);
        mongoConfig.db().getCollection(name).insertOne(document);
        EnumValueResponse response = new EnumValueResponse();
        response.name = request.name;
        response.value = request.value;
        response.id = document.getObjectId("_id").toHexString();
        response.displayOrder = request.displayOrder;
        return response;
    }

    public EnumValueResponse update(String name, String id, EnumTypeValueRequest request) {
        ObjectId objectId = new ObjectId(id);
        Document document = new Document("_id", objectId)
            .append("name", request.name)
            .append("value", request.value)
            .append("display_order", request.displayOrder);
        mongoConfig.db().getCollection(name).replaceOne(new Document("_id", objectId), document);
        EnumValueResponse response = new EnumValueResponse();
        response.name = request.name;
        response.value = request.value;
        response.id = document.getObjectId("_id").toHexString();
        response.displayOrder = request.displayOrder;
        return response;
    }

    public List<EnumValueResponse> values(String name) {
        ArrayList<Document> documents = mongoConfig.db().getCollection(name).find().sort(new Document("display_order", 1)).into(Lists.newArrayList());
        return documents.stream().map(document -> {
            EnumValueResponse response = new EnumValueResponse();
            response.id = document.getObjectId("_id").toHexString();
            response.name = document.getString("name");
            response.value = document.getString("value");
            response.displayOrder = document.getInteger("display_order");
            return response;
        }).collect(Collectors.toList());
    }

    public void delete(String name, String id) {
        mongoConfig.db().getCollection(name).deleteOne(new Document("_id", new ObjectId(id)));
    }
}
