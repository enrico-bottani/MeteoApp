package ch.supsi.dti.isin.meteoapp.model;

import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ch.supsi.dti.isin.meteoapp.network.Weather;

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


    public Weather fetchItem(String[] locationName) {
        Weather weather=null;

        try {
            String url = Uri.parse("http://api.openweathermap.org/data/2.5/weather?q=" + locationName[0] + "&appid=" + Datafetcher.API_KEY)
                    .buildUpon()
                    .build().toString();
            String jsonString = new String(getUrlBytes(url));
            Log.i(TAG, "Received JSON: " + jsonString);
            JSONObject jsonBody = new JSONObject(jsonString);
            Gson gson=new Gson();
            weather=gson.fromJson(jsonString, Weather.class);
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
