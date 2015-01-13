package com.way.baseserver.core;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DispatcherEventHander {
	private ExecutorService worker = Executors.newCachedThreadPool();
	
	private Dispatcher dispatcher;
	
	void onReadableEvent(final Connection con){
		ByteBuffer readBuffer = ByteBuffer.allocate(1024);
		try {
			int length = con.getSocketChannel().read(readBuffer);
			if (length>0) {
				byte[] data = new byte[length];
				System.arraycopy(readBuffer.array(), 0, data, 0, length);
				con.getReaderQueue().add(data);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (con.getReaderQueue().size()>0) {
			worker.execute(new Runnable() {
				@Override
				public void run() {
					con.getAppHander().onData(con);
				}
			});
		}
	}
	
	void onWriteableEvent(Connection con){
		if (con.getWriteQueue().size()>0) {
			//dispatcher.
//			ByteBuffer[] data = new ByteBuffer[con.getWriteQueue().size()]; 
//			con.getWriteQueue().toArray(data);
//			con.getWriteQueue().clear();
//			try {
//				System.out.println("write..");
//				con.getSocketChannel().write(data);
//				//con.getSocketChannel().
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
			
			while (!con.getWriteQueue().isEmpty()) {
				System.out.println("write..");
		        ByteBuffer buf = (ByteBuffer) con.getWriteQueue().poll();
		        try {
					con.getSocketChannel().write(buf);
				} catch (IOException e) {
					e.printStackTrace();
				}
		        if (buf.remaining() > 0) {
		        	break;
		        }
		      }
			System.out.println("write over..");
		}
	}

	public void setDispatcher(Dispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}
	
	
}
