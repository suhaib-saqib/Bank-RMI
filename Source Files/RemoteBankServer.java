package edu.btp400.w2017.server;
import java.rmi.*;
import java.rmi.server.*;

import com.java.accounts.Chequing;
import com.java.accounts.GIC;
import com.java.accounts.Savings;

public class RemoteBankServer {

   public static void main(String[] args) {

      try {
	   System.out.println( "Starting a server..." );

	   RemoteBankImpl SY = new RemoteBankImpl();
	   SY.addAccount(new Savings("Ryan, Mary","A123456", 450000.00, 0.15));
	   SY.addAccount(new Savings("Doe, John","B723416", 250.00, 0.25));
	   SY.addAccount(new Chequing("Ryan, Mary","C127652", 74000.00, 1.50, 500));
	   SY.addAccount(new Chequing("Doe, John","D726354", 9400.00, 2.50, 10));
	   SY.addAccount(new GIC("Ryan, Mary","E653251", 6000.00, 2, 1.50));
	   SY.addAccount(new GIC("Doe, John","F433251", 15000.00, 4, 2.50));

	   System.out.println( "Binding remote objects to rmi registry" );

	   /*
	   // use of rmi URL
       Naming.rebind( "rmi://localhost:6666/toaster", p1 );   // <name, reference>
                                                              // e.g.("toaster",p1)
       Naming.rebind( "rmi://localhost:6666/microwave", p2 );
	   */
	   
	   // for localhost only
	   java.rmi.registry.Registry registry = 
	        java.rmi.registry.LocateRegistry.createRegistry(5678);
			
       registry.rebind( "SenecaYork", SY );
	   
	   System.out.println( "These remote objects are waiting for clients..." );
      }

      catch( Exception e ) {
	                        System.out.println( "Error: " + e );
      }

      System.out.println( "... bye bye" );
      System.out.println( "... the main thread is put into a wait state!!!" );

      /* a separate thread is started after a remote object has been created */
   }
}
