package com.qiu.instantMessage.server;

import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

/**
 * �����û��б�
 */
public class UserOnlineList {
	private UserOnlineList() {
	}

	private static UserOnlineList userOnlineList = new UserOnlineList();

	public static UserOnlineList getUserOnlineList() {
		return userOnlineList;
	}

	// ���ǰ����е������˻� ȫ���Ǽ��ڼ�����
	/**
	 * String ���û��ı��
	 */
	private HashMap<String, UserInfo> hashMap = new HashMap<String, UserInfo>();

	// ע�������û�
	public void regOnline(String uid, Socket socket, String email, String phoneNumber) {

		// �ж������Ŀͻ����Ƿ��¼һ���û��� ���һ�� ��ǿ��Ū��ȥ
		UserInfo userInfo = hashMap.get(uid);
		if (userInfo != null) {
			try {
				try {
					userInfo.getSocket().getOutputStream().write(4);
				} catch (Exception e) {
				}
				userInfo.getSocket().close();
			} catch (Exception e) {
			}
		}

		userInfo = new UserInfo();
		userInfo.setUid(uid);
		userInfo.setEmail(email);
		userInfo.setPhone(phoneNumber);
		userInfo.setSocket(socket);
		hashMap.put(uid, userInfo);// �Ǽ�����
	}

	/**
	 * ���¿ͻ��˵�UDP��Ϣ
	 * 
	 * @param uid
	 *            �û����
	 * @param ip
	 *            udp IP��ַ
	 * @param port
	 *            udp�˿�
	 * @throws NullPointerException
	 *             ��ָ���쳣
	 */
	public void updateOnlineUDP(String uid, String ip, int port) throws NullPointerException {
		UserInfo userInfo = hashMap.get(uid);
		userInfo.setUdpip(ip);
		userInfo.setUdpport(port);
	}

	// �ж��û��Ƿ����� �����true ���� false ������
	public boolean isUserOnline(String uid) {
		return hashMap.containsKey(uid);
	}

	/**
	 * ��������û���Ϣ
	 * 
	 * @param uid
	 * @return
	 */
	public UserInfo getOnlineUserInfo(String uid) {
		return hashMap.get(uid);
	}

	/**
	 * ������
	 * 
	 * @param uid
	 */
	public void logout(String uid) {
		hashMap.remove(uid);
	}

	/**
	 * ������е�������Ϣ
	 * 
	 * @return
	 */
	public Set<String> getUserInfos() {
		return hashMap.keySet();
	}

}
