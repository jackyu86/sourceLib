package io.sited.file.site.web;

import com.google.common.io.Files;
import io.sited.file.api.FileConfig;
import io.sited.file.site.service.ImageCacheRepository;
import io.sited.file.site.service.ImageScalar;
import io.sited.file.site.service.ImageSize;
import io.sited.http.GET;
import io.sited.http.Path;
import io.sited.http.Request;
import io.sited.http.Response;
import io.sited.http.exception.NotFoundException;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author chi
 */
public class ImageController {
    private static final Pattern IMAGE_PATTERN = Pattern.compile("/image/([^/]*)/(.*)");

    @Inject
    ImageScalar imageScalar;

    @Inject
    FileConfig fileConfig;

    @Inject
    ImageCacheRepository imageCacheRepository;

    @Path("/image/*")
    @GET
    public Response image(Request request) throws IOException {
        Matcher matcher = IMAGE_PATTERN.matcher(request.path());
        if (!matcher.matches()) {
            throw new NotFoundException(request.path());
        }

        ImageSize imageSize = ImageSize.of(matcher.group(1));
        String path = matcher.group(2).substring("file".length());
        String imagePath = cacheKey(path, imageSize);

        Optional<InputStream> inputStream = imageCacheRepository.get(imagePath);

        if (!inputStream.isPresent()) {
            inputStream = fileConfig.repository().get(path);
            if (!inputStream.isPresent()) {
                throw new NotFoundException("missing file, path={}", path);
            }
            try (InputStream imageInputStream = imageScalar.scale(inputStream.get(), imageSize, Files.getFileExtension(path))) {
                imageCacheRepository.put(imagePath, imageInputStream);
            }
            inputStream = imageCacheRepository.get(imagePath);
        }

        if (!inputStream.isPresent()) {
            throw new NotFoundException("missing file, path={}", path);
        }

        return Response.body(inputStream.get());
    }

    private String cacheKey(String path, ImageSize imageSize) {
        return "/image/" + path + imageSize.toString() + "." + Files.getFileExtension(path);
    }
}
