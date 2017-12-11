package com.caej.insurance.web;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.caej.insurance.api.InsuranceCategoryWebService;
import com.caej.insurance.api.category.BatchInsuranceCategoryRequest;
import com.caej.insurance.api.category.InsuranceCategoryNodeResponse;
import com.caej.insurance.api.category.InsuranceCategoryQuery;
import com.caej.insurance.api.category.InsuranceCategoryRequest;
import com.caej.insurance.api.category.InsuranceCategoryResponse;
import com.caej.insurance.domain.InsuranceCategory;
import com.caej.insurance.service.InsuranceCategoryService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.sited.db.FindView;
import io.sited.http.PathParam;

/**
 * @author chi
 */
public class InsuranceCategoryWebServiceImpl implements InsuranceCategoryWebService {
    private final Logger logger = LoggerFactory.getLogger(InsuranceCategoryWebServiceImpl.class);

    @Inject
    InsuranceCategoryService insuranceCategoryService;

    @Override
    public FindView<InsuranceCategoryResponse> find(InsuranceCategoryQuery query) {
        return FindView.map(insuranceCategoryService.find(query), this::response);
    }

    @Override
    public List<InsuranceCategoryResponse> firstLevel() {
        return insuranceCategoryService.findFirstLevel().stream().map(this::response).collect(Collectors.toList());
    }

    @Override
    public List<InsuranceCategoryResponse> children(ObjectId id) {
        return insuranceCategoryService.findChildrenById(id).stream().map(this::response).collect(Collectors.toList());
    }

    @Override
    public List<InsuranceCategoryNodeResponse> tree() {
        List<InsuranceCategory> categories = insuranceCategoryService.findAll();
        Map<ObjectId, InsuranceCategoryNodeResponse> index = Maps.newHashMap();
        List<InsuranceCategoryNodeResponse> firstLevels = Lists.newArrayList();
        tree(categories, index, firstLevels);
        return firstLevels;
    }

    @Override
    public List<InsuranceCategoryResponse> list() {
        return insuranceCategoryService.findAll().stream().map(this::response).collect(Collectors.toList());
    }

    @Override
    public InsuranceCategoryResponse get(@PathParam("id") String id) {
        return response(insuranceCategoryService.get(new ObjectId(id)));
    }

    @Override
    public InsuranceCategoryResponse create(InsuranceCategoryRequest request) {
        return response(insuranceCategoryService.create(request));
    }

    @Override
    public InsuranceCategoryResponse update(@PathParam("id") String id, InsuranceCategoryRequest request) {
        return response(insuranceCategoryService.update(new ObjectId(id), request));
    }

    @Override
    public void delete(@PathParam("id") String id) {
        insuranceCategoryService.delete(new ObjectId(id));
    }

    @Override
    public List<InsuranceCategoryNodeResponse> childrenTree(BatchInsuranceCategoryRequest request) {
        List<InsuranceCategory> categories = insuranceCategoryService.batch(request.categoryIds);
        Map<ObjectId, InsuranceCategoryNodeResponse> index = Maps.newHashMap();
        List<InsuranceCategoryNodeResponse> firstLevels = Lists.newArrayList();
        tree(categories, index, firstLevels);
        return firstLevels;
    }

    @Override
    public List<InsuranceCategoryResponse> firstLevel(BatchInsuranceCategoryRequest request) {
        List<InsuranceCategory> categories = insuranceCategoryService.batch(request.categoryIds);
        List<InsuranceCategoryResponse> result = Lists.newArrayList();
        categories.forEach(category -> {
            if (category.parentId == null) result.add(response(category));
        });
        result.sort((InsuranceCategoryResponse o1, InsuranceCategoryResponse o2) -> o1.displayOrder.compareTo(o2.displayOrder));
        return result;
    }

    @Override
    public List<InsuranceCategoryResponse> batch(BatchInsuranceCategoryRequest request) {
        return insuranceCategoryService.batch(request.categoryIds).stream().map(this::response).collect(Collectors.toList());
    }

    private void tree(List<InsuranceCategory> categories, Map<ObjectId, InsuranceCategoryNodeResponse> index, List<InsuranceCategoryNodeResponse> firstLevels) {
        categories.forEach(category -> {
            InsuranceCategoryNodeResponse node = new InsuranceCategoryNodeResponse();
            node.category = response(category);
            node.children = Lists.newArrayList();

            if (category.parentId == null) {
                firstLevels.add(node);
            }
            index.put(category.id, node);
        });

        categories.forEach(category -> {
            if (category.parentId != null) {
                InsuranceCategoryNodeResponse parent = index.get(category.parentId);
                if (parent == null) {
                    logger.info("missing parent category, id={}, parentId={}", category.id, category.parentId);
                } else {
                    parent.children.add(index.get(category.id));
                }
            }
        });
    }

    private InsuranceCategoryResponse response(InsuranceCategory category) {
        InsuranceCategoryResponse response = new InsuranceCategoryResponse();
        response.id = category.id;
        response.parentId = category.parentId;
        response.name = category.name;
        response.displayOrder = category.displayOrder;
        response.recommended = category.recommended;
        response.description = category.description;
        response.createdTime = category.createdTime;
        response.createdBy = category.createdBy;
        response.updatedTime = category.updatedTime;
        response.updatedBy = category.updatedBy;
        return response;
    }
}
