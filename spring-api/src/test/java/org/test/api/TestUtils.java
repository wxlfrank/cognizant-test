package org.test.api;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.util.Assert;

public class TestUtils {
    public static byte[] readFile(Class<?> clazz, String filePath) {
        URL resourceURL = clazz.getResource(filePath);
        Assert.notNull(resourceURL, filePath + " does not exist");
        try {
            return Files.readAllBytes(Paths.get(resourceURL.toURI()));
        } catch (IOException | URISyntaxException e) {
            Assert.notNull(null, "error happens when reading " + filePath + e.getMessage());
            return null;
        }
    }
}
