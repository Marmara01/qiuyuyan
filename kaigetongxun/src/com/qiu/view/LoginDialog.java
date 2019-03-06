package com.qiu.view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import com.qiu.util.WindowXY;
import com.qiu.view.service.NetService;
import com.qiu.view.util.Config;

import net.sf.json.JSONObject;

public class LoginDialog extends JDialog implements WindowListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4595774253347573680L;
	private JPasswordField reg_password2;
	private JPasswordField reg_password1;
	private JTextField code;
	private JTextField reg_username;
	private JPasswordField password;
	private JTextField username;

	/**
	 * Launch the application
	 * 
	 * @param args
	 */
	public static void main(String args[]) {

		// test.Main.main(args);
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		// test.Main.main(args);

		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginDialog frame = new LoginDialog();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame
	 */
	public LoginDialog() {
		super();
		setTitle("InstantMessage");
		setResizable(false);
		setAlwaysOnTop(true);// 一直显示在最上面
		getContentPane().setLayout(null);
		setBounds(100, 100, 293, 314);// 646 314

		setLocation(WindowXY.getXY(this.getSize()));

		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		final JLabel label = new JLabel();
		label.setText("手机号:");
		label.setBounds(10, 102, 65, 24);
		getContentPane().add(label);

		final JLabel emailLabel = new JLabel();
		emailLabel.setText("Email:");
		emailLabel.setBounds(10, 123, 65, 24);
		getContentPane().add(emailLabel);

		username = new JTextField();
		username.setBounds(55, 99, 219, 48);
		getContentPane().add(username);

		final JLabel label_1 = new JLabel();
		label_1.setText("密　码:");
		label_1.setBounds(10, 186, 65, 18);
		getContentPane().add(label_1);

		password = new JPasswordField();
		password.setBounds(55, 171, 219, 48);
		getContentPane().add(password);

		final JButton loginbutton = new JButton();
		loginbutton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				// 用户名和密码
				String username_str = username.getText().trim();
				String password_str = password.getText().trim();
				if (username_str.trim().equals("") || password_str.trim().equals("")) {
					javax.swing.JOptionPane.showMessageDialog(LoginDialog.this, "用户名和密码必须填写!");
					return;
				}
				Config.username = username_str;
				Config.password = password_str;
				try {
					JSONObject json = NetService.getNetService().login();

					if (json.getInt("state") == 0) {

						// 登录成功后 显示好友列表
						new HaoyouliebiaoDialog().setVisible(true);
						LoginDialog.this.setVisible(false);
						LoginDialog.this.dispose();

					} else {
						javax.swing.JOptionPane.showMessageDialog(LoginDialog.this, json.getString("msg"));
					}

				} catch (Exception e1) {
					e1.printStackTrace();
					javax.swing.JOptionPane.showMessageDialog(LoginDialog.this, "网络连接失败!");
				}
			}
		});

		loginbutton.setText("登　录");
		loginbutton.setBounds(177, 225, 97, 51);
		getContentPane().add(loginbutton);

		final JButton button_1 = new JButton();
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {

				if (LoginDialog.this.getHeight() == 646) {// 646 314
					LoginDialog.this.setSize(293, 314);
				} else {
					LoginDialog.this.setSize(293, 646);
				}
				setLocation(WindowXY.getXY(LoginDialog.this.getSize()));

			}
		});
		button_1.setText("注　册");
		button_1.setBounds(10, 225, 97, 51);
		getContentPane().add(button_1);

		final JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new TitledBorder(null, "注册用户", TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		panel.setBounds(10, 306, 264, 271);
		getContentPane().add(panel);

		final JLabel label_2 = new JLabel();
		label_2.setText("手  机  号:");
		label_2.setBounds(10, 33, 65, 18);
		panel.add(label_2);

		final JLabel emailLabel_1 = new JLabel();
		emailLabel_1.setText("　 Email:");
		emailLabel_1.setBounds(10, 52, 65, 18);
		panel.add(emailLabel_1);

		reg_username = new JTextField();
		reg_username.setBounds(63, 27, 180, 43);
		panel.add(reg_username);

		final JButton button_2 = new JButton();
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {

				if (reg_username.getText().trim().equals("")) {
					javax.swing.JOptionPane.showMessageDialog(LoginDialog.this, "用户名不能为空!");
					return;
				}
				try {
					Socket socket = new Socket(Config.IP, Config.REG_PORT);
					InputStream input = socket.getInputStream();
					OutputStream output = socket.getOutputStream();

					output.write(("{\"type\":\"code\",\"username\":\"" + reg_username.getText() + "\"}").getBytes());
					output.flush();

					byte[] bytes = new byte[1024];
					int len = input.read(bytes);
					String str = new String(bytes, 0, len);
					JSONObject json = JSONObject.fromObject(str);
					if (json.getInt("state") == 0) {
						javax.swing.JOptionPane.showMessageDialog(LoginDialog.this, "发送成功！");
					} else {
						javax.swing.JOptionPane.showMessageDialog(LoginDialog.this, "发送失败！有可能你的手机号码或者email写错了!");
					}

					input.close();
					output.close();
					socket.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}

			}
		});
		button_2.setText("发送验证");
		button_2.setBounds(146, 76, 97, 30);
		panel.add(button_2);

		code = new JTextField();
		code.setBounds(63, 113, 85, 43);
		panel.add(code);

		final JLabel label_3 = new JLabel();
		label_3.setText("验  证  码:");
		label_3.setBounds(10, 125, 65, 18);
		panel.add(label_3);

		reg_password1 = new JPasswordField();
		reg_password1.setBounds(63, 162, 180, 43);
		panel.add(reg_password1);

		reg_password2 = new JPasswordField();
		reg_password2.setBounds(63, 211, 180, 43);
		panel.add(reg_password2);

		final JLabel label_4 = new JLabel();
		label_4.setText("密　　码:");
		label_4.setBounds(10, 174, 65, 18);
		panel.add(label_4);

		final JLabel label_5 = new JLabel();
		label_5.setText("确认密码:");
		label_5.setBounds(10, 223, 65, 18);
		panel.add(label_5);

		final JButton button_3 = new JButton();
		button_3.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(final ActionEvent e) {
				if (reg_username.getText().trim().equals("")) {
					javax.swing.JOptionPane.showMessageDialog(LoginDialog.this, "用户名不能为空!");
					return;
				}
				if (reg_password1.getText().trim().equals("")) {
					javax.swing.JOptionPane.showMessageDialog(LoginDialog.this, "密码不能为空!");
					return;
				}
				if (reg_password2.getText().trim().equals("")) {
					javax.swing.JOptionPane.showMessageDialog(LoginDialog.this, "确认密码不能为空!");
					return;
				}
				if (code.getText().trim().equals("")) {
					javax.swing.JOptionPane.showMessageDialog(LoginDialog.this, "验证码不能为空!");
					return;
				}
				if (!reg_password1.getText().trim().equals(reg_password2.getText())) {
					javax.swing.JOptionPane.showMessageDialog(LoginDialog.this, "两次密码不相等!");
					return;
				}
				try {
					Socket socket = new Socket(Config.IP, Config.REG_PORT);
					InputStream input = socket.getInputStream();
					OutputStream output = socket.getOutputStream();

					output.write(("{\"type\":\"reg\",\"username\":\"" + reg_username.getText() + "\",\"password\":\""
							+ reg_password1.getText() + "\",\"code\":\"" + code.getText() + "\"}").getBytes());
					output.flush();

					byte[] bytes = new byte[1024];
					int len = input.read(bytes);
					String str = new String(bytes, 0, len);
					JSONObject json = JSONObject.fromObject(str);
					if (json.getInt("state") == 0) {
						javax.swing.JOptionPane.showMessageDialog(LoginDialog.this, "恭喜您!注册成功！可以登录了！");
						reg_username.setText("");
						reg_password1.setText("");
						reg_password2.setText("");
						code.setText("");
					} else if (json.getInt("state") == 1) {
						javax.swing.JOptionPane.showMessageDialog(LoginDialog.this, "用户名已存在!");
					} else if (json.getInt("state") == 2) {
						javax.swing.JOptionPane.showMessageDialog(LoginDialog.this, "验证码错误，请重新获得!");
					} else if (json.getInt("state") == 3) {
						javax.swing.JOptionPane.showMessageDialog(LoginDialog.this, "未知错误!");
					}

					input.close();
					output.close();
					socket.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}

			}
		});
		button_3.setText("注册用户");
		button_3.setBounds(177, 583, 97, 30);
		getContentPane().add(button_3);

		final JButton button_4 = new JButton();
		button_4.setText("放弃");
		button_4.setBounds(10, 583, 97, 30);
		getContentPane().add(button_4);
		//
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

}
