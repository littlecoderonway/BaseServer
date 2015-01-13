package com.way.baseserver.core;

public class Server {

	public static void main(String[] args) {
		Dispatcher dis = new Dispatcher();
		dis.init();
		
		Acceptor acceptor = new Acceptor(9090, dis);
		acceptor.init();
		
		new Thread(dis).start();
		new Thread(acceptor).start();
	}

}
