package com.amazonaws.lambda.fileprocessingbot.lexresponse;

import java.util.LinkedHashMap;
import java.util.Map;

import com.amazonaws.lambda.fileprocessingbot.lexRequest.lexRequest;

public final class lexResponseHelper {

	public static lexResponse createFailedLexResponse(String msg, lexRequest lxRequest) {
        Map<String, Object> sessionAttributes = lxRequest != null ? lxRequest.getSessionAttributes() : new LinkedHashMap<>();
        return new lexResponse(new dialogAction(dialogAction.Type.Close, dialogAction.FulfillmentState.Failed,
                new message(message.ContentType.PlainText, msg)), sessionAttributes);
    }

    public static lexResponse createLexResponse(lexRequest lxRequest, String content, String type, String fulfillmentState) {
        message msg = new message(message.ContentType.PlainText, content);
        dialogAction dialogAction = new dialogAction(type, fulfillmentState, msg);
        return new lexResponse(dialogAction, lxRequest.getSessionAttributes());
    }
}
