package com.amazonaws.lambda.fileprocessingbot.lexRequest.intentLoad;

import java.util.Map;

import com.amazonaws.lambda.fileprocessingbot.lexRequest.lexRequest;

public abstract class IntentLoader {
	
	protected static void readSlots(lexRequest request, Map<String, Object> slots, String productSlotName, String amountSlotName, String unitSlotName) {
        request.setProduct(getSlotValueFor(slots, productSlotName, null));
        request.setRequestedAmount(getSlotValueFor(slots, amountSlotName, null));
        request.setRequestedUnit(getSlotValueFor(slots, unitSlotName, null));
    }

    protected static String getSlotValueFor(Map<String, Object> slots, String productSlotName, String defaultValue) {
        String slotValue = (String)slots.get(productSlotName);
        return slotValue != null ? slotValue : defaultValue;
    }

    public abstract void load(lexRequest request, Map<String, Object> slots);

}
