package com.hmmk.service;

import com.hmmk.DTO.WebServiceQueueItem;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@ApplicationScoped
public class PublishNewSub {

    @Inject
    @Channel("sdp-notify-web-register")
    Emitter<WebServiceQueueItem> emitToWebRegister;

    @Inject
    @Channel("sdp-notify-sms-sender")
    Emitter<WebServiceQueueItem> emitToSmsSender;

    @Inject
    @Channel("sdp-notify-charging")
    Emitter<WebServiceQueueItem> emitToCharging;

    @Inject
    @Channel("sdp-notify-reporting")
    Emitter<WebServiceQueueItem> emitToReporting;

    @Inject
    @Channel("sdp-notify-red-ad-receiver")
    Emitter<WebServiceQueueItem> emitToRedAdReceiver;


    public void publishAll(WebServiceQueueItem webServiceQueueItem){
        emitToCharging.send(webServiceQueueItem);
        emitToReporting.send(webServiceQueueItem);
        emitToWebRegister.send(webServiceQueueItem);
        emitToSmsSender.send(webServiceQueueItem);
        emitToRedAdReceiver.send(webServiceQueueItem);
    }
}
