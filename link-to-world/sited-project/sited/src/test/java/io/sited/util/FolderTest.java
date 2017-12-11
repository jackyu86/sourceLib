package io.sited.util;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

/**
 * @author chi
 */
public class FolderTest {
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void delete() throws IOException {
        File dir = temporaryFolder.newFolder("test");
        File file = new File(dir, "test1");
        Folder folder = new Folder(dir);
        folder.delete();

        Assert.assertFalse(file.exists());
    }
}