package com.qiu.view;

import java.awt.EventQueue;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GerenziliaoFrame extends JFrame {

	private JTextArea textArea;
	private JComboBox comboBox_3;
	private JComboBox comboBox_2;
	private JComboBox comboBox;
	private JTextField textField_2;
	private JTextField textField_1;
	private JTextField textField;
	/**
	 * Launch the application
	 * @param args
	 */
	public static void main(String args[]) {
		
//		test.Main.main(args);
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
//		test.Main.main(args);

		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GerenziliaoFrame frame = new GerenziliaoFrame();
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
	public GerenziliaoFrame() {
		super();
		setTitle("个人资料");
		setResizable(false);
		getContentPane().setLayout(null);
		setBounds(100, 100, 482, 399);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		final JLabel label = new JLabel();
		label.setBounds(10, 10, 63, 63);
		getContentPane().add(label);

		textField = new JTextField();
		textField.setBounds(79, 10, 385, 35);
		getContentPane().add(textField);

		textField_1 = new JTextField();
		textField_1.setBounds(79, 51, 385, 22);
		getContentPane().add(textField_1);

		final JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new TitledBorder(null, "个人资料", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
		panel.setBounds(10, 94, 454, 265);
		getContentPane().add(panel);

		final JLabel label_1 = new JLabel();
		label_1.setText("真实名字:");
		label_1.setBounds(10, 37, 66, 18);
		panel.add(label_1);

		final JLabel label_2 = new JLabel();
		label_2.setText("性别:");
		label_2.setBounds(236, 37, 66, 18);
		panel.add(label_2);

		textField_2 = new JTextField();
		textField_2.setBounds(75, 35, 132, 22);
		panel.add(textField_2);

		final JRadioButton radioButton = new JRadioButton();
		radioButton.setText("男");
		radioButton.setBounds(279, 33, 38, 26);
		panel.add(radioButton);

		final JRadioButton radioButton_1 = new JRadioButton();
		radioButton_1.setText("女");
		radioButton_1.setBounds(323, 33, 38, 26);
		panel.add(radioButton_1);

		final JLabel label_3 = new JLabel();
		label_3.setText("出生年月:");
		label_3.setBounds(10, 76, 66, 18);
		panel.add(label_3);

		comboBox = new JComboBox();
		comboBox.setBounds(75, 72, 66, 27);
		panel.add(comboBox);

		comboBox_2 = new JComboBox();
		comboBox_2.setBounds(179, 72, 47, 27);
		panel.add(comboBox_2);

		comboBox_3 = new JComboBox();
		comboBox_3.setBounds(264, 72, 47, 27);
		panel.add(comboBox_3);

		final JLabel label_4 = new JLabel();
		label_4.setText("年");
		label_4.setBounds(147, 76, 22, 18);
		panel.add(label_4);

		final JLabel label_5 = new JLabel();
		label_5.setText("月");
		label_5.setBounds(236, 76, 22, 18);
		panel.add(label_5);

		final JLabel label_6 = new JLabel();
		label_6.setText("日");
		label_6.setBounds(323, 76, 22, 18);
		panel.add(label_6);

		final JLabel label_7 = new JLabel();
		label_7.setText("备　　注:");
		label_7.setBounds(10, 116, 66, 18);
		panel.add(label_7);

		final JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(75, 116, 358, 133);
		panel.add(scrollPane);

		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		//
	}

}
