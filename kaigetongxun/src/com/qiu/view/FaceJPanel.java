package com.qiu.view;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.qiu.view.util.Config;

public class FaceJPanel extends JPanel implements Comparable<FaceJPanel>, MouseListener, Runnable {

	private String image;
	private String netName;
	private String info;
	private String uid;
	private JLabel label_image;
	private JLabel label_netName;
	private JLabel label_info;

	public FaceJPanel(String image, String netName, String info, String uid) {
		this.image = image;
		this.netName = netName;
		this.info = info;
		this.uid = uid;

		this.setLayout(null);

		label_image = new JLabel();
		label_image.setBounds(4, 4, 48, 48);
		add(label_image);

		setImage(image);

		label_netName = new JLabel();
		label_netName.setBounds(58, 4, 478, 24);
		add(label_netName);
		label_netName.setFont(new Font("微软雅黑", Font.BOLD, 14));
		label_netName.setText(netName);

		label_info = new JLabel();
		label_info.setBounds(58, 34, 478, 18);
		add(label_info);
		label_info.setText(info);

		this.addMouseListener(this);

	}

	// 所有的消息
	private Vector<Msg> msgs = new Vector<Msg>();

	boolean run = true;

	public void run() {
		run=true;
		int x = this.getX();
		int y = this.getY();

		while (run) {

			this.setLocation(x - 1, y - 1);
			try {
				Thread.sleep(400);
			} catch (InterruptedException e) {

			}
			this.setLocation(x + 2, y + 2);
			try {
				Thread.sleep(400);
			} catch (InterruptedException e) {

			}
		}

		this.setLocation(x, y);

	}

	private Thread thread = null;

	// 寄存消息
	public void addMessage(Msg msg) {

		msgs.add(msg);// 添加消息进去

		if (thread == null) {
			
			thread = new Thread(this);
			thread.start();
		} else if (thread.getState() == Thread.State.TERMINATED) {
			thread = new Thread(this);
			thread.start();
		} else if (run == false) {
			thread = new Thread(this);
			thread.start();
		}

	}

	public void setImage(String image) {
		if (image.equals("def")) {
			image = "0";
		}
		if (online) {
			label_image.setIcon(new ImageIcon("face0/" + image + ".png"));
		} else {
			label_image.setIcon(new ImageIcon("face1/" + image + ".png"));
		}

		this.image = image;
	}

	public void setNetname(String netName) {
		label_netName.setText(netName);
		this.netName = netName;
	}

	public void setInfo(String info) {
		label_info.setText(info);
		this.info = info;
	}

	int xx = 0;
	int yy = 0;
	private boolean online = false;

	public void setOnline(boolean online) {
		this.online = online;
		if (online) {
			label_image.setIcon(new ImageIcon("face0/" + image + ".png"));
		} else {
			label_image.setIcon(new ImageIcon("face1/" + image + ".png"));
		}
	}

	public int compareTo(FaceJPanel o) {
		if (o.online) {
			return 1;
		} else if (this.online) {
			return -1;
		} else {
			return 0;
		}
	}

	public void mouseClicked(MouseEvent e) {

		if (e.getClickCount() == 2) {
			if (online) {
				run = false;// 终止线程
				Config.showLiaotianFrame(uid, netName, info, image,msgs);
			}
		}

	}

	public void mousePressed(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {
		xx = this.getX();
		yy = this.getY();
		this.setLocation(xx - 3, yy - 3);
	}

	public void mouseExited(MouseEvent e) {
		this.setLocation(xx, yy);
	}

}
