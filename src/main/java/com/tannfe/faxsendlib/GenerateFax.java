package com.tannfe.sendfaxlib;

import java.util.HashMap;

import org.freeswitch.esl.*;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import org.freeswitch.esl.client.IEslEventListener;
import org.freeswitch.esl.client.transport.event.EslEvent;
import org.freeswitch.esl.client.inbound.Client;
import org.freeswitch.esl.client.inbound.InboundConnectionFailure;

public class GenerateFax {

	static Logger logger = Logger.getLogger(GenerateFax.class.getName());

	private static String toNumber = null;
	private static String fromNumber = null;
	private static String fileFullPath = null;
	private static String faxHeader = null;
	private static String callerID = null;
	private static String gatewayName = null;
	private static Long faxSendID = null;


	/**
	 * Constructor for class
	 * @param toNumber		The number to send a fax to
	 * @param fromNumber	The number to send the fax from
	 * @param fileFullPath	The path to the file that we are sending, must be a tiff
	 * @param faxHeader		Header to put on the fax
	 * @param callerID		Number that we should show the call as coming from
	 * @param gatewayName	Name of the gateway that freeswitch will interpret, e.g. Flowroute
	 * @param faxSendID		ID of the fax that we are seeing that should have been recorded in the db, could be null if we are only doing a test from main
	 */
	public GenerateFax(String toNumber, String fromNumber, String fileFullPath, 
							String faxHeader, String callerID, String gatewayName, Long faxSendID) {

		toNumber = this.toNumber;
		fromNumber = this.fromNumber;
		fileFullPath = this.fileFullPath;
		faxHeader = this.faxHeader;
		callerID = this.callerID;
		gatewayName = this.gatewayName;
		faxSendID = this.faxSendID;
	}


	/**
	 * Create a connection to freeswitch, generate the fax and wait to get information back regarding the UUID for the fax
	 * @param fsAddress		Address of the freeswitch server
	 * @param fsPort		Port that the freeswitch server is listening on
	 * @param fsPasswd		Password of the freeswitch server
	 * @return 				UUID of the fax that freeswitch has generated
	 */
 	public static String sendFax(String fsAddress, Integer fsPort, String fsPasswd) {
 			
		String responseUUID = null;

		try {

			System.load("/usr/lib64/libesljni.so");

			//Client client = new Client();
			MyESLClient client = new MyESLClient();
			client.addEventListener(new MyEslEventListener());
			client.connect(fsAddress, fsPort, fsPasswd, 1);

			String cmd = "{fax_ident='" + toNumber + "',fax_header='" + faxHeader  + "',origination_caller_id_number=" + callerID + "}sofia/gateway/" + gatewayName + "/" + fromNumber + " &txfax('" + fileFullPath + "')";

			responseUUID = client.sendAsyncApiCommand("originate ", cmd);

			logger.info("Fax UUID: " + responseUUID);
			
			client.close();

			client = null;
		}
		catch(Exception e) {
			logger.error("Error, Exception attempting to parse the ESL event message", e);
			responseUUID = null;
		}

		return responseUUID;
	}


}




