package com.hmmk.dataSync;

import jakarta.jws.WebService;
import jakarta.xml.ws.Holder;
import org.csapi.schema.parlayx.data.v1_0.NamedParameterList;
import org.csapi.schema.parlayx.data.v1_0.UserID;
import org.csapi.wsdl.parlayx.data.sync.v1_0._interface.DataSync;
import org.eclipse.microprofile.rest.client.inject.RestClient;


@WebService(serviceName = "DataSyncService")
public class SdpSyncImpl implements DataSync {
    @RestClient
    NotifyRestClient restClient;

    @Override
    public void syncOrderRelation(UserID userID, String spID, String productID, String serviceID, String serviceList, int updateType, String updateTime, String updateDesc, String effectiveTime, String expiryTime, Holder<NamedParameterList> extensionInfo, Holder<Integer> result, Holder<String> resultDescription) {
        try {
            //notifyAutoResponder(updateType == 1 ? "ok" : "stop", userID.getID());
            String response = restClient.notify(updateType == 1 ? "ok" : "stop", userID.getID());
            System.out.println("******************************************************");
            System.out.println(response);
            int resultValue = 0;
            result.value = resultValue;
            resultDescription.value = response;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
}
