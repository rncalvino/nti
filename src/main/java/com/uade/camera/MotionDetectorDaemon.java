package com.uade.camera;

import java.util.Observable;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamMotionDetector;
import com.github.sarxos.webcam.WebcamMotionEvent;
import com.github.sarxos.webcam.WebcamMotionListener;
import com.github.sarxos.webcam.WebcamUtils;
import com.uade.predictors.Predictor;

public class MotionDetectorDaemon extends Observable implements Runnable {

    private Webcam webcam;
    private Properties properties;
    private Predictor predictor;
    private Logger logger;
    
    
    public MotionDetectorDaemon(Webcam webcam, Properties properties, Predictor predictor, Logger logger) {
        
        this.webcam = webcam;
        this.properties = properties;
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
                
                notifyObservers("Actividad detectada por la camara.");
                setChanged();
                
                /**
                 * Obtengo una captura JPG de la camara y la envio al predictor.
                 */
                
                byte[] bytes = WebcamUtils.getImageBytes(webcam, "jpg");
                
                float imageProbability = predictor.predict(bytes);
                float limitProbability = Float.parseFloat(properties.getProperty("predictor.probability"));
                
                notifyObservers(String.format("Se obtuvo una probabilidad de %f para la imagen capturada.", imageProbability));
                setChanged();
                
                if(imageProbability > limitProbability) {
                    
                    notifyObservers(String.format("ATENCION: La persona NO cumple cumple con los parametros establecidos: P(sospechoso) > %f", limitProbability));
                    
                } else {

                    notifyObservers(String.format("La persona cumple con los parametros de seguridad establecidos: P(sospechoso) < %f", limitProbability));
                }
                
                setChanged();
            }
        });

        detector.start();
    }
}
