package com.presencecontrol.m2m.m2m_presencecontrol;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * author :Leonor Martinez Mesas
 * This class implement in a separate handler thread.
 * The DM-M2M communication
 */
public class DMGETPOSTIntentService extends IntentService {
    //atributes
    URL url;
    String getResponse;
    String postResponse;
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_GET = "com.presencecontrol.m2m.m2m_presencecontrol.action.GET";
    private static final String ACTION_POSTHEADER = "com.presencecontrol.m2m.m2m_presencecontrol.action.POSTHEADER";
    // parameters
    private static final String EXTRA_URL = "com.presencecontrol.m2m.m2m_presencecontrol.extra.url";
  //  private static final String EXTRA_BODY = "com.presencecontrol.m2m.m2m_presencecontrol.extra.PARAM2";
    /**
     * Starts this service to perform action POST with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
//    public static void startActionPOST(Context context, String stringUrl, String body) {
//        Intent intent = new Intent(context, DMGETPOSTIntentService.class);
//        intent.setAction(ACTION_POST);
//        intent.putExtra(EXTRA_URL, stringUrl);
//        intent.putExtra(EXTRA_BODY, body);
//        context.startService(intent);
//    }
//


    /**
     * Starts this service to perform action GET with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionGET(Context context, String stringUrl) {
        Intent intent = new Intent(context, DMGETPOSTIntentService.class);
        intent.setAction(ACTION_GET);
        intent.putExtra(EXTRA_URL, stringUrl);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action POSTHEADER with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionPOSTHEADER(Context context, String stringUrl) {
        Intent intent = new Intent(context, DMGETPOSTIntentService.class);
        intent.setAction(ACTION_POSTHEADER);
        intent.putExtra(EXTRA_URL, stringUrl);
        context.startService(intent);
    }

    public DMGETPOSTIntentService() {
        super("DMGETPOSTIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_GET.equals(action)) {
                final String stringUrl = intent.getStringExtra(EXTRA_URL);
                handleActionGET(stringUrl);
            } else if (ACTION_POSTHEADER.equals(action)) {
                final String stringUrl = intent.getStringExtra(EXTRA_URL);
                handleActionPOSTHEADER(stringUrl);
            }
        }
    }

    /**
     * Handle action GET execute in background thread a get petition with the provided
     * url.
     */
    private void handleActionGET(String stringUrl) {
        Log.d(" ----------------DMGETPOSTIntentService----------------:","handleActionGET:------------"+stringUrl+"-----------------------");
        try {
            url = new URL(stringUrl);
            URLConnection urlConnection = url.openConnection();
            InputStream in0 = urlConnection.getInputStream();
            Log.d("----------------------getInputStreamOk------------------------------------------", "------------------------------------");
            InputStreamReader in = new InputStreamReader(in0);
            BufferedReader rd = new BufferedReader(in);
            String line;
            StringBuilder total = new StringBuilder();
            while ((line = rd.readLine()) != null){
                    total.append(line);
            }
            getResponse= total.toString();
            Log.d("------------RESPUESTA----------", "------" + getResponse + "-----");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e) {
            Log.d("---Error IOException", e.getMessage());
        }
           }
    /**
     * Handle action POST without body in the provided background thread with the provided
     * Url.
     */
    private void handleActionPOSTHEADER(String stringUrl) {
        Log.d("------------------------------DMGETPOSTIntentService----------------------------:","handleActionPOSTHEADER:------------"+stringUrl+"-----------------------");
        try {
            url = new URL(stringUrl);
            URLConnection urlConnection = url.openConnection();
            urlConnection.setDoOutput(true);
            InputStream in0 = urlConnection.getInputStream();
            Log.d("----------------------getInputStreamOk------------------------------------------", "------------------------------------");
            InputStreamReader in = new InputStreamReader(in0);
            BufferedReader rd = new BufferedReader(in);
            String line;
            StringBuilder total = new StringBuilder();
            try {
                while ((line = rd.readLine()) != null)
                    total.append(line);            } catch (IOException e) {
                e.printStackTrace();
            }
            postResponse = total.toString();
            Log.d("----RESULTADO--------", "------" + postResponse + "-----");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e) {
            Log.d("---Error IOException", e.getMessage());
        }
    }
}
