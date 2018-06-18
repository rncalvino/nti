package com.uade.prediction.predictors;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collection;
import java.util.Properties;

import javax.net.ssl.HttpsURLConnection;
import org.apache.log4j.Logger;

import com.uade.prediction.Prediction;

public class AzurePredictor implements Predictor {
	
	private String predictionKey;
	private String predictionUrl;
	private Logger logger;
	
	public AzurePredictor(Properties properties, Logger logger) {
		this.predictionKey = properties.getProperty("predictor.azure.key"); 
		this.predictionUrl = properties.getProperty("predictor.azure.url");
		this.logger = logger;
	}
	
	@Override
	public Collection<Prediction> predict(byte[] image) {
		
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
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			
			in.close();
			
			this.logger.debug(response.toString());
			
		} catch (IOException e1) {

			e1.printStackTrace();
		}

		return null;
	}
}
