package com.uade.predictors;

import java.util.Properties;

import org.apache.log4j.Logger;

public class MockPredictor implements Predictor {

	public MockPredictor(Properties properties, Logger logger) {
		
	}
	
    @Override
    public float predict(byte[] image) {

        return 0.5F;
    }
}
