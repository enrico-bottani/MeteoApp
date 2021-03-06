package ch.supsi.dti.isin.meteoapp.model;

import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import ch.supsi.dti.isin.meteoapp.network.JSONRoot;

import static android.support.constraint.Constraints.TAG;

public class Datafetcher {
    private static final String API_KEY = "c749c22085868d0df94c28375102d043";
    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK)
                throw new IOException(connection.getResponseMessage() + ": with " + urlSpec);

            int bytesRead;
            byte[] buffer = new byte[1024];

            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }

            out.close();
             Log.d("array", out.toByteArray().toString());
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }


    public JSONRoot fetchItem(Location locationName) {
        JSONRoot weather=null;

        try {
            String url=null;
            if(locationName.getLon()!=0 && locationName.getLat()!=0){
                url = Uri.parse("http://api.openweathermap.org/data/2.5/weather?lat=" + locationName.getLat() + "&lon="+locationName.getLon()+"&appid=" + Datafetcher.API_KEY)
                        .buildUpon()
                        .build().toString();
            }
            else {
                url = Uri.parse("http://api.openweathermap.org/data/2.5/weather?q=" + locationName.getName() + "&appid=" + Datafetcher.API_KEY)
                        .buildUpon()
                        .build().toString();
            }

            String jsonString = new String(getUrlBytes(url));
            Log.i(TAG, "Received JSON: " + jsonString);
            JSONObject jsonBody = new JSONObject(jsonString);
            Gson gson=new Gson();
            weather=gson.fromJson(jsonString, JSONRoot.class);
            Log.d("tag",jsonBody.toString());
            Log.d("tag","temperature: "+weather.getMain().getTemp());
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
        } catch (JSONException je) {
            Log.e(TAG, "Failed to parse JSON", je);
        }

        return weather;
    }
}
