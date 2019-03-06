package com.qiu.instantMessage.server;

public class Start {

	public static void main(String[] args) {
		new Thread() {

			public void run() {
				try {
					System.out.println("登录服务器启动成功!");
					LoginServer.openServer();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
		}.start();

		new Thread() {

			public void run() {
				try {
					System.out.println("注册服务器启动成功!");
					RegServer.openServer();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
		}.start();
		

		new Thread() {

			public void run() {
				try {
					System.out.println("信息中转服务器启动成功!");
					UDPMessageServer.openServer();
				
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
		}.start();
	}

}
