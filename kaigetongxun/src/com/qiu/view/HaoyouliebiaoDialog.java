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
 * ������Ϣ�б�
 */
public class HaoyouliebiaoDialog extends JDialog {
	final JLabel myNetname = new JLabel();
	final JLabel myface = new JLabel(new ImageIcon("face/15.png"));
	final JLabel myinfo = new JLabel();

	public void gengxin() {

		// {"back":"","dd":0,"email":"1234561@qq.com","img":"2","info":"��һ�����̻�","mm":0,"name":"","netname":"��","phonenumber":"17807061318","sex":"","uid":"123456789","yy":0}
		JSONObject jsonObject = JSONObject.fromObject(Config.geren_json_data);

		this.setTitle(jsonObject.getString("netname")+" ��ʱͨѶ���");
		
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
		myNetname.setText("С�����뾲��");
		panel_1.add(myNetname, BorderLayout.CENTER);

		myinfo.setFont(new Font("����", Font.PLAIN, 12));
		myinfo.setText("֪ʶ�ļ�ֵ������ռ�У�������ʹ�á�");
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
		button.setText("����");
		panel_3.add(button);

		final JButton button_2 = new JButton();
		button_2.setText("����");
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
		button_1.setText("�˳�");
		panel_4.add(button_1);

		final JTabbedPane tabbedPane = new JTabbedPane();
		getContentPane().add(tabbedPane, BorderLayout.CENTER);

		final JPanel panel_5 = new JPanel();
		panel_5.setLayout(new BorderLayout());
		tabbedPane.addTab("�ҵĺ���", null, panel_5, null);

		final JScrollPane scrollPane = new JScrollPane();
		panel_5.add(scrollPane, BorderLayout.CENTER);
		scrollPane.getViewport().add(new HaoyouListJPanel());
		gengxin();
		//
	}

}
