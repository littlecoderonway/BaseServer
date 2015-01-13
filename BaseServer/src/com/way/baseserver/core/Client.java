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
				//ˢ���������ʹServer�����յ����ַ���
				//System.out.println("Client:"+line);
				//��ϵͳ��׼����ϴ�ӡ������ַ���
				System.out.println("Server:"+ins.readLine());
				//��Server����һ�ַ���������ӡ����׼�����
				line=bf.readLine(); //��ϵͳ��׼�������һ�ַ���
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








