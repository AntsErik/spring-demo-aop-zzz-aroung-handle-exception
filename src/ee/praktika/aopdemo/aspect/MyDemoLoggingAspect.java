package ee.praktika.aopdemo.aspect;

import java.util.List;
import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import ee.praktika.aopdemo.Account;

@Aspect
@Component
@Order( 4 )
public class MyDemoLoggingAspect {

    private Logger myLogger = Logger.getLogger( getClass().getName() );

    @Around( "execution(* ee.praktika.aopdemo.service.*.getFortune(..))" )
    public Object aroundGetFortune( ProceedingJoinPoint theProceedingJoinPoint ) throws Throwable{

        //print out which method are we advising on
        String method = theProceedingJoinPoint.getSignature().toShortString();
        myLogger.info( "\n======>>> Executing @Around on method: " + method );

        //get the beggining timestamp
        long begin = System.currentTimeMillis();

        //excecute the method
        Object result = null;

        try {
            result = theProceedingJoinPoint.proceed();
        }
        catch( Exception e ) {

            //log the exception
            myLogger.warning( e.getMessage() );

            //rethrow the exception
            throw e;

        }

        //get the ending timestamp
        long end = System.currentTimeMillis();

        //display the duration for the method to excecute
        long duration = end - begin;
        myLogger.info( "\n========>>> Duration: " + duration / 1000.0 + " seconds." ); //translating milliseconds to seconds

        return result;

    }

    @After( "execution(* ee.praktika.aopdemo.dao.AccountDAO.findAccounts(..))" )
    public void afterFinallyFindAccountsAdvice( JoinPoint theJoinPoint ){

        //print out which method are we advising on
        String method = theJoinPoint.getSignature().toShortString();
        myLogger.info( "\n======>>> Executing @After (finally) on method: " + method );
    }

    @AfterThrowing( pointcut = "execution(* ee.praktika.aopdemo.dao.AccountDAO.findAccounts(..))", throwing = "theExc" )
    public void afterThrowingFindAccountsAdvice( JoinPoint theJoinPoint, Throwable theExc ){

        //print out which method we are advising on
        String method = theJoinPoint.getSignature().toShortString();
        myLogger.info( "\n======>>> Executing @AfterThrowing on method: " + method );

        //log the exception
        myLogger.info( "\n======>>> Exceptiong is: " + theExc );
    }

    @AfterReturning( pointcut = "execution(* ee.praktika.aopdemo.dao.AccountDAO.findAccounts(..))", returning = "result" )
    public void afterReturningFindAccountsAdvice(
        JoinPoint theJoinPoint, List<Account> result ){

        //print out which method are we advising on
        String method = theJoinPoint.getSignature().toShortString();
        myLogger.info( "\n======>>> Executing @AfterReturning on method: " + method );

        //print out the result of the method call
        myLogger.info( "\n======>>> result is: " + result );

        //watn to post-process the data and modify it, before it makes back to caller

        //convert the account name to all uppercase
        convertAccountNamesToUpperCase( result );

        myLogger.info( "\n======>>> result is: " + result );

    }

    private void convertAccountNamesToUpperCase( List<Account> result ){

        //loop through accounts
        for( Account tempAccount : result ) {

            //get uppercase version of names
            String theUpperName = tempAccount.getName().toUpperCase();

            //update the name on the account object
            tempAccount.setName( theUpperName );

        }
    }

    @Before( "ee.praktika.aopdemo.aspect.AopExpressions.referencePointcutIgnoreGetSet()" )
    public void beforeAddAccountAdvice( JoinPoint theJoinPoint ){
        myLogger.info( "\n======>>> Executing @Before advice on addAccount() in the DAO package" );

        //display the method signature
        MethodSignature methodSignature = (MethodSignature)theJoinPoint.getSignature();

        myLogger.info( "Method: " + methodSignature );

        //display the method arguments that are being passed in

        //get the arguments
        Object[] args = theJoinPoint.getArgs();

        //looping through the arguments and printing them out
        for( Object tempArg : args ) {
            myLogger.info( tempArg.toString() );

            if( tempArg instanceof Account ) {
                //downcast and print Account specific stuff
                Account theAccount = (Account)tempArg;

                myLogger.info( "Account name: " + theAccount.getName() );
                myLogger.info( "Level name: " + theAccount.getLevel() );
            }
        }
    }
}
