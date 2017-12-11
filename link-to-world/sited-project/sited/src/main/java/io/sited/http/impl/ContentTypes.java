package io.sited.http.impl;

import com.google.common.io.Files;
import io.undertow.util.MimeMappings;

import java.io.File;

/**
 * @author chi
 */
public class ContentTypes {
    private static MimeMappings mimeMappings;

    static {
        mimeMappings = MimeMappings.builder()
            .addMapping("mp3", "audio/mp3")
            .addMapping("mp4", "video/mp4")
            .addMapping("svg", "image/svg+xml")
            .addMapping("ttf", "application/x-font-ttf")
            .addMapping("otf", "application/x-font-opentype")
            .addMapping("woff", "application/font-woff")
            .addMapping("woff2", "application/font-woff2")
            .addMapping("eot", "application/vnd.ms-fontobject")
            .addMapping("sfnt", "application/font-sfnt")
            .build();
    }

    public static String of(File file) {
        return of(file.getName());
    }

    public static String of(String path) {
        String mimeType = mimeMappings.getMimeType(Files.getFileExtension(path));
        return mimeType == null ? "application/octet-stream" : mimeType;
    }
}
