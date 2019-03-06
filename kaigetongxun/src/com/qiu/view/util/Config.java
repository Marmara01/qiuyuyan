package com.qiu.view.util;

import java.net.DatagramSocket;
import java.util.Hashtable;
import java.util.Vector;

import com.qiu.view.FaceJPanel;
import com.qiu.view.HaoyouListJPanel;
import com.qiu.view.LiaotianFrame;
import com.qiu.view.Msg;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Config {

	// ��������ַ
	public static final String IP = "192.168.43.130";
	// ��¼�˿�
	public static final int LOGIN_PORT = 4004;
	// ע��˿�
	public static final int REG_PORT = 4002;

	// �û���������Ĵ�
	public static String username;
	public static String password;

	public static HaoyouListJPanel haoyouListJPanel;

	// ������Ϣ�б� JSON
	public static String haoyou_json_data = "";

	// ������Ϣ�б�
	public static String haoyou_liebiao_data = "";

	/**
	 * ȡ�������б�ֵ
	 * 
	 * @param haoyou_json_data
	 */
	public static void jiexi_haoyou_json_data(String haoyou_json_data) {
		Config.haoyou_json_data = haoyou_json_data;
		JSONArray json = JSONArray.fromObject(haoyou_json_data);
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < json.size(); i++) {
			JSONObject jsonobj = (JSONObject) json.get(i);
			stringBuffer.append(jsonobj.getString("uid"));
			stringBuffer.append(",");
		}
		haoyou_liebiao_data = stringBuffer.toString();

	}

	// ��������
	public static String geren_json_data = "";

	// ��������
	public static String haoyou_online = "";

	// UDP���ͺͽ��� �Լ�������
	public static DatagramSocket datagramSocket_client = null;

	// ���촰�ڵǼ�
	public  static Hashtable<String, LiaotianFrame> liaotianTable = new Hashtable<String, LiaotianFrame>();

	// ��ʾ���촰��
	public static void showLiaotianFrame(String uid, String netName, String info,
			String img,Vector<Msg> msgs) {

		if (liaotianTable.get(uid) == null) {
			LiaotianFrame liaotian = new LiaotianFrame(
					uid, netName, img, info,msgs);
			liaotianTable.put(uid, liaotian);
		} else {
			liaotianTable.get(uid).setAlwaysOnTop(true);
			liaotianTable.get(uid).setVisible(true);
		}

	}

	public static void closeLiaotianFrame(String uid) {

		liaotianTable.remove(uid);
	}
	
	//�����б����
	public static Hashtable<String, FaceJPanel> list = new Hashtable();
}
