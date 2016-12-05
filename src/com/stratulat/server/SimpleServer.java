/**
 * 
 */
package com.stratulat.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Remus Stratulat
 *
 */
public class SimpleServer {
	
	private Logger logger;

	private AsynchronousServerSocketChannel serverSocket;

	/**
	 * Creates a SimpleServer listening on the default port 8080.
	 */
	public SimpleServer() {
		this(8080);
	}

	/**
	 * Creates a SimpleServer listening on a specific port. 
	 * 
	 * @param port the port binding for this server. 
	 */
	public SimpleServer(int port) {
		logger = Logger.getLogger("global");

		try {
			AsynchronousChannelGroup group = AsynchronousChannelGroup.withThreadPool(Executors
		            .newSingleThreadExecutor());
			serverSocket = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(port));
			
			logger.log(Level.INFO, "server is accepting connection on port: " + port);
			 
			serverSocket.accept(null, new CompletionHandler<AsynchronousSocketChannel,Void>() {
			      public void completed(AsynchronousSocketChannel ch, Void att) {
			          // accept the next connection
			    	  serverSocket.accept(null, this);

			          // handle this connection
			          handle(ch);
			      }
			      
			      public void failed(Throwable exc, Void att) {
			    	  logger.log(Level.INFO, "Connection failed!");
			      }
			      
			      public void handle(AsynchronousSocketChannel ch) {
			    	  logger.log(Level.INFO, "handling connection: ");
			    	  try {
			    		ByteBuffer buffer = ByteBuffer.allocate(256);
			    		Future<Integer> result = ch.read(buffer);
			    		int code = result.get();
			    		
			    		String message = new String(buffer.array()).trim();
			    		logger.log(Level.INFO, "got from the channel: " + code + ":\n" + message);
			    		
						ch.close();
					} catch (IOException | InterruptedException | ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			      }
			});
			
			// wait until group.shutdown()/shutdownNow(), or the thread is interrupted:
			group.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);

		} catch (IOException | InterruptedException e) {
			// TODO log this in a proper way. 
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		SimpleServer testServer = new SimpleServer(4444);
	}

	public AsynchronousServerSocketChannel getServerSocket() {
		return serverSocket;
	}

	public void setServerSocket(AsynchronousServerSocketChannel serverSocket) {
		this.serverSocket = serverSocket;
	}

}
