package com.uade.prediction.predictors;

import java.util.Collection;

import com.uade.prediction.Prediction;

public interface Predictor {

	Collection<Prediction> predict(byte[] image);
}
