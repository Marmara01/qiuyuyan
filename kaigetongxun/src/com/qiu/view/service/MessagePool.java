package com.qiu.view.service;

import java.util.HashMap;
import java.util.LinkedList;

import com.qiu.view.FaceJPanel;
import com.qiu.view.LiaotianFrame;
import com.qiu.view.Msg;
import com.qiu.view.util.Config;

import net.sf.json.JSONObject;

/**
 * 消息池 会把所有的消息接收到池里进行存储
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

	// {“type”:”msg”,”toUID”:””,”myUID”:””,”msg”:””,”code”:””}
	// 不管是给谁消息 都应该在池里存储起来
	public void addMessage(String json) {

		JSONObject jsonObject = JSONObject.fromObject(json);
		String toUID = jsonObject.getString("toUID");
		String myUID = jsonObject.getString("myUID");
		String msg = jsonObject.getString("msg");
		String type = jsonObject.getString("type");
		String code = jsonObject.getString("code");

		// 把接收好的消息 包装在Msg对象内
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
			

//			// 链表集合存储Msg对象 以便今后读取里面的消息
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
