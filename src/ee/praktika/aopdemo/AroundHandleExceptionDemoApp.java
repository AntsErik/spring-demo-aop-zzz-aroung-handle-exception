package ee.praktika.aopdemo;

import java.util.logging.Logger;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ee.praktika.aopdemo.service.TrafficFortuneService;

public class AroundHandleExceptionDemoApp {

    private static Logger myLogger = Logger.getLogger( AroundHandleExceptionDemoApp.class.getName() ); //Built in Java, no need for additional JAR files

    public static void main( String[] args ){

        //read Spring Configuration Java Class
        AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext( DemoConfig.class );

        //Get the bean from Spring container
        TrafficFortuneService theFortuneService =
            context.getBean( "trafficFortuneService", TrafficFortuneService.class );

        myLogger.info( "\nMain program :AroundDemoApp" );

        myLogger.info( "Calling getFortune" );

        boolean tripWire = true;
        String data = theFortuneService.getFortune( tripWire );

        myLogger.info( "\nMy fortune is: " + data );

        myLogger.info( "\nFinished!" );

        //Close the context
        context.close();

    }

}
