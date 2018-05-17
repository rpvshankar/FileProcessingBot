package com.amazonaws.lambda.fileprocessingbot.lexRequest.intentLoad;

import java.util.Map;

import com.amazonaws.lambda.fileprocessingbot.intents.GreetingsIntent;
import com.amazonaws.lambda.fileprocessingbot.lexRequest.lexRequest;

public class GreetingsIntentLoad extends IntentLoader {

	@Override
	public void load(lexRequest request, Map<String, Object> slots) {
		request.setFirstName(getSlotValueFor(slots, GreetingsIntent.Slot.FirstName, null));
	    request.setLastName(getSlotValueFor(slots, GreetingsIntent.Slot.LastName, null));

	}

}
