package com.caej.product.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Test;

import com.caej.product.domain.Product;
import com.caej.product.domain.ProductQuestion;
import com.google.common.collect.Lists;

/**
 * @author chi
 */
public class ProductDifferTest {
    @Test
    public void differ() {
        Product original = new Product();
        original.name = "some";

        List<ProductFieldChange> changes = new ProductDiffer().diff(original, new Product());
        Assert.assertEquals(1, changes.size());
        Assert.assertEquals("product.name", changes.get(0).field);
    }

    @Test
    public void list() {
        Product original = new Product();
        original.insuranceClaimIds = Lists.newArrayList(new ObjectId(), new ObjectId());

        Product value = new Product();
        value.insuranceClaimIds = Lists.newArrayList(new ObjectId());

        List<ProductFieldChange> changes = new ProductDiffer().diff(original, value);
        Assert.assertEquals(2, changes.size());
        Assert.assertEquals("product.insuranceClaimIds[0]", changes.get(0).field);
        Assert.assertEquals("UPDATE", changes.get(0).type);
    }

    @Test
    public void object() {
        Product original = new Product();
        ProductQuestion productQuestion = new ProductQuestion();
        productQuestion.question = "question";
        productQuestion.answer = "answer";
        original.questions = Lists.newArrayList(productQuestion);

        Product value = new Product();
        ProductQuestion productQuestion2 = new ProductQuestion();
        productQuestion2.question = "question2";
        productQuestion2.answer = "answer2";
        value.questions = Lists.newArrayList(productQuestion2);

        List<ProductFieldChange> changes = new ProductDiffer().diff(original, value);
        Assert.assertEquals(2, changes.size());
        Assert.assertEquals("product.questions[0].question", changes.get(0).field);
        Assert.assertEquals("UPDATE", changes.get(0).type);
    }
}