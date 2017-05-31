package com.caej.esb;

import com.caej.esb.api.ESBModule;
import com.caej.esb.api.ESBRecordWebService;
import com.caej.esb.domain.ESBRecord;
import com.caej.esb.service.ESBRecordService;
import com.caej.esb.web.ESBRecordWebServiceImpl;

import io.sited.ModuleInfo;
import io.sited.api.APIConfig;
import io.sited.api.APIModule;
import io.sited.db.DBModule;
import io.sited.db.MongoConfig;

/**
 * @author miller
 */
@ModuleInfo(name = "esb.api", require = {DBModule.class, APIModule.class})
public class ESBModuleImpl extends ESBModule {
    @Override
    protected void configure() throws Exception {
        require(MongoConfig.class)
            .entity(ESBRecord.class);
        bind(ESBRecordService.class);
        APIConfig apiConfig = require(APIConfig.class);
        apiConfig.service(ESBRecordWebService.class, ESBRecordWebServiceImpl.class);
    }
}
