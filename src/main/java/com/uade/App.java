package com.uade;

import org.apache.log4j.Logger;

import java.awt.Dimension;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;
import javax.swing.SwingUtilities;

import com.uade.camera.MotionDetectorDaemon;
import com.uade.predictors.Predictor;
import com.uade.views.MainView;

import com.github.sarxos.webcam.Webcam;

/**
 * Nuevas Tecnologias de la Informacion 
 *
 * 2018
 */

public class App 
{
    
    private static final Logger logger = Logger.getLogger(App.class);
    
    /**
     * Deteccion de movimiento
     */
    
    public static void main(String[] args ) throws FileNotFoundException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

    	/**
    	 * Obtengo la configuracion 
    	 */
    	
        final Properties properties = new Properties();
        properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("app.properties"));    	
    	
        /**
         * Obtengo una referencia de la camara disponible.
         */
        
        final Webcam webcam = Webcam.getDefault();
        webcam.setViewSize(new Dimension(640, 480));
        webcam.open();

        /**
         * Instancio al predictor configurado (Azure)
         */
        
        final Predictor predictor = getConfiguredPredictor(properties);
        
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                
                try {
                    
                    /**
                     * Muestro la camara en vivo
                     */
                    
                    final MainView mainView = new MainView(webcam);
                    mainView.setLocationRelativeTo(null);
                    mainView.setVisible(true);
                	
                    /**
                     * Detecto los movimientos de la camara en un thread aparte.
                     */
                    
                    MotionDetectorDaemon daemon = new MotionDetectorDaemon(webcam, properties, predictor, logger);
                    daemon.addObserver(mainView);
                    
                    Thread thread = new Thread(daemon);
                    thread.setDaemon(true);
                    thread.start();
                    
                } catch (Exception e) {
                    
                    logger.error(e.getStackTrace());
                }
            }
        });
    }
    
    private static Predictor getConfiguredPredictor(Properties properties) {
    
        Predictor predictor = null;
        
        try {

            Class<?> predictorClass = Class.forName(properties.getProperty("predictor.class"));
            Class<?>[] arguments = new Class<?>[2];
            arguments[0] = Properties.class;
            arguments[1] = Logger.class;
            
            predictor = (Predictor) predictorClass.getDeclaredConstructor(arguments).newInstance(properties, logger);
            
        } catch(Exception e) {
            
            logger.error(e.getMessage());
        } 
        
        return predictor;
    }
}
