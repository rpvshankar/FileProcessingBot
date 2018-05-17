package com.amazonaws.lambda.fileprocessingbot.lexresponse;

public class dialogAction {

	private String type;
	private String fulfillmentState;
	private message message;
	
	public class Type {
        public static final String Close = "Close";
    }

    public class FulfillmentState {
        public static final String Fulfilled = "Fulfilled";
        public static final String Failed = "Failed";
    }
	
	public dialogAction(String type, String fulfillmentState, message msg) {
		this.type = type;
		this.fulfillmentState = fulfillmentState;
		this.message = msg;
	}
	
	public void setType(String type) {
		this.type = type;
	}

	public void setFulfillmentState(String fulfillmentState) {
		this.fulfillmentState = fulfillmentState;
	}

	public void setMessage(message message) {
		this.message = message;
	}

	public String getType() {
		return type;
	}

	public String getFulfillmentState() {
		return fulfillmentState;
	}

	public message getMessage() {
		return message;
	}

}
