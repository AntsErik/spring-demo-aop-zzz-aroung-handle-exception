package ee.praktika.aopdemo;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ee.praktika.aopdemo.service.TrafficFortuneService;

public class AroundDemoApp {

    public static void main( String[] args ){

        //read Spring Configuration Java Class
        AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext( DemoConfig.class );

        //Get the bean from Spring container
        TrafficFortuneService theFortuneService =
            context.getBean( "trafficFortuneService", TrafficFortuneService.class );

        System.out.println( "\nMain program :AroundDemoApp" );

        System.out.println( "Calling getFortune" );

        String data = theFortuneService.getFortune();

        System.out.println( "\nMy fortune is: " + data );

        System.out.println( "\nFinished!" );

        //Close the context
        context.close();

    }

}
