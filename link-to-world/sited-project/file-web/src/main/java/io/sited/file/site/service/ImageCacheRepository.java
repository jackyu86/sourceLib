package io.sited.file.site.service;

import io.sited.file.api.impl.FolderepositoryImpl;

import java.io.File;

/**
 * @author chi
 */
public class ImageCacheRepository extends FolderepositoryImpl {
    public ImageCacheRepository(File dir) {
        super(dir);
    }
}
