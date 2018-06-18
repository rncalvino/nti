package com.uade.predictors;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;

import javax.net.ssl.HttpsURLConnection;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uade.predictors.azure.Prediction;
import com.uade.predictors.azure.Response;

public class AzurePredictor implements Predictor {
    
    private String predictionKey;
    private String predictionUrl;
    private String predictionTag;
    private Logger logger;
    
    public AzurePredictor(Properties properties, Logger logger) {
        this.predictionKey = properties.getProperty("predictor.azure.key"); 
        this.predictionUrl = properties.getProperty("predictor.azure.url");
        this.predictionTag = properties.getProperty("predictor.azure.tag");
        this.logger = logger;
    }
    
    @Override
    public float predict(byte[] image) {
        
        float probability = 0;

        this.logger.info("Realizando una llamada a la API de Azure para determinar la probabilidad del Tag.");
        
        Response response = this.performRequest(image);
        
        if(response != null) {
        
            this.logger.info("La API contesto correctamente. Evaluando probabilidad.");
            
            for(Prediction prediction : response.getPredictions()) {

                if(prediction.getTagName().equals(this.predictionTag)) {
                    
                    this.logger.info("Se obtuvo una probabilidad de " + prediction.getProbability());
                    
                    probability = prediction.getProbability();
                }
            }            
        }
        
        return probability;
    }
    
    private Response performRequest(byte[] image) {
        
        Response response = null;
        
        try {
            
            URL obj = new URL(this.predictionUrl);
            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
            con.setRequestProperty("PREDICTION-KEY", this.predictionKey);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/octet-stream");
            
            // Send post request
            con.setDoOutput(true);
            
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.write(image);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            this.logger.debug("\nSending 'POST' request to URL : " + this.predictionUrl);
            this.logger.debug("Post bytes : ");
            this.logger.debug(image);
            this.logger.debug("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer httpResponse = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                httpResponse.append(inputLine);
            }
            
            in.close();
            
            this.logger.debug(httpResponse.toString());
            
            ObjectMapper mapper = new ObjectMapper();
            
            response = mapper.readValue(httpResponse.toString(), Response.class);
            
        } catch (IOException e1) {

            e1.printStackTrace();
        }

        return response; 
    }
}
