package com.amazonaws.lambda.fileprocessingbot.lexRequest.intentLoad;

import java.util.Map;

import com.amazonaws.lambda.fileprocessingbot.lexRequest.lexRequest;

public class UnsupportedIntentLoader extends IntentLoader {

	@Override
	public void load(lexRequest request, Map<String, Object> slots) {
		request.setError("Not recognized request.");

	}

}

