package com.uade.views;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;

public class MainView extends JFrame {

    private static final long serialVersionUID = 1510807459007481944L;

    WebcamPanel panel;
    
    public MainView(Webcam webcam) {

        this.panel = new WebcamPanel(webcam);
        panel.setFPSDisplayed(true);
        panel.setDisplayDebugInfo(true);
        panel.setImageSizeDisplayed(true);
        panel.setMirrored(true);
        
        this.setTitle("Security Camera Application");
        this.setBounds(100, 100, 450, 300);
        this.add(panel, BorderLayout.CENTER);
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);        
    }
}
