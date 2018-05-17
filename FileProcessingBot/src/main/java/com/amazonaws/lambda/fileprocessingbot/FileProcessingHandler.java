package com.amazonaws.lambda.fileprocessingbot;

import java.util.Map;

import com.amazonaws.lambda.fileprocessingbot.lexRequest.lexRequest;
import com.amazonaws.lambda.fileprocessingbot.lexresponse.dialogAction;
import com.amazonaws.lambda.fileprocessingbot.lexresponse.lexResponse;
import com.amazonaws.lambda.fileprocessingbot.lexresponse.message;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class FileProcessingHandler implements RequestHandler<Map<String, Object>, Object> {

    @Override
    public Object handleRequest(Map<String, Object>  input, Context context) {
        context.getLogger().log("Input: " + input);
        
        Map<String, Object> botMap = (Map<String, Object>) input.get("bot");
        
        String strBotName = (String) botMap.get("name");
        String strContent = "Request came from Bot " + strBotName;
        
        lexRequest lRequest = new lexRequest();
        
        message objMessage = new message("PlainText",strContent);
        dialogAction objDialogAction =  new dialogAction("Close","Fulfilled", objMessage);

        // TODO: implement your handler
       // return new lexResponse(objDialogAction);
        return null;
    }

}
