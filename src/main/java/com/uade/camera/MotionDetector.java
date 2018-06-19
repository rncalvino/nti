package com.uade.camera;

import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

import org.apache.log4j.Logger;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamMotionDetector;
import com.github.sarxos.webcam.WebcamMotionEvent;
import com.github.sarxos.webcam.WebcamMotionListener;
import com.github.sarxos.webcam.WebcamUtils;
import com.uade.predictors.Predictor;
import com.uade.views.MainView;

public class MotionDetector implements Runnable {

    private MainView mainView;
    private Webcam webcam;
    private Predictor predictor;
    private Logger logger;
    
    
    public MotionDetector(MainView mainView,
                          Webcam webcam, 
                          Predictor predictor, 
                          Logger logger) {
        
    	this.mainView = mainView;
        this.webcam = webcam;
        this.predictor = predictor;
        this.logger = logger;
    }
    
    @Override
    public void run() {

        /**
         * En cada movimiento detectado en la puerta del lugar 
         * tomo una imagen y la analizo con mi Predictor configurado.
         */
        
        this.logger.info("Iniciando la deteccion de movimientos...");
        
        WebcamMotionDetector detector = new WebcamMotionDetector(this.webcam);
        detector.setAreaThreshold(10);
        detector.setInertia(3000);
        detector.setInterval(1000);
        
        detector.addMotionListener(new WebcamMotionListener() {
            
            @Override
            public void motionDetected(WebcamMotionEvent arg0) {
            
                logger.info("Actividad detectada.");

                JProgressBar progressBar = new JProgressBar();
                progressBar.setMinimum(0);
                progressBar.setMaximum(100);
                
                mainView.addProgressBar(progressBar);
                
                /**
                 * Obtengo una captura JPG de la camara y la envio al predictor.
                 */
                
                byte[] bytes = WebcamUtils.getImageBytes(webcam, "jpg");
                
                progressBar.setValue(50);
                
                float probability = predictor.predict(bytes);
                
                progressBar.setValue(100);
                
                mainView.removeProgressBar(progressBar);
                
                if(probability > 0.5) {
                
                    logger.info("La persona no cumple cumple con los parametros de seguridad establecidos.");
                    
                    JOptionPane.showMessageDialog(null, "Se ha detectado a una persona con mucha probabilidad de cara tapada!", "Alerta", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        detector.start();
    }
}
