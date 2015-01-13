package com.way.baseserver.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Socket socket = new Socket(InetAddress.getLocalHost(),9090);
			BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
			PrintWriter outs = new PrintWriter(socket.getOutputStream());
			BufferedReader ins = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			String line;
			line = bf.readLine();
			while(!"bye".equals(line)){
				outs.println(line);
				outs.flush();
				//刷新输出流，使Server马上收到该字符串
				//System.out.println("Client:"+line);
				//在系统标准输出上打印读入的字符串
				System.out.println("Server:"+ins.readLine());
				//从Server读入一字符串，并打印到标准输出上
				line=bf.readLine(); //从系统标准输入读入一字符串
			}
			
			bf.close();
			outs.close();
			ins.close();
			socket.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}








