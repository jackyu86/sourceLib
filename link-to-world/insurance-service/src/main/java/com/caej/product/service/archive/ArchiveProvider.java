package com.caej.product.service.archive;

import io.sited.form.Form;

/**
 * @author miller
 */
public interface ArchiveProvider {
    String name();

    String value(Form.Group group, String fieldName);

    String listValue(Form.Group group, String fieldName);
}
