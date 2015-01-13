package com.way.baseserver.core;

import java.nio.channels.SocketChannel;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;
import java.nio.ByteBuffer;


/**
 * 当前链接的抽象，封装socket和hander
 * @author liu
 */
public class Connection {
	private SocketChannel socketChannel;
	private DispatcherEventHander hander;
	private AppHander appHander = new AppHander();
	private Dispatcher dispatcher;
	
	//private ByteBuffer readerBuffer = ByteBuffer.allocate(1024);
	//private ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
	
	private Queue<byte[]> readerQueue = new LinkedBlockingDeque<byte[]>();
	private Queue<ByteBuffer> writeQueue = new LinkedBlockingDeque<ByteBuffer>();
	
	public Connection(SocketChannel sc,DispatcherEventHander hander,Dispatcher dis){
		this.socketChannel = sc;
		this.hander = hander;
		this.dispatcher = dis;
		//this.appHander = appHander;
	}
	
	public void writeData(byte[] data){
		ByteBuffer writeBuffer = ByteBuffer.wrap(data);
		writeQueue.add(writeBuffer);
		dispatcher.announceWriteNeed(this);
	}
		
	public class Data{
		private byte data[];
		private int length;
		
		public Data(byte[] data,int length){
			this.data = data;
			this.length = length;
		}
	}
	
	public SocketChannel getSocketChannel() {
		return socketChannel;
	}


	public DispatcherEventHander getHander() {
		return hander;
	}



	public Queue<byte[]> getReaderQueue() {
		return readerQueue;
	}

	public Queue<ByteBuffer> getWriteQueue() {
		return writeQueue;
	}

	public AppHander getAppHander() {
		return appHander;
	}

	
}
