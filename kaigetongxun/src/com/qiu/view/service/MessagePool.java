package com.qiu.view.service;

import java.util.HashMap;
import java.util.LinkedList;

import com.qiu.view.FaceJPanel;
import com.qiu.view.LiaotianFrame;
import com.qiu.view.Msg;
import com.qiu.view.util.Config;

import net.sf.json.JSONObject;

/**
 * ��Ϣ�� ������е���Ϣ���յ�������д洢
 *
 */
public class MessagePool {

	private MessagePool() {
	}

	private static MessagePool messagePool = new MessagePool();

	public static MessagePool getMessagePool() {
		return messagePool;
	}

	public static HashMap<String, LinkedList<Msg>> hashMap = new HashMap();

	// {��type��:��msg��,��toUID��:����,��myUID��:����,��msg��:����,��code��:����}
	// �����Ǹ�˭��Ϣ ��Ӧ���ڳ���洢����
	public void addMessage(String json) {

		JSONObject jsonObject = JSONObject.fromObject(json);
		String toUID = jsonObject.getString("toUID");
		String myUID = jsonObject.getString("myUID");
		String msg = jsonObject.getString("msg");
		String type = jsonObject.getString("type");
		String code = jsonObject.getString("code");

		// �ѽ��պõ���Ϣ ��װ��Msg������
		Msg msgObj = new Msg();
		msgObj.setCode(code);
		msgObj.setMsg(msg);
		msgObj.setMyUID(myUID);
		msgObj.setToUID(toUID);
		msgObj.setType(type);

		try {
			LiaotianFrame liaotianFrame = Config.liaotianTable.get(myUID);
			if (liaotianFrame.isVisible()) { 
				liaotianFrame.addMessage(msgObj);
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			
			
			FaceJPanel faceJPanel= Config.list.get(myUID);
			faceJPanel.addMessage(msgObj);
			

//			// �����ϴ洢Msg���� �Ա����ȡ�������Ϣ
//			LinkedList<Msg> list = hashMap.get(myUID);
//			if (list == null) {
//				list = new LinkedList();
//			}
//			list.add(msgObj);
//
//			hashMap.put(myUID, list);
		}

	}

}
