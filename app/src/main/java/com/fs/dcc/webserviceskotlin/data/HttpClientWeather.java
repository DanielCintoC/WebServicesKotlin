package com.fs.dcc.webserviceskotlin.data;

import com.fs.dcc.webserviceskotlin.util.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by danielcintoconde on 05/09/18.
 */

public class HttpClientWeather {

    public String getWeatherData(String place){
        HttpURLConnection connection = null;
        InputStream inputStream = null;

        try{

            connection = (HttpURLConnection) (new URL(Util.INSTANCE.getBASE_URL() + place)).openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();

            StringBuilder stringBuffer = new StringBuilder();
            inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while ((line = bufferedReader.readLine()) != null){
                stringBuffer.append(line).append("\r\n");
            }

            inputStream.close();
            connection.disconnect();

            return stringBuffer.toString();

        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
        //return null;

    }

}
