package com.uade.predictors.azure;

import com.fasterxml.jackson.annotation.JsonSetter;

public class Prediction {

    private String tagId;
    private String tagName;
    private float probability;
    
    public String getTagId() {
        return tagId;
    }
    
    @JsonSetter("tagId")
    public void setTagId(String tagId) {
        this.tagId = tagId;
    }
    
    public String getTagName() {
        return tagName;
    }
    
    @JsonSetter("tagName")
    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
    
    public float getProbability() {
        return probability;
    }
    
    @JsonSetter("probability")
    public void setProbability(float probability) {
        this.probability = probability;
    }
}
