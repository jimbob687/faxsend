package net.freeswitch;
/*
* Copyright 2010 david varnes.
*
* Licensed under the Apache License, version 2.0 (the "License"); 
* you may not use this file except in compliance with the License. 
* You may obtain a copy of the License at:
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS, 
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
* See the License for the specific language governing permissions and
* limitations under the License.
*               */
import java.util.Map.Entry;
import java.util.UUID;

import org.freeswitch.esl.client.IEslEventListener;
import org.freeswitch.esl.client.inbound.InboundConnectionFailure;
import org.freeswitch.esl.client.transport.CommandResponse;
import org.freeswitch.esl.client.transport.SendMsg;
import org.freeswitch.esl.client.transport.event.EslEvent;
import org.freeswitch.esl.client.transport.message.EslHeaders.Name;
import org.freeswitch.esl.client.transport.message.EslMessage;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientTest
{
    private final Logger log = LoggerFactory.getLogger( this.getClass() );

    private String host = "127.0.0.1";
    private int port = 8021;
    private String password = "ClueCon"; 
        
    @Test
    public void do_connect() throws InterruptedException
    {
        Client client = new Client();
     
        client.addEventListener( new IEslEventListener()
        {
            public void eventReceived( EslEvent event )
            {
                log.info( "Event received [{}]", event );
            }
            public void backgroundJobResultReceived( EslEvent event )
            {
                log.info( "Background job result received [{}]", event );
            }
            
        } );
        
        log.info( "Client connecting .." );
        try
        {
            client.connect( host, port, password, 20 );
        }
        catch ( InboundConnectionFailure e )
        {
            log.error( "Connect failed", e );
            return;
        }
        log.info( "Client connected .." );
        
		//      client.setEventSubscriptions( "plain", "heartbeat CHANNEL_CREATE CHANNEL_DESTROY BACKGROUND_JOB" );
		client.setEventSubscriptions( "plain", "all" );
        client.addEventFilter( "Event-Name", "heartbeat" );
        client.cancelEventSubscriptions();
        client.setEventSubscriptions( "plain", "all" );
        client.addEventFilter( "Event-Name", "heartbeat" );
        client.addEventFilter( "Event-Name", "channel_create" );
        client.addEventFilter( "Event-Name", "background_job" );
        client.sendSyncApiCommand( "echo", "Foo foo bar" );
		String uuid1=UUID.randomUUID().toString();
		String uuid2=UUID.randomUUID().toString();
		EslMessage resp=client.sendSyncApiCommand("originate ", "{origination_uuid=" + uuid1 + "}sofia/gateway/mygateway/OUTBOUND+18185551212 1004 park" );
		log.info( "Response to 'park1': [{}]", resp );
		for ( Entry header : resp.getHeaders().entrySet() ) {
			log.info( " * header [{}]", header );
		}
		for ( String bodyLine : resp.getBodyLines() ) {
			log.info( " * body [{}]", bodyLine );
		}

		SendMsg playMsg = new SendMsg(uuid1);
		playMsg.addCallCommand( "execute" );
		playMsg.addExecuteAppName( "speak" );
		playMsg.addExecuteAppArg("cepstral|callie|welcome to java. Welcome to make call");
		CommandResponse cmdresp = client.sendMessage(playMsg);

		resp=client.sendSyncApiCommand("originate ", "{origination_uuid=" + uuid2 + "}sofia/gateway/mygateway/OUTBOUND+17325551212 1004 park" );
		log.info( "Response to 'park2': [{}]", resp );
		for ( Entry header : resp.getHeaders().entrySet() ) {
			log.info( " * header [{}]", header );
		}
		for ( String bodyLine : resp.getBodyLines() ) {
			log.info( " * body [{}]", bodyLine );
		}
		resp=client.sendSyncApiCommand("uuid_bridge ", uuid1 + " " + uuid2 );
		log.info( "Response to 'uuid_bridge': [{}]", resp );
		for ( Entry header : resp.getHeaders().entrySet() ) {
			log.info( " * header [{}]", header );
		}
		for ( String bodyLine : resp.getBodyLines() ) {
			log.info( " * body [{}]", bodyLine );
		}
		//CommandResponse cmd=CommandResponse( bMsg.toString(), resp);
		//client.sendSyncApiCommand("originate", "sofia/gateway/mygateway/OUTBOUND+17325551212 park");
		//client.sendSyncApiCommand("bridge", "sofia/gateway/mygateway/OUTBOUND+18185551212 1004 park" );
		//        client.sendSyncApiCommand( "sofia status", "" );
		String jobId = client.sendAsyncApiCommand( "status", "" );
		log.info( "Job id [{}] for [status]", jobId );
		client.sendSyncApiCommand( "version", "" );
		//        client.sendAsyncApiCommand( "status", "" );
		//        client.sendSyncApiCommand( "sofia status", "" );
		//        client.sendAsyncApiCommand( "status", "" );
		EslMessage response = client.sendSyncApiCommand( "sofia status", "" );
        log.info( "sofia status = [{}]", response.getBodyLines().get( 3 ) );
        
        // wait to see the heartbeat events arrive
        Thread.sleep( 40000 );
        client.close();
    }

    @Test
    public void do_multi_connects() throws InterruptedException {
        Client client = new Client();
        
        log.info( "Client connecting .." );
        try {
            client.connect( host, port, password, 2 );
        }
        catch ( InboundConnectionFailure e ) {
            log.error( "Connect failed", e );
            return;
        }
        log.info( "Client connected .." );
        
        log.info( "Client connecting .." );
        try {
            client.connect( host, port, password, 2 );
        }
        catch ( InboundConnectionFailure e ) {
            log.error( "Connect failed", e );
            return;
        }
        log.info( "Client connected .." );
        
        client.close();
    }
    
    @Test
    public void sofia_contact() {

        Client client = new Client();
        try {
            client.connect( host, port, password, 2 );
        }
        catch ( InboundConnectionFailure e ) {
            log.error( "Connect failed", e );
            return;
        }
        
        EslMessage response = client.sendSyncApiCommand( "sofia_contact", "internal/102@192.xxx.xxx.xxx" );

        log.info( "Response to 'sofia_contact': [{}]", response );
        for ( Entry header : response.getHeaders().entrySet() ) {
            log.info( " * header [{}]", header );
        }
        for ( String bodyLine : response.getBodyLines() ) {
            log.info( " * body [{}]", bodyLine );
        }
        client.close();
    }

}
