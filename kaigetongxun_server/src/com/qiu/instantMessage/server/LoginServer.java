package com.qiu.instantMessage.server;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.qiu.instantMessage.db.PasswordException;
import com.qiu.instantMessage.db.StateException;
import com.qiu.instantMessage.db.UserInfo2;
import com.qiu.instantMessage.db.UserService;
import com.qiu.instantMessage.db.UsernameNotFoundException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * ��¼������ ��Ҫ�Ǹ����¼
 */
public class LoginServer implements Runnable {

	private Socket socket = null;

	public LoginServer(Socket socket) {
		this.socket = socket;
	}

	// �̷߳���
	public void run() {
		String uid = null;
		// ��¼����
		InputStream in = null;
		OutputStream out = null;
		try {
			in = socket.getInputStream();
			out = socket.getOutputStream();

			// �ȴ��ͻ�����Ϣ
			byte[] bytes = new byte[1024];
			int len = in.read(bytes);
			String json_str = new String(bytes, 0, len);
			//////////////////////////////////////////// {��username��:��17807061318��,��password��:��123456��}
			JSONObject json = JSONObject.fromObject(json_str);// ����
			String username = json.getString("username");
			String password = json.getString("password");

			boolean type = false;
			// �ж��ǲ����ֻ�����
			if (username.trim().length() == 11 && username.indexOf("@") <= -1) {
				try {
					// �ж��ֻ������ǲ��Ǵ�����
					Long.parseLong(username);
					type = true;
				} catch (NumberFormatException e) {
					out.write("{\"state\":4,\"msg\":\"δ֪����!\"}".getBytes());
					out.flush();
					return;
				}
			} else {// ���ֻ�����
				type = false;
			}

			try {

				if (type == true) {// �ֻ�����
					uid = new UserService().loginForPhone(username, password);
					// �Ǽǵ�¼��Ϣ
					UserOnlineList.getUserOnlineList().regOnline(uid, socket, null, username);
				} else {// ������ʽ��¼
					uid = new UserService().loginForEmail(username, password);
					// �Ǽǵ�¼��Ϣ
					UserOnlineList.getUserOnlineList().regOnline(uid, socket, username, null);
				}

				out.write("{\"state\":0,\"msg\":\"��¼�ɹ�!\"}".getBytes());
				out.flush();
				// ½½�����Ľ��տͻ��˷��͵�ָ��Ҫ��
				while (true) {
					bytes = new byte[2048];
					len = in.read(bytes);
					String command = new String(bytes, 0, len);
					if (command.equals("U0001")) { // ���º����б�

						Vector<com.qiu.instantMessage.db.UserInfo> userinfos = new UserService().getHaoyouliebiao(uid);
						out.write(JSONArray.fromObject(userinfos).toString().getBytes());
						out.flush();

					} else if (command.equals("U0002")) {// ���º�������
						out.write(1);
						out.flush();
						// ��ú��ѵ��б���
						len = in.read(bytes);// 1324564,12346546,123456456,2346546,2456456,1237489,137687
						String str = new String(bytes, 0, len);
						String[] ids = str.split(",");

						StringBuffer stringBuffer = new StringBuffer();
						for (String string : ids) {
							if (UserOnlineList.getUserOnlineList().isUserOnline(string)) {
								stringBuffer.append(string);
								stringBuffer.append(",");
							}
						}
						if (stringBuffer.length() == 0) {
							// û�к�������
							out.write("notFound".getBytes());
							out.flush();
						} else {
							// ��ִ���������б�
							out.write(stringBuffer.toString().getBytes());
							out.flush();
						}

					} else if (command.equals("U0003")) { // ���¸�������

						UserInfo2 userinfo2 = new UserService().getUserinfo(uid);
						out.write(JSONObject.fromObject(userinfo2).toString().getBytes());
						out.flush();

					} else if (command.equals("E0001")) {// �޸ĸ�������

					} else if (command.equals("EXIT")) {// �˳��û���¼
						UserOnlineList.getUserOnlineList().logout(uid);
						return;
					}

				}

			} catch (UsernameNotFoundException e) {
				out.write("{\"state\":2,\"msg\":\"�˻�������!\"}".getBytes());
				out.flush();
				return;
			} catch (PasswordException e) {
				out.write("{\"state\":1,\"msg\":\"�û��������!\"}".getBytes());
				out.flush();
				return;
			} catch (StateException e) {
				out.write("{\"state\":3,\"msg\":\"�˻�����!\"}".getBytes());
				out.flush();
				return;
			} catch (SQLException e) {
				out.write("{\"state\":4,\"msg\":\"δ֪����!\"}".getBytes());
				out.flush();
				return;
			}

		} catch (Exception e) {

		} finally {
			// ����������ӹر�
			try {
				// �������ͻȻ�رջ����ǹرյĻ� ������Ҫ���б���ȥ�����˻�
				UserOnlineList.getUserOnlineList().logout(uid);
				in.close();
				out.close();
				socket.close();
			} catch (Exception e2) {
			}
		}

	}

	public static void openServer() throws Exception {
		// �̳߳�
		ExecutorService execute = Executors.newFixedThreadPool(2000);
		// ������ TCP 4001 �˿� ���ڵ�¼ҵ��
		ServerSocket server = new ServerSocket(4004);
		// ��ѭ����Ŀ���ǿ������޷���
		while (true) {
			Socket socket = server.accept();
			socket.setSoTimeout(10000);
			execute.execute(new LoginServer(socket));
		}

	}

	public static void main(String[] args) {
		try {
			openServer();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
