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
 */
package com.tannfe.sendfaxlib;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.freeswitch.esl.client.IEslEventListener;
import org.freeswitch.esl.client.internal.IEslProtocolListener;
import org.freeswitch.esl.client.transport.CommandResponse;
import org.freeswitch.esl.client.transport.SendMsg;
import org.freeswitch.esl.client.transport.event.EslEvent;
import org.freeswitch.esl.client.transport.message.EslMessage;
import org.freeswitch.esl.client.inbound.Client;
import org.freeswitch.esl.client.inbound.InboundClientHandler;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Entry point to connect to a running FreeSWITCH Event Socket Library module, as a client.
 * <p>
 * This class provides what the FreeSWITCH documentation refers to as an 'Inbound' connection
 * to the Event Socket module. That is, with reference to the socket listening on the FreeSWITCH
 * server, this client occurs as an inbound connection to the server.
 * <p>
 * See <a href="http://wiki.freeswitch.org/wiki/Mod_event_socket">http://wiki.freeswitch.org/wiki/Mod_event_socket</a>
 * 
 * @author  david varnes
 */
public class MyESLClient extends Client {

    private final Logger log = LoggerFactory.getLogger( this.getClass() );
    
    private Channel channel;


    /**
     * Close the socket connection
     * 
     * @return a {@link CommandResponse} with the server's response.
     */
    public CommandResponse close()
    {
        checkConnected();
        InboundClientHandler handler = (InboundClientHandler)channel.getPipeline().getLast();
        EslMessage response = handler.sendSyncSingleLineCommand( channel, "exit" );

        channel.disconnect();
		channel = null;

		//eventListenerExecutor.shutdown();

        return new CommandResponse( "exit", response );
    }

    private void checkConnected() {
        if ( ! canSend() )
        {
            throw new IllegalStateException( "Not connected to FreeSWITCH Event Socket" );
        }
    }
    
}
