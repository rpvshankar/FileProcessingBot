package com.amazonaws.lambda.fileprocessingbot.lexresponse;

public class message {
	
	private String contentType;
	private String content;
	
	public class ContentType{
        public static final String PlainText = "PlainText";
    }

	public message(String contentType, String content) {
		this.content = content;
		this.contentType = contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContentType() {
		return contentType;
	}

	public String getContent() {
		return content;
	}

}
