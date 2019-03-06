package com.qiu.view.service;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import com.qiu.view.util.Config;

import net.sf.json.JSONObject;

public class MessageRegService extends Thread {

	// ÿ10���� �������ע������һ��
	public void run() {

		String uid = JSONObject.fromObject(Config.geren_json_data).getString("uid");
		String jsonStr = "{\"type\":\"reg\",\"myUID\":\"" + uid + "\"}";
		byte[] bytes = jsonStr.getBytes();

		while (true) {
			try {
				DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length,
						InetAddress.getByName(Config.IP), 4003);

				// ��������Ϣ���͸�������
				client.send(datagramPacket);
				Thread.sleep(9999);
			} catch (Exception e) {
			}
		}

	}

	private DatagramSocket client = null; 
	public MessageRegService(DatagramSocket client) {
		this.client = client;
		this.start();
	}
}
