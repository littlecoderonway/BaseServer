package com.way.baseserver.core;

import java.nio.ByteBuffer;

public class AppHander {
	public void onData(Connection con){
		while(con.getReaderQueue().size()>0){
			byte[] data = con.getReaderQueue().poll();
			System.out.println("get from client:"+ new String( data));
		}
		con.writeData("hello\n".getBytes());
	}
}
