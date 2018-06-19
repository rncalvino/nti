package com.uade.predictors.azure;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonSetter;

public class Response {

    private String id;
    private String project;
    private String iteration;
    private String createdAt;
    
    private Collection<Prediction> predictions;
    
    public String getId() {
        return id;
    }
    
    @JsonSetter("id")
    public void setId(String id) {
        this.id = id;
    }
    
    public String getProject() {
        return project;
    }
    
    @JsonSetter("project")
    public void setProject(String project) {
        this.project = project;
    }
    
    public String getIteration() {
        return iteration;
    }
    
    @JsonSetter("iteration")
    public void setIteration(String iteration) {
        this.iteration = iteration;
    }
    
    public String getCreatedAt() {
        return createdAt;
    }
    
    @JsonSetter("created")
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    
    @JsonSetter("predictions")
    public Collection<Prediction> getPredictions() {
        return predictions;
    }
    
    public void setPredictions(Collection<Prediction> predictions) {
        this.predictions = predictions;
    }    
}
