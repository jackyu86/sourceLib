package io.sited.file.web;

import com.mongodb.client.MongoCollection;
import io.sited.SiteRule;
import io.sited.db.DBModule;
import io.sited.db.MongoConfig;
import io.sited.file.FileModuleImpl;
import io.sited.file.domain.Directory;
import io.sited.http.ServerResponse;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author chi
 */
public class DirectoryWebServiceImplTest {
    @Rule
    public final SiteRule site = new SiteRule(new FileModuleImpl());

    @Before
    public void setUp() throws Exception {
        MongoCollection<Directory> collection = site.module(DBModule.class).require(MongoConfig.class).db().getCollection("file.Directory", Directory.class);
        Directory directory = new Directory();
        directory.id = new ObjectId();
        collection.insertOne(directory);
        Directory levelOne1 = new Directory();
        levelOne1.id = new ObjectId();
        levelOne1.parentId = directory.id;
        collection.insertOne(levelOne1);
        Directory levelOne2 = new Directory();
        levelOne2.id = new ObjectId();
        levelOne2.parentId = directory.id;
        collection.insertOne(levelOne2);
        Directory levelTwo1 = new Directory();
        levelTwo1.id = new ObjectId();
        levelTwo1.parentId = levelOne1.id;
        collection.insertOne(levelTwo1);
        Directory levelTwo2 = new Directory();
        levelTwo2.id = new ObjectId();
        levelTwo2.parentId = levelOne2.id;
        collection.insertOne(levelTwo2);
        Directory levelTwo3 = new Directory();
        levelTwo3.id = new ObjectId();
        levelTwo3.parentId = levelOne2.id;
        collection.insertOne(levelTwo3);
    }

    @Test
    public void tree() throws Exception {
        ServerResponse response = site.get("/api/file/directory/tree").execute();
        Assert.assertEquals(200, response.statusCode());
    }

}