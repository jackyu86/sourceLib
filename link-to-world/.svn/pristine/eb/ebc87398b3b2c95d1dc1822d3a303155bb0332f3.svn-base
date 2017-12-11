package com.caej.insurance.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.caej.insurance.domain.InsuranceFormGroup;
import com.caej.insurance.service.form.InstallInsuranceFormGroupRequest;
import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.google.common.io.Resources;

import io.sited.StandardException;
import io.sited.util.JSON;
import io.sited.util.Types;
/**
 * @author chi
 */
public class InstallInsuranceFormGroupService {
    private final Logger logger = LoggerFactory.getLogger(InstallInsuranceFormGroupService.class);

    @Inject
    InsuranceFormGroupService insuranceFormGroupService;

    @Inject
    InsuranceFormFieldService insuranceFormFieldService;

    public void installIfEmpty(String path) {
        if (!insuranceFormGroupService.findAll().isEmpty()) {
            return;
        }
        List<InstallInsuranceFormGroupRequest> requests = JSON.fromJSON(resource(path), Types.list(InstallInsuranceFormGroupRequest.class));
        requests.forEach(installInsuranceFormGroupRequest -> {
            logger.info("install form group, name={}", installInsuranceFormGroupRequest.name);
            InsuranceFormGroup insuranceFormGroup = insuranceFormGroup(installInsuranceFormGroupRequest);
            insuranceFormGroupService.insert(insuranceFormGroup);
            installInsuranceFormGroupRequest.fields.forEach(insuranceFormField -> insuranceFormFieldService.insert(insuranceFormField));

//            if (installInsuranceFormGroupRequest.name.equals("feature")) {
//                insuranceRiskProtectionService.findAll().forEach(insuranceRiskProtection -> {
//                    InsuranceFormField field = new InsuranceFormField();
//                    field.id = insuranceRiskProtection.id;
//                    field.name = insuranceRiskProtection.id.toHexString();
//                    field.displayName = insuranceRiskProtection.name;
//                    field.groupId = installInsuranceFormGroupRequest.id;
//                    field.createdTime = LocalDateTime.now();
//                    field.updatedTime = LocalDateTime.now();
//                    field.updatedBy = "init";
//                    field.createdBy = "init";
//                    insuranceFormFieldService.insert(field);
//                });
//            }
        });
    }

    private InsuranceFormGroup insuranceFormGroup(InstallInsuranceFormGroupRequest request) {
        InsuranceFormGroup instance = new InsuranceFormGroup();
        instance.displayOrder = request.displayOrder;
        instance.id = request.id;
        instance.name = request.name;
        instance.displayName = request.displayName;
        instance.required = request.required;
        instance.multiple = request.multiple;
        instance.description = request.description;
        instance.createdTime = request.createdTime;
        instance.createdBy = request.createdBy;
        instance.updatedTime = request.updatedTime;
        instance.updatedBy = request.updatedBy;
        return instance;
    }

    private String resource(String path) {
        try (Reader reader = new InputStreamReader(Resources.getResource(path).openStream(), Charsets.UTF_8)) {
            return CharStreams.toString(reader);
        } catch (IOException e) {
            throw new StandardException(e);
        }
    }
}
