package com.amazonaws.lambda.fileprocessingbot.lexRequest;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.lambda.fileprocessingbot.intents.FileTransferIntent;
import com.amazonaws.lambda.fileprocessingbot.intents.GreetingsIntent;
import com.amazonaws.lambda.fileprocessingbot.lexRequest.intentLoad.FileTransferIntentLoad;
import com.amazonaws.lambda.fileprocessingbot.lexRequest.intentLoad.GreetingsIntentLoad;
import com.amazonaws.lambda.fileprocessingbot.lexRequest.intentLoad.IntentLoader;
import com.amazonaws.lambda.fileprocessingbot.lexRequest.intentLoad.UnsupportedIntentLoader;

import static org.apache.http.util.TextUtils.isEmpty;

public class LexRequestFactory {


    private final static Map<String, IntentLoader> intentLoaderStrategies = new HashMap<>();
    private final static IntentLoader unsupportedIntentLoader = new UnsupportedIntentLoader();
    private final static String FACEBOOK_ID_PATTERN = "^\\d{16}$";

    static {
        intentLoaderStrategies.put(GreetingsIntent.Name, new GreetingsIntentLoad());
       // intentLoaderStrategies.put(BakeryDepartmentIntent.Name, new BakeryDepartmentIntentLoadingStrategy());
       // intentLoaderStrategies.put(MilkDepartmentIntent.Name, new MilkDepartmentIntentLoadingStrategy());
        intentLoaderStrategies.put(FileTransferIntent.Name, new FileTransferIntentLoad());
    }
	
	

    public static lexRequest createFromMap(Map<String, Object> input) {
        lexRequest request = new lexRequest();
        if(input == null)
            return request;

        loadMainAttributes(input, request);
        loadBotName(input, request);
        loadSessionAttributes(input, request);

        Map<String, Object> currentIntent = loadCurrentIntent(input);
        if (currentIntent != null)
            loadIntentParameters(currentIntent, request);

        return request;
    }

    private static void loadMainAttributes(Map<String, Object> input, lexRequest request) {
        loadUserId(input, request);
        request.setInputTranscript((String) input.get(LexRequestAttribute.InputTranscript));
        request.setInvocationSource(getInvocationSource(input));
        request.setOutputDialogMode(getOutputDialogMode(input));
    }

    private static void loadUserId(Map<String, Object> input, lexRequest request) {
        String userId = (String) input.get(LexRequestAttribute.UserId);
        request.setUserId(userId);
        if(isEmpty(userId))
            request.setUserIdType(UserIdType.Undefined);
        else {
            if(userId.matches(FACEBOOK_ID_PATTERN))
                request.setUserIdType(UserIdType.Facebook);
            else
                request.setUserIdType(UserIdType.Undefined);
        }
    }

    private static OutputDialogMode getOutputDialogMode(Map<String, Object> input) {
        return LexRequestAttribute.OutputDialogModeValue.Voice.equals((String) input.get(LexRequestAttribute.OutputDialogMode))
                ? OutputDialogMode.Voice : OutputDialogMode.Text;
    }

    private static InvocationSource getInvocationSource(Map<String, Object> input) {
        return LexRequestAttribute.InvocationSourceValue.DialogCodeHook.equals((String) input.get(LexRequestAttribute.InvocationSource))
                                        ? InvocationSource.DialogCodeHook : InvocationSource.FulfillmentCodeHook;
    }

    private static void loadSessionAttributes(Map<String, Object> input, lexRequest request) {
        Map<String, Object> sessionAttrs = (Map<String, Object>) input.get(LexRequestAttribute.SessionAttributes);
        if (sessionAttrs != null)
            request.setSessionAttributes(sessionAttrs);
    }

    private static void loadIntentParameters(Map<String, Object> currentIntent, lexRequest request) {
        request.setConfirmationStatus(getConfirmationStatus(currentIntent));
        request.setIntentName((String) currentIntent.get(LexRequestAttribute.CurrentIntentName));

        loadIntentSlots(currentIntent, request);
    }

    private static ConfirmationStatus getConfirmationStatus(Map<String, Object> currentIntent) {
        String confirmationStatus = (String) currentIntent.get(LexRequestAttribute.InvocationSource);
        return LexRequestAttribute.ConfirmationStatusValue.Confirmed.equals(confirmationStatus)
                ? ConfirmationStatus.Confirmed
                : LexRequestAttribute.ConfirmationStatusValue.Denied.equals(confirmationStatus)
                    ? ConfirmationStatus.Denied
                    : ConfirmationStatus.None;
    }

    private static Map<String, Object> loadCurrentIntent(Map<String, Object> input) {
        return (Map<String, Object>) input.get(LexRequestAttribute.CurrentIntent);
    }

    private static void loadBotName(Map<String, Object> input, lexRequest request) {
        Map<String, Object> bot = (Map<String, Object>) input.get(LexRequestAttribute.Bot);
        if (bot != null)
            request.setBotName((String) bot.get(LexRequestAttribute.BotName));
    }

    private static void loadIntentSlots(Map<String, Object> currentIntent, lexRequest request) {
        IntentLoader strategy = getIntentLoadingStrategyBy(request.getIntentName());
        Map<String, Object> slots = (Map<String, Object>) currentIntent.get(LexRequestAttribute.Slots);
        strategy.load(request, slots);
    }

    private static IntentLoader getIntentLoadingStrategyBy(String intentName) {
        return intentLoaderStrategies.containsKey(intentName)
                ? intentLoaderStrategies.get(intentName)
                : unsupportedIntentLoader;
    }

}
