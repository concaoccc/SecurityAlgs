package UI;

import java.awt.EventQueue;

import javax.management.RuntimeErrorException;
import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JTextArea;
import java.awt.GridBagConstraints;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JScrollBar;
import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.border.BevelBorder;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;

public class MainUI {

	private JFrame frame;
	private JTextField keyValue;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainUI window = new MainUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		//����Frame
		frame = new JFrame("��Ϣ����ϵͳV1.0");
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		/*
		 * �������
		 */
		JPanel setting = new JPanel();
		setting.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		setting.setBounds(10, 10, 197, 495);
		frame.getContentPane().add(setting);
		setting.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("\u8BBE\u7F6E");
		lblNewLabel_1.setFont(new Font("����", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(66, 10, 52, 24);
		setting.add(lblNewLabel_1);
		//�ԳƼ����㷨��ѡ��
		JLabel lblNewLabel_2 = new JLabel("\u5BF9\u79F0\u52A0\u5BC6\u7B97\u6CD5\u9009\u62E9\uFF1A");
		lblNewLabel_2.setFont(new Font("����", Font.PLAIN, 14));
		lblNewLabel_2.setBounds(10, 44, 160, 35);
		setting.add(lblNewLabel_2);		
		//DES��AES��ѡ��
		//DES
		JRadioButton rdbtnDes = new JRadioButton("DES");
		rdbtnDes.setFont(new Font("����", Font.PLAIN, 14));
		rdbtnDes.setBounds(10, 76, 52, 23);
		setting.add(rdbtnDes);
		//AES
		JRadioButton rdbtnAes = new JRadioButton("AES");
		rdbtnAes.setFont(new Font("����", Font.PLAIN, 14));
		rdbtnAes.setBounds(91, 76, 52, 23);
		setting.add(rdbtnAes);
		//�ԳƼ��ܵ�ģʽ
		JLabel label_1 = new JLabel("\u5BF9\u79F0\u52A0\u5BC6\u6A21\u5F0F\u9009\u62E9\uFF1A");
		label_1.setFont(new Font("����", Font.PLAIN, 14));
		label_1.setBounds(10, 105, 160, 35);
		setting.add(label_1);
		//�ԳƼ���ģʽ��ѡ��
		//CBC
		JRadioButton rdbtnCbc = new JRadioButton("CBC");
		rdbtnCbc.setFont(new Font("����", Font.PLAIN, 14));
		rdbtnCbc.setBounds(10, 146, 52, 23);
		setting.add(rdbtnCbc);
		//CFB
		JRadioButton rdbtnCfb = new JRadioButton("CFB");
		rdbtnCfb.setFont(new Font("����", Font.PLAIN, 14));
		rdbtnCfb.setBounds(66, 146, 52, 23);
		setting.add(rdbtnCfb);
		//OFB
		JRadioButton rdbtnOfb = new JRadioButton("OFB");
		rdbtnOfb.setFont(new Font("����", Font.PLAIN, 14));
		rdbtnOfb.setBounds(120, 146, 52, 23);
		setting.add(rdbtnOfb);
		//��ϢժҪ�㷨
		JLabel label_2 = new JLabel("\u4FE1\u606F\u6458\u8981\u7B97\u6CD5\u9009\u62E9\uFF1A");
		label_2.setFont(new Font("����", Font.PLAIN, 14));
		label_2.setBounds(10, 179, 177, 35);
		setting.add(label_2);
		//SHA-1�㷨
		JRadioButton rdbtnSha = new JRadioButton("SHA-1");
		rdbtnSha.setFont(new Font("����", Font.PLAIN, 14));
		rdbtnSha.setBounds(10, 220, 71, 23);
		setting.add(rdbtnSha);
		//MD-5�㷨
		JRadioButton rdbtnMd = new JRadioButton("MD5");
		rdbtnMd.setFont(new Font("����", Font.PLAIN, 14));
		rdbtnMd.setBounds(91, 220, 52, 23);
		setting.add(rdbtnMd);
		//RSA�㷨���ȵ�ѡ��
		JLabel lblRsa = new JLabel("RSA\u4F4D\u6570\uFF1A");
		lblRsa.setFont(new Font("����", Font.PLAIN, 14));
		lblRsa.setBounds(15, 261, 103, 35);
		setting.add(lblRsa);
		
		JComboBox RSALength = new JComboBox();
		RSALength.setBounds(91, 268, 66, 21);
		setting.add(RSALength);
		
		//��Կ����Դ
		JLabel label_3 = new JLabel("\u5BF9\u79F0\u79D8\u94A5\u6765\u6E90\u9009\u62E9\uFF1A");
		label_3.setFont(new Font("����", Font.PLAIN, 14));
		label_3.setBounds(10, 306, 177, 35);
		setting.add(label_3);
		//��Կ�������
		JRadioButton generate = new JRadioButton("\u81EA\u52A8\u751F\u6210");
		generate.setFont(new Font("����", Font.PLAIN, 14));
		generate.setBounds(15, 336, 91, 23);
		setting.add(generate);
		//��Կ����
		JRadioButton input = new JRadioButton("\u6587\u672C\u6846\u8F93\u5165");
		input.setFont(new Font("����", Font.PLAIN, 14));
		input.setBounds(15, 361, 103, 23);
		setting.add(input);
		
		keyValue = new JTextField();
		keyValue.setBounds(10, 390, 160, 35);
		setting.add(keyValue);
		keyValue.setColumns(10);
		
		//���水ť
		JButton btnSave = new JButton("\u4FDD\u5B58");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnSave.setBounds(10, 446, 71, 23);
		setting.add(btnSave);
		//�޸İ�ť
		JButton btnChange = new JButton("\u4FEE\u6539");
		btnChange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnChange.setBounds(109, 446, 66, 23);
		setting.add(btnChange);	
		
		/*
		 * �����������
		 */
		JPanel sendArea = new JPanel();
		sendArea.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		sendArea.setBounds(221, 10, 269, 353);
		frame.getContentPane().add(sendArea);
		sendArea.setLayout(null);
		
		JLabel label_4 = new JLabel("\u53D1\u9001\u65B9");
		label_4.setBounds(94, 5, 60, 24);
		label_4.setFont(new Font("����", Font.PLAIN, 20));
		sendArea.add(label_4);
		
		JTextArea sendText = new JTextArea();
		sendText.setBounds(10, 39, 249, 304);
		sendArea.add(sendText);
		
		/*
		 * �����������
		 */
		JPanel receiveArea = new JPanel();
		receiveArea.setLayout(null);
		receiveArea.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		receiveArea.setBounds(505, 10, 269, 353);
		frame.getContentPane().add(receiveArea);
		
		JLabel label_5 = new JLabel("\u53D1\u9001\u65B9");
		label_5.setFont(new Font("����", Font.PLAIN, 20));
		label_5.setBounds(94, 5, 60, 24);
		receiveArea.add(label_5);
		
		JTextArea receiveText = new JTextArea();
		receiveText.setBounds(10, 39, 249, 304);
		receiveArea.add(receiveText);
		
		/*
		 * ��������
		 */
		JPanel data = new JPanel();
		data.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		data.setBounds(221, 373, 553, 179);
		frame.getContentPane().add(data);
		data.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\u52A0\u5BC6\u6570\u636E");
		lblNewLabel.setFont(new Font("����", Font.PLAIN, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel.setBounds(246, 5, 72, 21);
		data.add(lblNewLabel);
		
		JTextArea textData = new JTextArea();
		textData.setBounds(10, 30, 533, 107);
		data.add(textData);
		//ȷ�ϼ���
		JButton btnEncry = new JButton("\u52A0\u5BC6");
		btnEncry.setFont(new Font("����", Font.PLAIN, 16));
		btnEncry.setBounds(436, 147, 93, 23);
		data.add(btnEncry);
	}
}
