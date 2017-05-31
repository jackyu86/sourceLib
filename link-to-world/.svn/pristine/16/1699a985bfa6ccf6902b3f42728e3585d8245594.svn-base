package io.sited.file.site.service;

import com.google.common.base.Preconditions;

/**
 * @author chi
 */
public class ImageSize {
    public int width;
    public int height;

    public static ImageSize of(String size) {
        if (size.startsWith("x")) {
            ImageSize imageSize = new ImageSize();
            imageSize.width = 0;
            imageSize.height = Integer.parseInt(size.substring(1));
            return imageSize;
        } else if (size.endsWith("x")) {
            ImageSize imageSize = new ImageSize();
            imageSize.width = Integer.parseInt(size.substring(0, size.length() - 1));
            imageSize.height = 0;
            return imageSize;
        } else {
            int p = size.indexOf('x');
            Preconditions.checkState(p > 0, "invalid image size %s", size);
            ImageSize imageSize = new ImageSize();
            imageSize.width = Integer.parseInt(size.substring(0, p));
            imageSize.height = Integer.parseInt(size.substring(p + 1, size.length()));
            return imageSize;
        }
    }

    @Override
    public String toString() {
        return width + "x" + height;
    }
}
