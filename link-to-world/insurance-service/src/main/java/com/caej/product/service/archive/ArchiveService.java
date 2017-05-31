package com.caej.product.service.archive;

import java.util.List;
import java.util.Map;

import com.caej.product.api.archive.ArchiveElement;
import com.caej.product.api.archive.ArchiveField;
import com.caej.product.api.archive.ArchiveGroup;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.sited.form.Field;
import io.sited.form.Form;
import io.sited.util.JSON;

/**
 * @author miller
 */
public class ArchiveService {
    private final Map<String, ArchiveProvider> providers = Maps.newHashMap();

    public ArchiveService register(ArchiveProvider archiveProvider) {
        providers.put(archiveProvider.name(), archiveProvider);
        return this;
    }

    public List<ArchiveGroup> convertToArchive(Form form) {
        List<ArchiveGroup> list = Lists.newArrayList();
        form.groups.forEach(fieldGroup -> {
            ArchiveGroup archiveGroup = new ArchiveGroup();
            archiveGroup.name = fieldGroup.name;
            archiveGroup.displayName = fieldGroup.displayName;
            archiveGroup.groups = Lists.newArrayList();
            if (fieldGroup.multiple == null || !fieldGroup.multiple) {
                archiveGroup.groups.add(convertToElement(form.group(fieldGroup.name)));
            } else {
                Form.ListGroup listGroup = form.listGroup(fieldGroup.name);
                listGroup.forEach(group -> {
                    archiveGroup.groups.add(convertToElement(group));
                });
            }
            list.add(archiveGroup);
        });
        return list;
    }

    private ArchiveElement convertToElement(Form.Group group) {
        ArchiveElement archiveElement = new ArchiveElement();
        archiveElement.fields = Lists.newArrayList();
        for (String key : group.values.keySet()) {
            Field field = group.fieldGroup.field(key).get();
            ArchiveField archiveField = new ArchiveField();
            archiveField.name = field.name;
            archiveField.displayName = field.displayName;
            if (field.multiple != null && field.multiple) {
                archiveField.value = listValue(group, field.name, field.type.name());
            } else {
                archiveField.value = value(group, field.name, field.type.name());
            }
            archiveElement.fields.add(archiveField);
        }
        return archiveElement;
    }

    private String listValue(Form.Group group, String fieldName, String type) {
        ArchiveProvider archiveProvider = providers.get(type);
        if (archiveProvider == null) {
            List<String> list = group.listValue(fieldName);
            StringBuilder stringBuilder = new StringBuilder();
            list.forEach(string -> {
                stringBuilder.append(string);
                stringBuilder.append(' ');
            });
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            return stringBuilder.toString();
        } else {
            return archiveProvider.listValue(group, fieldName);
        }
    }

    private String value(Form.Group group, String fieldName, String type) {
        ArchiveProvider archiveProvider = providers.get(type);
        if (archiveProvider == null) {
            return JSON.convert(group.value(fieldName), String.class);
        } else {
            return archiveProvider.value(group, fieldName);
        }
    }
}
