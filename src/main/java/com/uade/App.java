package com.uade;

import org.apache.log4j.Logger;

import java.awt.Dimension;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;
import javax.swing.SwingUtilities;

import com.uade.camera.MotionDetector;
import com.uade.predictors.Predictor;
import com.uade.views.MainView;

import com.github.sarxos.webcam.Webcam;

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

        /**
         * Obtengo una referencia de la camara disponible.
         */
        
        Webcam webcam = Webcam.getDefault();
        webcam.setViewSize(new Dimension(640, 480));
        webcam.open();

        /**
         * Instancio al predictor configurado (Azure)
         */
        
        Predictor predictor = getConfiguredPredictor();
        
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                
                try {
                    
                    /**
                     * Detecto los movimientos de la camara.
                     */
                    
                    Thread motionDetector = new Thread(new MotionDetector(webcam, predictor, logger));
                    motionDetector.setDaemon(true);
                    motionDetector.start();
                    
                    /**
                     * Muestro la camara en vivo
                     */
                    
                    MainView mainView = new MainView(webcam);
                    mainView.setLocationRelativeTo(null);
                    mainView.setVisible(true);
                    
                } catch (Exception e) {
                    
                    logger.error(e.getStackTrace());
                }
            }
        });
    }
    
    private static Predictor getConfiguredPredictor() {
    
        Predictor predictor = null;
        
        try {
        
            Properties properties = new Properties();
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("app.properties"));
            
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
