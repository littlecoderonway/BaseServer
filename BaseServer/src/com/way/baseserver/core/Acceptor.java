package com.way.baseserver.core;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Acceptor implements Runnable{
	private int port = 9090;
	private Dispatcher dispatcher;
	private ServerSocketChannel serverChannel = null;
	
	public Acceptor(int port, Dispatcher dispatcher) {
		super();
		this.port = port;
		this.dispatcher = dispatcher;
	}

	void init(){
		try {
			serverChannel = ServerSocketChannel.open();
			serverChannel.configureBlocking(true);
			serverChannel.socket().bind(new InetSocketAddress("127.0.0.1", port));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run(){
		while(true){
			try {
				SocketChannel socketChannel = serverChannel.accept();
				System.out.println("connected..");
				dispatcher.register(socketChannel);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
