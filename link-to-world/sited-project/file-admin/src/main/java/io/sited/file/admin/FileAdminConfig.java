package io.sited.file.admin;


import java.util.function.Function;

/**
 * @author chi
 */
public interface FileAdminConfig {
    Function<String, String> pathConverter();
}
