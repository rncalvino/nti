package com.uade.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
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
    private JLabel vivoLabel;
    private JLabel sospechosoLabel;
    private JLabel noSospechosoLabel;
    private JPanel bottomPanel;
    private JScrollPane bottomScrollPane;
    private JTextArea bottomTextArea;
    private WebcamPanel webcamPanel;
    
    public MainView(Webcam webcam) {

        /**
         * Panel superior
         */

        this.vivoLabel = new JLabel();
        this.vivoLabel.setText("CAMARA 1");
        this.vivoLabel.setForeground(Color.white);
        this.vivoLabel.setFont(new Font("Tahoma", Font.BOLD, 32));
        
        this.sospechosoLabel = new JLabel();
        this.sospechosoLabel.setText("SOSPECHOSO");
        this.sospechosoLabel.setForeground(Color.red);
        this.sospechosoLabel.setFont(new Font("Tahoma", Font.BOLD, 32));

        this.noSospechosoLabel = new JLabel();
        this.noSospechosoLabel.setText("OK");
        this.noSospechosoLabel.setForeground(Color.green);
        this.noSospechosoLabel.setFont(new Font("Tahoma", Font.BOLD, 32));
        
        this.topPanel = new JPanel();
        this.topPanel.add(this.vivoLabel);
        
        /**
         * Panel inferior
         */
        
        this.bottomTextArea = new JTextArea();
        this.bottomTextArea.setRows(5);
        this.bottomTextArea.setColumns(80);
        
        this.bottomScrollPane = new JScrollPane(this.bottomTextArea);
        this.bottomScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.bottomPanel = new JPanel();
        this.bottomPanel.add(this.bottomScrollPane);
        
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

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setVisible(true);
        this.pack();
    }
    
    @Override
    public void update(Observable arg0, Object arg1) {
        
        JLabel label = (boolean)arg1 ? this.sospechosoLabel : this.noSospechosoLabel; 
        
        this.topPanel.remove(vivoLabel);
        this.topPanel.add(label);
        this.topPanel.repaint();
        this.topPanel.revalidate();
        
        new java.util.Timer().schedule( 
            new java.util.TimerTask() {
                @Override
                public void run() {
                    topPanel.remove(label);
                    topPanel.add(vivoLabel);
                    topPanel.repaint();
                    topPanel.revalidate();
                }
            }, 
            1000 
        );
    }
    
    public void writeMessage(String message) {
        this.bottomTextArea.append(message);
        this.bottomScrollPane.getVerticalScrollBar().setValue(this.bottomScrollPane.getVerticalScrollBar().getMaximum());        
    }    
}
