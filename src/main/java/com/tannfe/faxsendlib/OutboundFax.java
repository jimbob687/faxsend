package com.tannfe.faxsendlib;

/*
 * Rewrote this as GenerateFax so this may be deprecated
 */

import org.freeswitch.esl.client.IEslEventListener;
import org.freeswitch.esl.client.transport.event.EslEvent;
import org.freeswitch.esl.client.inbound.Client;
import org.freeswitch.esl.client.inbound.InboundConnectionFailure;


public class OutboundFax {

	public static void sendFax(String srcNumber, String destNumber,String faxHeader, String fileNamePath) {

		Client client = null;
	
		try {
 			
			/*
			* Once you get libesljni.so compiled you can either put it in your java library path and
			* use System.loadlibrary or just use System.load with the absolute path.
			*/

			System.load("/usr/lib64/libesljni.so");


			client = new Client();
			client.connect("127.0.0.1", 8021, "ClueCon", 1);

			String cmd = "{fax_ident='" + srcNumber + "',fax_header='" + faxHeader + "',origination_caller_id_number=" + srcNumber +
					"}sofia/gateway/FlowRoute/" + destNumber + " &txfax('" + fileNamePath + "')";

			System.out.println(cmd);

			String responseUUID = client.sendAsyncApiCommand("originate ", cmd);

			System.out.println("UUID: " + responseUUID);

		}
		catch(InboundConnectionFailure e) {
			//logger.error("Error, InboundConnectionFailure attempting to send fax", e);
			System.out.println("Error, InboundConnectionFailure attempting to send fax");
		}
		catch(Exception e) {
			//logger.error("Error, Exception attempting to send fax", e);
			System.out.println("Error, Exception attempting to send fax");
		}
		finally {
			try {
				client.close();
			}
			catch(Exception e) {
				//logger.error("Error, Exception attempting to close client connection to freeswitch", e);
				System.out.println("Error, Exception attempting to close client connection to freeswitch");
			}
		}

	}
}
