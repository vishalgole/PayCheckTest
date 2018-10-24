package cordova.plugin.testpayment;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import widget.liquidpay.com.widget.handler.PaymentInterface;
import widget.liquidpay.com.widget.handler.WidgetBuilder;
import widget.liquidpay.com.widget.handler.WidgetInterface;
import widget.liquidpay.com.widget.utility.Utils;

/**
 * This class echoes a string called from JavaScript.
 */
public class TestPayment extends CordovaPlugin {

    public static String LIQUIDPAY_API_KEY = "KEY1" ;
    public static String LIQUIDPAY_SECRET_KEY = "SECRET1";

    

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("coolMethod")) {
            String message = args.getString(0);
            this.coolMethod(message, callbackContext);
            return true;
        }else if (action.equals("add")) {
            this.add(args, callbackContext);
        }else if (action.equals("triggerWid")) {
            String message = args.getString(0);
            this.triggerWid(message, callbackContext);
        }else if(action.equals("triggerPay")) {
            String message = args.getString(0);
            this.triggerPay(message, callbackContext);
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
        Context context = this.cordova.getActivity().getApplicationContext();
         WidgetBuilder widgetBuilder = new WidgetBuilder()
                .setApiKey(LIQUIDPAY_API_KEY)
                .setApiSecret(LIQUIDPAY_SECRET_KEY)
                .setApplicationContext(context)
                .build();
        try {
            widgetBuilder.run(new WidgetInterface() {
                @Override
                public void onExit() {
                    // result_status.setTextColor(Color.BLACK);
                    // result_status.setText(R.string.done);
                    new Utils().showToast(context, "Widget Triggered");
                    callbackContext.success("Done");
                }
            });
        } catch (IllegalStateException e) {
            Log.e("WIDGET ACCESS", e.getMessage());
            callbackContext.error("Error");
        }
    }

    private void triggerPay(String message, CallbackContext callbackContext) {
        Context context = this.cordova.getActivity().getApplicationContext();
         WidgetBuilder widgetBuilder = new WidgetBuilder()
                .setApiKey(LIQUIDPAY_API_KEY)
                .setApiSecret(LIQUIDPAY_SECRET_KEY)
                .setApplicationContext(context)
                .build();
        try {
            widgetBuilder.doPayment(new PaymentInterface() {
                @Override
                public void onPaymentSuccess() {
                    new Utils().showToast(context, context.getResources().getString(R.string.payment_success));
                    callbackContext.success(context.getResources().getString(R.string.payment_success));
                }

                @Override
                public void onPaymentFailure() {
                    new Utils().showToast(context, context.getResources().getString(R.string.payment_failed));
                    callbackContext.success(context.getResources().getString(R.string.payment_failed));
                }

                @Override
                public void onPaymentCancelled() {
                    new Utils().showToast(context, context.getResources().getString(R.string.transaction_cancelled));
                    callbackContext.success(context.getResources().getString(R.string.transaction_cancelled));
                }
            });

        } catch (IllegalStateException e) {
            Log.e("WIDGET ACCESS", e.getMessage());
        }
    }
    
}
