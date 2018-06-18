package com.uade.camera;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamMotionDetector;
import com.github.sarxos.webcam.WebcamMotionEvent;
import com.github.sarxos.webcam.WebcamMotionListener;
import com.github.sarxos.webcam.WebcamUtils;
import com.uade.predictors.Predictor;

public class MotionDetector implements Runnable {

    private Webcam webcam;
    private Predictor predictor;
    private Logger logger;
    
    
    public MotionDetector(Webcam webcam, 
                          Predictor predictor, 
                          Logger logger) {
        
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
        detector.setInterval(2000);
        
        predictor.predict(WebcamUtils.getImageBytes(webcam, "jpg"));
        
        detector.addMotionListener(new WebcamMotionListener() {
            
            @Override
            public void motionDetected(WebcamMotionEvent arg0) {
            
                logger.info("Ingreso un cliente a la tienda");
                
                /**
                 * Obtengo una captura JPG de la camara y la envio al predictor.
                 */
                
                byte[] bytes = WebcamUtils.getImageBytes(webcam, "jpg");
                
                float probability = predictor.predict(bytes);
                //float probability = 0;
                
                if(probability > 0.6) {
                
                    JOptionPane.showMessageDialog(null, "WARNING.", "Se ha detectado a una persona con mucha probabilidad de cara tapada!", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        detector.start();
    }
}
