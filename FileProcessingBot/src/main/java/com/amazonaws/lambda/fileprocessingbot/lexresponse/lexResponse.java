package com.amazonaws.lambda.fileprocessingbot.lexresponse;

import java.util.LinkedHashMap;
import java.util.Map;

public class lexResponse {

	private dialogAction dialogAction;
	private Map<String, Object> sessionAttributes = new LinkedHashMap<>();
	
	public lexResponse(dialogAction dialogAction, Map<String, Object> sessionAttributes) {
		this.dialogAction = dialogAction;
		this.sessionAttributes = sessionAttributes != null ? sessionAttributes : new LinkedHashMap<>();
	}

	public dialogAction getDialogAction() {
		return dialogAction;
	}
	
	public Object getSessionAttribute(String attributeName) {
        return sessionAttributes.containsKey(attributeName) ? sessionAttributes.get(attributeName) : null;
    }

    public Map<String, Object> getSessionAttributes() {
        return sessionAttributes;
    }

}
