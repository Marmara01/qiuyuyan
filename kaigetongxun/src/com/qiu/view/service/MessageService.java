package com.qiu.view.service;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import com.qiu.view.util.Config;

import net.sf.json.JSONObject;

/**
 * 接收服务器的中转消息
 *
 */
public class MessageService extends Thread {

	public void run() {
		while (true) {
			try {
				byte[] bytes = new byte[1024 * 32];
				DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length);
				client.receive(datagramPacket);

				
				MessagePool
				.getMessagePool()
				.addMessage(
						new String(datagramPacket.getData(), 
								0, datagramPacket.getData().length));
 

			} catch (Exception e) {
			}
		}

	}

	private DatagramSocket client = null;

	public MessageService(DatagramSocket client) {
		this.client = client;
		this.start();
	}
}
