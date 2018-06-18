package com.uade.camera;

import org.apache.log4j.Logger;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamMotionDetector;
import com.github.sarxos.webcam.WebcamMotionEvent;
import com.github.sarxos.webcam.WebcamMotionListener;

public class MotionDetector implements Runnable {

	private Logger logger;
	private Webcam webcam;
	
	public MotionDetector(Webcam webcam, Logger logger) {
		
		this.logger = logger;
		this.webcam = webcam;
	}
	
	@Override
	public void run() {

    	/**
    	 * En cada movimiento detectado en la puerta del lugar 
    	 * tomo una imagen y la analizo con mi Predictor configurado.
    	 */
    	
		this.logger.info("Iniciando la deteccion de movimientos...");
		
    	WebcamMotionDetector detector = new WebcamMotionDetector(this.webcam);
    	detector.setInertia(3000);
		detector.setInterval(1000);
		detector.addMotionListener(new WebcamMotionListener() {
			
			@Override
			public void motionDetected(WebcamMotionEvent arg0) {
			
				logger.info("Ingreso un cliente a la tienda");
			}
		});

		detector.start();
	}
}
