package com.uade.views;

import java.awt.BorderLayout;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;

public class MainView extends JFrame implements Observer {

    private static final long serialVersionUID = 1510807459007481944L;

    private JPanel topPanel;
    private JPanel bottomPanel;
    private JTextArea bottomTextArea;
    private WebcamPanel webcamPanel;
    
    public MainView(Webcam webcam) {

    	/**
    	 * Panel superior
    	 */
    	
    	this.topPanel = new JPanel();
    	this.topPanel.add(new JLabel("Nuevas Tecnologias de la Informacion"));
    	
    	/**
    	 * Panel inferior
    	 */
    	
    	this.bottomTextArea = new JTextArea();
    	this.bottomTextArea.setRows(5);
    	this.bottomTextArea.setColumns(80);
    	
    	JScrollPane scrollPane = new JScrollPane(this.bottomTextArea);
    	scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    	this.bottomPanel = new JPanel();
    	this.bottomPanel.add(scrollPane);
    	
    	/**
    	 * Panel central
    	 */
    	
        this.webcamPanel = new WebcamPanel(webcam);
        webcamPanel.setFPSDisplayed(true);
        webcamPanel.setDisplayDebugInfo(true);
        webcamPanel.setImageSizeDisplayed(true);
        webcamPanel.setMirrored(true);
        
        this.setTitle("Nuevas Tecnologias de la Informacion");
        this.setLayout(new BorderLayout());
        this.getContentPane().add(this.topPanel, BorderLayout.NORTH);
        this.getContentPane().add(this.webcamPanel, BorderLayout.CENTER);
        this.getContentPane().add(this.bottomPanel, BorderLayout.SOUTH);
        this.setResizable(true);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

	@Override
	public void update(Observable arg0, Object arg1) {
		
		this.bottomTextArea.append(String.format("%s: %s %s", new Date().toString(), arg1.toString(), System.lineSeparator()));
	}
}
