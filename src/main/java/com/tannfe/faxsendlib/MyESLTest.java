package com.tannfe.sendfaxlib;

/*
* MyESLTest.java -- 16-Jun 2010
*
* An extremely simple java example of the javamod ESL wrapper for FreeSWITCH. Hopefully this will turn some gears.
* I'd like to get an AJAX version of FOP (Flash Op Panel) and this could/would be the groundwork for it.
*
* Anthony Cosgrove (zorprime)
* acosgrove@aretta.com
* Aretta Communications
* http://www.aretta.com
*
*/

// Be sure to include the esl.jar in your project (I'm using eclipse to develop)
//import org.freeswitch.esl.*;

import org.freeswitch.esl.client.IEslEventListener;
import org.freeswitch.esl.client.transport.event.EslEvent;
import org.freeswitch.esl.client.inbound.Client;
import org.freeswitch.esl.client.inbound.InboundConnectionFailure;


public class MyESLTest {

	public static void main(String [] args) {
	
 			
		/*
		* 
		* Once you get libesljni.so compiled you can either put it in your java library path and
		* use System.loadlibrary or just use System.load with the absolute path.
		*
		*/

		System.load("/usr/lib64/libesljni.so");

		/*
		*
		* Trying to keep this simple (and I'm no java expert) I am instantiating the ESLconnection and
		* ESLevent object in a static reference here so remember if you don't plan on doing everything in main
		* you will need to instantiate your class first or you will get compile-time errors.
		*
		*/

		//OutboundFax.sendFax("14152230433", "19175634528", "Test Fax", "/tmp/txfax.tiff");
		//OutboundFax.sendFax("19175634528", "14152230433", "Test Fax", "/tmp/txfax.tiff");

		GenerateFax genFax = new GenerateFax("14152230433", "19175634528", "/tmp/txfax.tiff", 
													"fax header", "19175634528", "Flowroute", null);
		String faxUUID = genFax.sendFax("127.0.0.1",8021,"ClueCon");
		
		System.out.println("Fax UUID has been returned");

		/*
		Client client = new Client();
		//client.addEventListener(new MyEslEventListener());
		client.connect("127.0.0.1", 8021, "ClueCon", 1);

		//ESLconnection con = new ESLconnection("127.0.0.1","8021","ClueCon");

		//ESLevent evt;

		String cmd = "{fax_ident='14152230433',fax_header='James',origination_caller_id_number=14152230433}sofia/gateway/FlowRoute/14152837190 &txfax('/tmp/txfax.tiff')";
		//String cmd = "api sofia status";

		//evt = con.sendRecv(cmd);
		//EslMessage resp=client.sendSyncApiCommand("originate ", cmd);
		//client.sendSyncApiCommand("originate ", cmd);
		String responseUUID = client.sendAsyncApiCommand("originate ", cmd);

		//System.out.println(evt.serialize("plain"));
		System.out.println("UUID: " + responseUUID);
		//System.out.println(resp);
		*/

		/*
		con.events("plain","all");

		// Loop while connected to the socket -- not sure if this method is constantly updated so may not be a good test
		while (con.connected() == 1) {

			// Get an event - recvEvent will block if nothing is queued
			evt = con.recvEvent();

			// Print the entire event in key : value format. serialize() according to the wiki usually takes no arguments
			// but if you do not put in something you will not get any output so I just stuck plain in.
			System.out.println(evt.serialize("plain"));
		}
		*/

	}
}
