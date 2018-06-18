package com.uade;

import org.apache.log4j.Logger;

import java.awt.Dimension;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;
import javax.swing.SwingUtilities;

import com.github.sarxos.webcam.Webcam;
import com.uade.camera.MotionDetector;
import com.uade.prediction.predictors.Predictor;
import com.uade.views.MainView;

/**
 * Hello world!
 *
 */
public class App 
{
	
    private static final Logger logger = Logger.getLogger(App.class);
    
    /**
     * Deteccion de movimiento
     */
	
    public static void main( String[] args ) throws FileNotFoundException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

        Webcam webcam = Webcam.getDefault();
    	webcam.setViewSize(new Dimension(640, 480));
        webcam.open();    	
    	
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
	            
	            try {
	            	
	            	/**
	            	 * Muestro la camara en vivo
	            	 */
	            	
	            	MainView mainView = new MainView(webcam);
	            	mainView.setLocationRelativeTo(null);
	            	mainView.setVisible(true);
	            	
	            	/**
	            	 * Detecto los movimientos (Cuando una persona ingresa al local)
	            	 */
	            	
	        		Thread motionDetector = new Thread(new MotionDetector(webcam, logger));
	        		motionDetector.setDaemon(true);
	        		motionDetector.start();	            	
	            	
	            } catch (Exception e) {
	            	
	                logger.error(e.getStackTrace());
	            }
            }
        });
    	
        /*
    	
    	Predictor predictor = getConfiguredPredictor();
    	
        try {
        	
			// ImageIO.write(webcam.getImage(), "PNG", new File("hello-world.png"));
        	
        	byte[] bytes = WebcamUtils.getImageBytes(webcam, "jpg");
        	
        	predictor.predict(bytes);
			
        	webcam.close();
        	
		} catch (Exception e) {
			
			logger.error(e.getStackTrace());
		} 
		*/       
    }
    
    private static Predictor getConfiguredPredictor() {
    
    	Predictor predictor = null;
    	
    	try {
    	
	    	Properties properties = new Properties();
	    	properties.load(new FileInputStream("app.properties"));
	    	
	    	Class<?> predictorClass = Class.forName(properties.getProperty("predictor.class"));
	    	Class<?>[] arguments = new Class<?>[2];
	    	arguments[0] = Properties.class;
	    	arguments[1] = Logger.class;
	    	
	    	predictor = (Predictor) predictorClass.getDeclaredConstructor(arguments).newInstance(properties, logger);
	    	
    	} catch(Exception e) {
    		
    		logger.error(e.getStackTrace());
    	} 
    	
    	return predictor;
    }
}
