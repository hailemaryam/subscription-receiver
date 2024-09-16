package com.hmmk.dataSync;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/api/receive-sdp-custom")
@RegisterRestClient(configKey = "extensions-api")
public interface NotifyRestClient {

    @GET
    String notify(@QueryParam("message") String message, @QueryParam("phone") String phone);

//    @ClientExceptionMapper
//    static Void toException(Response response) {
//        System.out.println(response.getStatus());
//        return null;
//    }

}
