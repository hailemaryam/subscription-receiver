package com.hmmk.dataSync;

import com.hmmk.model.PhoneList;
import jakarta.jws.WebService;
import jakarta.transaction.Transactional;
import jakarta.xml.ws.Holder;
import lombok.extern.java.Log;
import org.csapi.schema.parlayx.data.v1_0.NamedParameterList;
import org.csapi.schema.parlayx.data.v1_0.UserID;
import org.csapi.wsdl.parlayx.data.sync.v1_0._interface.DataSync;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;


@WebService(serviceName = "DataSyncService")
@Log
public class SdpSyncImpl implements DataSync {
    private static final Logger log = LoggerFactory.getLogger(SdpSyncImpl.class);
    @RestClient
    NotifyRestClient restClient;

    @Override
    @Transactional
    public void syncOrderRelation(UserID userID, String spID, String productID, String serviceID, String serviceList, int updateType, String updateTime, String updateDesc, String effectiveTime, String expiryTime, Holder<NamedParameterList> extensionInfo, Holder<Integer> result, Holder<String> resultDescription) {
        saveToDb(userID, productID, serviceID, updateType);
        try {
            String response = restClient.notify(updateType == 1 ? "ok" : "stop", userID.getID());
            log.info(response);
            int resultValue = 0;
            result.value = resultValue;
            resultDescription.value = response;
        } catch (java.lang.Exception ex) {
            log.error(ex.toString());
        }
    }

    private static void saveToDb(UserID userID, String productID, String serviceID, int updateType) {
        PhoneList phoneList = new PhoneList();
        phoneList.phone = userID.getID();
        phoneList.serviceId = serviceID;
        phoneList.productId = productID;
        phoneList.customerSegmentGroup = "default";
        phoneList.status = updateType == 1;
        phoneList.updateTime = Instant.now();
        phoneList.persist();
    }
}
