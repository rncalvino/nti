package com.uade.predictors.azure;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonSetter;

public class Response {

    private String id;
    private String project;
    private String iteration;
    private String createdAt;
    
    private Collection<Prediction> predictions;
    
    @JsonSetter("Id")
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    
    @JsonSetter("Project")
    public String getProject() {
        return project;
    }
    
    public void setProject(String project) {
        this.project = project;
    }
    
    @JsonSetter("Iteration")
    public String getIteration() {
        return iteration;
    }
    
    public void setIteration(String iteration) {
        this.iteration = iteration;
    }
    
    @JsonSetter("Created")
    public String getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    
    @JsonSetter("Predictions")
    public Collection<Prediction> getPredictions() {
        return predictions;
    }
    
    public void setPredictions(Collection<Prediction> predictions) {
        this.predictions = predictions;
    }    
}
