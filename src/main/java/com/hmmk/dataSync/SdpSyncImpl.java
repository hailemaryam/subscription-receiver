package com.hmmk.dataSync;

import com.hmmk.DTO.WebServiceQueueItem;
import com.hmmk.model.PhoneList;
import com.hmmk.service.NotifyRestClient;
import com.hmmk.service.PublishNewSub;
import jakarta.inject.Inject;
import jakarta.jws.WebService;
import jakarta.transaction.Transactional;
import jakarta.xml.ws.Holder;
import org.csapi.schema.parlayx.data.v1_0.NamedParameterList;
import org.csapi.schema.parlayx.data.v1_0.UserID;
import org.csapi.wsdl.parlayx.data.sync.v1_0._interface.DataSync;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;


@WebService(serviceName = "DataSyncService")
public class SdpSyncImpl implements DataSync {
    private static final Logger log = LoggerFactory.getLogger(SdpSyncImpl.class);
    @RestClient
    NotifyRestClient restClient;

    @Inject
    PublishNewSub publishNewSub;

    @Override
    @Transactional
    public void syncOrderRelation(UserID userID, String spID, String productID, String serviceID, String serviceList, int updateType, String updateTime, String updateDesc, String effectiveTime, String expiryTime, Holder<NamedParameterList> extensionInfo, Holder<Integer> result, Holder<String> resultDescription) {
        try {
//            String response = restClient.notify(updateType == 1 ? "ok" : "stop", userID.getID());
            saveToDb(userID, productID, serviceID, updateType);
            sendToQueue(userID, productID, serviceID, updateType);
            log.info("user with phone: {} send {}", userID.getID(), updateType == 1 ? "ok" : "stop");
            result.value = 0;
            resultDescription.value = "received successfully";
        } catch (java.lang.Exception ex) {
            log.error(ex.toString());
        }
    }

    private void sendToQueue(UserID userID, String productID, String serviceID, int updateType){
        WebServiceQueueItem webServiceQueueItem = new WebServiceQueueItem();
        webServiceQueueItem.setPhone(userID.getID());
        webServiceQueueItem.setServiceId(serviceID);
        webServiceQueueItem.setProductId(productID);
        webServiceQueueItem.setGeneratedPassword(generate4digitNo());
        webServiceQueueItem.setUpdateType(updateType == 1 ? "ok" : "stop");
        publishNewSub.publishAll(webServiceQueueItem);
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
    public String generate4digitNo() {
        int randomPIN = (int)(Math.random()*9000)+1000;
        return String.valueOf(randomPIN);
    }

}
