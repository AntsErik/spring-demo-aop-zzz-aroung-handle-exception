package ee.praktika.aopdemo.service;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

@Component
public class TrafficFortuneService {

    public String getFortune(){

        //simulate a delay
        try {
            TimeUnit.SECONDS.sleep( 5 ); //will sleep for 5 seconds
        }
        catch( InterruptedException e ) {
            e.printStackTrace();
        }

        //return a fortune
        return "Expect heavy traffic!";
    }

    public String getFortune( boolean tripWire ){

        if( tripWire ) {
            throw new RuntimeException( "Major accident! Highway closed." );
        }

        return getFortune();
    }
}
