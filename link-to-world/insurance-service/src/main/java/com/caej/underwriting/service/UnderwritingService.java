package com.caej.underwriting.service;

import org.apache.cxf.endpoint.Client;

import com.caej.api.underwritting.UnderwritingRequest;
import com.caej.api.underwritting.UnderwritingResponse;
import com.caej.api.util.CxfClient;
import com.caej.api.util.KdlinsMD5Util;
import com.caej.underwriting.UnderwritingOptions;

import io.sited.util.XML;

/**
 * @author miller
 */
public class UnderwritingService {
    private final Client client;
    private final UnderwritingOptions underwritingOptions;

    public UnderwritingService(UnderwritingOptions underwritingOptions) {
        this.underwritingOptions = underwritingOptions;
        this.client = CxfClient.getCxfWebserviceClient(underwritingOptions.underwritingWsdl);
    }

    public UnderwritingResponse callWebService(UnderwritingRequest underwritingRequest) throws Exception {
        String xmlString = XML.toXML(underwritingRequest);
        Object[] ret = client.invoke("handle", underwritingOptions.agentCode, KdlinsMD5Util.getCheckNo(underwritingOptions.agentCode, xmlString), xmlString);
        return XML.fromXML(ret[0].toString(), UnderwritingResponse.class);
    }
}
