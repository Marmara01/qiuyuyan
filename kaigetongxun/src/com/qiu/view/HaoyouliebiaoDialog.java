package com.qiu.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import com.qiu.view.util.Config;

import net.sf.json.JSONObject;

/**
 * 好友信息列表
 */
public class HaoyouliebiaoDialog extends JDialog {
	final JLabel myNetname = new JLabel();
	final JLabel myface = new JLabel(new ImageIcon("face/15.png"));
	final JLabel myinfo = new JLabel();

	public void gengxin() {

		// {"back":"","dd":0,"email":"1234561@qq.com","img":"2","info":"不一样的烟火","mm":0,"name":"","netname":"邱","phonenumber":"17807061318","sex":"","uid":"123456789","yy":0}
		JSONObject jsonObject = JSONObject.fromObject(Config.geren_json_data);

		this.setTitle(jsonObject.getString("netname")+" 即时通讯软件");
		
		myNetname.setText(jsonObject.getString("netname"));
		myinfo.setText(jsonObject.getString("info"));
		myface.setIcon(new ImageIcon("face0/" + jsonObject.getString("img") + ".png"));

	}

	public HaoyouliebiaoDialog() {
		super();
		setBounds(100, 100, 246, 743);

		final JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(5, 5));
		getContentPane().add(panel, BorderLayout.NORTH);

		myface.setPreferredSize(new Dimension(55, 55));

		panel.add(myface, BorderLayout.WEST);

		final JPanel panel_1 = new JPanel();
		final BorderLayout borderLayout = new BorderLayout(5, 5);
		panel_1.setLayout(borderLayout);
		panel.add(panel_1, BorderLayout.CENTER);

		myNetname.setFont(new Font("", Font.BOLD, 16));
		myNetname.setText("小球球想静静");
		panel_1.add(myNetname, BorderLayout.CENTER);

		myinfo.setFont(new Font("宋体", Font.PLAIN, 12));
		myinfo.setText("知识的价值不在于占有，而在于使用。");
		panel_1.add(myinfo, BorderLayout.SOUTH);

		final JPanel panel_2 = new JPanel();
		panel_2.setLayout(new BorderLayout());
		getContentPane().add(panel_2, BorderLayout.SOUTH);

		final JPanel panel_3 = new JPanel();
		final FlowLayout flowLayout_1 = new FlowLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		panel_3.setLayout(flowLayout_1);
		panel_2.add(panel_3);

		final JButton button = new JButton();
		button.setText("设置");
		panel_3.add(button);

		final JButton button_2 = new JButton();
		button_2.setText("查找");
		panel_3.add(button_2);

		final JPanel panel_4 = new JPanel();
		final FlowLayout flowLayout = new FlowLayout();
		panel_4.setLayout(flowLayout);
		panel_2.add(panel_4, BorderLayout.EAST);

		final JButton button_1 = new JButton();
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				
				System.exit(0);
			}
		});
		button_1.setText("退出");
		panel_4.add(button_1);

		final JTabbedPane tabbedPane = new JTabbedPane();
		getContentPane().add(tabbedPane, BorderLayout.CENTER);

		final JPanel panel_5 = new JPanel();
		panel_5.setLayout(new BorderLayout());
		tabbedPane.addTab("我的好友", null, panel_5, null);

		final JScrollPane scrollPane = new JScrollPane();
		panel_5.add(scrollPane, BorderLayout.CENTER);
		scrollPane.getViewport().add(new HaoyouListJPanel());
		gengxin();
		//
	}

}
