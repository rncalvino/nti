package com.uade.predictors;

public class MockPredictor implements Predictor {

    @Override
    public float predict(byte[] image) {

        return 0.5F;
    }
}
