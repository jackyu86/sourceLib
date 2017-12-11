package com.caej.product.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.inject.Inject;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.caej.product.api.product.AuditingProductAuditRequest;
import com.caej.product.api.product.AuditingProductQuery;
import com.caej.product.api.product.AuditingStatus;
import com.caej.product.api.product.ProductAdminRequest;
import com.caej.product.api.product.ProductRequest;
import com.caej.product.api.product.ProductVisibleType;
import com.caej.product.api.product.SearchProductRequest;
import com.caej.product.api.product.UpdateProductStatusRequest;
import com.caej.product.domain.AuditingProduct;
import com.caej.product.domain.Product;
import com.caej.product.domain.ProductStatus;
import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.io.CharStreams;
import com.google.common.io.Resources;
import com.mongodb.client.AggregateIterable;

import io.sited.StandardException;
import io.sited.db.FindView;
import io.sited.db.MongoRepository;
import io.sited.util.JSON;

/**
 * @author chi
 */
public class ProductService {
    private final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Inject
    MongoRepository<Product> repository;
    @Inject
    MongoRepository<AuditingProduct> auditingProductMongoRepository;

    public void installIfNotExists(String path) {
        ProductRequest request = JSON.fromJSON(resource(path), ProductRequest.class);
        if (!findByDisplayName(request.displayName).isPresent()) {
            logger.info("create product, name={}, path={}", request.displayName, path);
            Product product = new ProductBuilder(request).setVersion(0).build();
            repository.insert(product);
        }
    }

    private String resource(String path) {
        try (Reader reader = new InputStreamReader(Resources.getResource(path).openStream(), Charsets.UTF_8)) {
            return CharStreams.toString(reader);
        } catch (IOException e) {
            throw new StandardException(e);
        }
    }

    public Optional<Product> findByDisplayName(String displayName) {
        return repository.query("display_name", displayName).findOne();
    }

    public FindView<Product> search(SearchProductRequest request) {
        Document filter = new Document("status", ProductStatus.ACTIVE);
        filter.put("latest_version", true);
        filter.put("public_visible", new Document("$ne", ProductVisibleType.ONLY_DEALER));
        if (request.categoryIds != null && !request.categoryIds.isEmpty()) {
            filter.put("insurance_category_ids", new Document("$in", request.categoryIds));
        }
        return repository.query(filter)
            .page(request.page).limit(request.limit)
            .sort("display_order", true)
            .find();
    }

    public List<Product> list(ProductStatus status) {
        Document filter = new Document("latest_version", true);
        if (status != null) {
            filter.put("status", status);
        }
        return repository.query(filter).findMany();
    }

    public Product get(ObjectId id) {
        return repository.query(new Document("_id", id).append("status", ProductStatus.ACTIVE)).findOne().get();
    }

    public Product getById(ObjectId id) {
        return repository.query(new Document("_id", id)).findOne().get();
    }

    public Optional<Product> getByName(String name) {
        return repository.query(new Document("name", name).append("status", ProductStatus.ACTIVE).append("latest_version", true)).findOne();
    }

    public Optional<Product> getByNameIgnoreStatus(String name) {
        return repository.query(new Document("name", name).append("latest_version", true)).findOne();
    }

    public Optional<Product> getByNameAndVersion(String name, Integer version) {
        return repository.query(new Document("name", name).append("version", version)).findOne();
    }

    public void update(ObjectId id, Product product, String requestBy) {
        product.updatedTime = LocalDateTime.now();
        product.updatedBy = requestBy;
        repository.update(id, product);
    }

    public void insert(Product product) {
        repository.insert(product);
    }

    public FindView<Product> batchGet(FindView<String> productNames) {
        Document filter = new Document("status", ProductStatus.ACTIVE);
        filter.put("name", new Document("$in", productNames));
        return repository.query(filter)
            .page(productNames.page).limit(productNames.limit)
            .find();
    }

    public Set<String> categorySet() {
        List<Document> pipeline = Lists.newArrayList();
        pipeline.add(new Document("$match", new Document("latest_version", true)));
        pipeline.add(new Document("$project", new Document("insurance_category_ids", 1)));
        pipeline.add(new Document("$group", new Document("_id", new Document("insurance_category_ids", "$insurance_category_ids"))));
        AggregateIterable<Document> aggregate = repository.collection().aggregate(pipeline, Document.class);
        Set<String> set = Sets.newHashSet();
        for (Document row : aggregate) {
            Map<String, List<String>> map = JSON.fromJSON(JSON.toJSON(row.get("_id")), Map.class);
            set.addAll(map.get("insurance_category_ids"));
        }
        return set;
    }

    public long countActiveInsuranceClause(ObjectId insuranceClauseId) {
        return repository.query(new Document("insurance_clause_ids", new Document("$in", Lists.newArrayList(insuranceClauseId)))).count();
    }

    public long countActiveInsuranceClaim(ObjectId insuranceClaimId) {
        return repository.query(new Document("insurance_claim_ids", new Document("$in", Lists.newArrayList(insuranceClaimId)))).count();
    }

    public FindView<Product> findAdmin(ProductAdminRequest query) {
        Document filter = new Document();
        if (query.displayName != null) {
            filter.put("display_name", new Document("$regex", query.displayName));
        }
        if (query.latestVersion != null) {
            filter.put("latest_version", query.latestVersion);
        }
        if (query.status != null) {
            filter.put("status", query.status);
        }
        if (query.order != null) {
            return repository.query(filter).sort(query.order, query.desc).page(query.page).limit(query.limit).find();
        }
        return repository.query(filter).page(query.page).limit(query.limit).find();
    }

    public void updateProductStatus(ObjectId id, UpdateProductStatusRequest request) {
        Product product = repository.get(id);
        product.status = request.status;
        product.updatedTime = LocalDateTime.now();
        product.updatedBy = request.requestBy;
        repository.update(id, product);
    }

    public AuditingProduct createAudit(Product product) {
        AuditingProduct auditingProduct = new AuditingProduct();
        auditingProduct.product = product;
        auditingProduct.productDisplayName = product.displayName;
        auditingProduct.version = product.version + 1;
        auditingProduct.createdBy = product.createdBy;
        auditingProduct.createdTime = LocalDateTime.now();
        auditingProduct.status = AuditingStatus.PENDING;
        auditingProductMongoRepository.insert(auditingProduct);
        return auditingProduct;
    }

    public FindView<AuditingProduct> auditList(AuditingProductQuery query) {
        Document filter = new Document();
        if (query.status != null) {
            filter.put("status", query.status);
        }
        if (!Strings.isNullOrEmpty(query.productDisplayName)) {
            filter.put("product_display_name", new Document("$regex", query.productDisplayName));
        }
        if (query.order != null) {
            return auditingProductMongoRepository.query(filter).sort(query.order, query.desc).page(query.page).limit(query.limit).find();
        }
        return auditingProductMongoRepository.query(filter).page(query.page).limit(query.limit).find();
    }

    public void deleteAudit(ObjectId id) {
        auditingProductMongoRepository.delete(id);
    }

    public AuditingProduct updateAuditProduct(ObjectId id, AuditingProductAuditRequest request) {
        AuditingProduct auditingProduct = getAudit(id);
        auditingProduct.status = request.status;
        auditingProduct.comment = request.comment;
        auditingProduct.auditedTime = LocalDateTime.now();
        auditingProduct.auditedBy = request.requestBy;
        auditingProductMongoRepository.update(id, auditingProduct);
        return auditingProduct;
    }

    public AuditingProduct getAudit(ObjectId id) {
        return auditingProductMongoRepository.get(id);
    }
}
