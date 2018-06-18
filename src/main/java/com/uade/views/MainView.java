package com.uade.views;

import javax.swing.JFrame;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;

public class MainView extends JFrame {

    private static final long serialVersionUID = 1510807459007481944L;

    public MainView(Webcam webcam) {

        WebcamPanel panel = new WebcamPanel(webcam);
        panel.setFPSDisplayed(true);
        panel.setDisplayDebugInfo(true);
        panel.setImageSizeDisplayed(true);
        panel.setMirrored(true);
        
        this.setTitle("Security Camera Application");
        this.setBounds(100, 100, 450, 300);
        this.add(panel);
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);        
    }
}
