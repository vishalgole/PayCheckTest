package cordova.plugin.testpayment;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class echoes a string called from JavaScript.
 */
public class TestPayment extends CordovaPlugin {

    public static String LIQUIDPAY_API_KEY = "KEY1" ;
    public static String LIQUIDPAY_SECRET_KEY = "SECRET1";

    private WidgetBuilder widgetBuilder = new WidgetBuilder()
                .setApiKey(LIQUIDPAY_API_KEY)
                .setApiSecret(LIQUIDPAY_SECRET_KEY)
                .setApplicationContext(getApplicationContext())
                .build();

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("coolMethod")) {
            String message = args.getString(0);
            this.coolMethod(message, callbackContext);
            return true;
        }else if (action.equals("add")) {
            this.add(args, callbackContext);
        }else if (action.equals("triggerWid")) {
            this.triggerWid(args, callbackContext);
        }
        return false;
    }

    private void coolMethod(String message, CallbackContext callbackContext) {
        if (message != null && message.length() > 0) {
            callbackContext.success(message);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }

    private void add(JSONArray args, CallbackContext callbackContext) {
        if(args !=null){
            try {
                int param1 = Integer.parseInt(args.getJSONObject(0).getString("param1"));
                int param2 = Integer.parseInt(args.getJSONObject(1).getString("param2"));
                callbackContext.success("" + (param1+param2));
            } catch (Exception e) {
                //TODO: handle exception
                callbackContext.error("Unexpected Error");
            }
        }
    }

    private void triggerWid(String message, CallbackContext callbackContext) {
        try {
            widgetBuilder.run(new WidgetInterface() {
                @Override
                public void onExit() {
                    // result_status.setTextColor(Color.BLACK);
                    // result_status.setText(R.string.done);
                    new Utils().showToast(getApplicationContext(), "Widget Triggered");
                    callbackContext.success("Done");
                }
            });
        } catch (IllegalStateException e) {
            Log.e("WIDGET ACCESS", e.getMessage());
            callbackContext.error("Error");
        }
    }
}
