package webService;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by zmhbh on 6/14/15.
 */
public class JSONRequest extends IntentService{
    private final static String webServiceUrl="http://www.codeee.com:8080/ICWSWebService/";

    ///////////////
    public static final String IN_MSG="requestType";
    private String inMessage;
    public static final String OUT_MSG= "outputMessage";
    private String process_response_filter;
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public JSONRequest() {
        super("JSONRequest");
    }


    @Override
    protected void onHandleIntent(Intent intent) {

    }





    // integration of sendHttpRequest and broadcastResponse functions
    private void requestBroadcastProcess(String url, List<NameValuePair> nameValuePairs){
        String response=sendHttpRequest(url,nameValuePairs);
        broadcastResponse(response);

    }

    // send Http request to servlet by name value pair
    private String sendHttpRequest(String url,List<NameValuePair> nameValuePairs){
        int REGISTRATION_TIMEOUT = 15 * 1000;
        int WAIT_TIMEOUT = 60 * 1000;
        HttpClient httpclient = new DefaultHttpClient();
        HttpParams params = httpclient.getParams();
        HttpResponse response;
        String content =  "";
        try {

            //http request parameters
            HttpConnectionParams.setConnectionTimeout(params, REGISTRATION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(params, WAIT_TIMEOUT);
            ConnManagerParams.setTimeout(params, WAIT_TIMEOUT);

            //http POST
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            //send the request and receive the repponse
            response = httpclient.execute(httpPost);

            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                content = out.toString();
            }

            else{
                //Closes the connection.
                Log.w("HTTP1:", statusLine.getReasonPhrase());
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }

        } catch (ClientProtocolException e) {
            Log.w("HTTP2:",e );
        } catch (IOException e) {
            Log.w("HTTP3:",e );
        } catch (Exception e) {
            Log.w("HTTP4:",e );
        }

        //send back the JSON response String
        return content;
    }

    //broadcast message that we have received the response
    //from the web service
    private void broadcastResponse(String response){
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(process_response_filter);
        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
        broadcastIntent.putExtra(IN_MSG,inMessage);
        broadcastIntent.putExtra(OUT_MSG,response);
        sendBroadcast(broadcastIntent);
    }



}
