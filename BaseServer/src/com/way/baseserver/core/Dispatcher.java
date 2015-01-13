package com.way.baseserver.core;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class Dispatcher implements Runnable{
	
	private Selector selector;
	private DispatcherEventHander hander = new DispatcherEventHander();
	private Object guard = new Object();
	
	public void init(){
		try {
			selector = Selector.open();
			hander.setDispatcher(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void register(SocketChannel sc){
		Connection con = new Connection(sc, hander,this);
			try {
				System.out.println("register socket..");
				sc.configureBlocking(false);
				
				synchronized (guard) {
					selector.wakeup();
					sc.register(selector, SelectionKey.OP_READ, con);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	public void announceWriteNeed(Connection con){
		synchronized(guard){
			System.out.println("wants write..");
			selector.wakeup();
			SelectionKey key = con.getSocketChannel().keyFor(selector);
			key.interestOps(SelectionKey.OP_WRITE);
		}
	}
	
	@Override
	public void run() {
		while(true){
			try {
				selector.select();
				System.out.println("selected");
				synchronized (guard) {
					Iterator<SelectionKey> it = selector.selectedKeys().iterator();
					while(it.hasNext()){
						System.out.println("keys..");
						SelectionKey key = it.next();
						it.remove();
						
						if (key.isValid()){
							Connection con = (Connection)key.attachment();
							if(key.isReadable()) {
								System.out.println("read event");
								hander.onReadableEvent(con);
							}else if (key.isWritable()) {
								System.out.println("write event");
								hander.onWriteableEvent(con);
								key.interestOps(SelectionKey.OP_READ);
							}
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}






