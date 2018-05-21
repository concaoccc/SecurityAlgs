package UI;

import java.awt.EventQueue;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;

public class MainUI {

	private JFrame frame;
	//����ԳƼ����㷨
	public String symAlgs = "DES";
	public String symmode = "CBC";
	public String shaAlgs = "SHA-1";
	public String keySelect = "generate";
	public String symKey = "";
	public String RSALength = "200";
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
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
		//�޷����зŴ�
		frame.setResizable(false);

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
		final JRadioButton rdbtnDes = new JRadioButton("DES", true);
		rdbtnDes.setFont(new Font("����", Font.PLAIN, 14));
		rdbtnDes.setBounds(10, 76, 52, 23);
		setting.add(rdbtnDes);
		//AES
		final JRadioButton rdbtnAes = new JRadioButton("AES", false);
		rdbtnAes.setFont(new Font("����", Font.PLAIN, 14));
		rdbtnAes.setBounds(91, 76, 52, 23);
		setting.add(rdbtnAes);
		//���뵽һ����ť��
		ButtonGroup SymmetricAlgs = new ButtonGroup();
		SymmetricAlgs.add(rdbtnDes);
		SymmetricAlgs.add(rdbtnAes);
		//����DESѡ�����Ӧ�¼�
		rdbtnDes.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				symAlgs = "DES";
			}
		});
		//����AESѡ�����Ӧ�¼�
		rdbtnAes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				symAlgs = "AES";
			}
		});
		//�ԳƼ��ܵ�ģʽ
		JLabel label_1 = new JLabel("\u5BF9\u79F0\u52A0\u5BC6\u6A21\u5F0F\u9009\u62E9\uFF1A");
		label_1.setFont(new Font("����", Font.PLAIN, 14));
		label_1.setBounds(10, 105, 160, 35);
		setting.add(label_1);
		//�ԳƼ���ģʽ��ѡ��
		//CBC
		final JRadioButton rdbtnCbc = new JRadioButton("CBC", true);
		rdbtnCbc.setFont(new Font("����", Font.PLAIN, 14));
		rdbtnCbc.setBounds(10, 146, 52, 23);
		setting.add(rdbtnCbc);
		//CFB
		final JRadioButton rdbtnCfb = new JRadioButton("CFB", false);
		rdbtnCfb.setFont(new Font("����", Font.PLAIN, 14));
		rdbtnCfb.setBounds(66, 146, 52, 23);
		setting.add(rdbtnCfb);
		//OFB
		final JRadioButton rdbtnOfb = new JRadioButton("OFB", false);
		rdbtnOfb.setFont(new Font("����", Font.PLAIN, 14));
		rdbtnOfb.setBounds(120, 146, 52, 23);
		setting.add(rdbtnOfb);
		//���뵽һ����ť��
		ButtonGroup SymmetricMode = new ButtonGroup();
		SymmetricMode.add(rdbtnCbc);
		SymmetricMode.add(rdbtnCfb);
		SymmetricMode.add(rdbtnOfb);
		//����CBC����Ӧʱ��
		rdbtnCbc.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				symmode = "CBC";
			}
		});
		//����CFB����Ӧ�¼�
		rdbtnCfb.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				symmode = "CFB";
			}
		});
		//����OFB����Ӧ�¼�
		rdbtnOfb.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				symmode = "OFB";
			}
		});
		
		//��ϢժҪ�㷨
		JLabel label_2 = new JLabel("\u4FE1\u606F\u6458\u8981\u7B97\u6CD5\u9009\u62E9\uFF1A");
		label_2.setFont(new Font("����", Font.PLAIN, 14));
		label_2.setBounds(10, 179, 177, 35);
		setting.add(label_2);
		//SHA-1�㷨
		final JRadioButton rdbtnSha = new JRadioButton("SHA-1", true);
		rdbtnSha.setFont(new Font("����", Font.PLAIN, 14));
		rdbtnSha.setBounds(10, 220, 71, 23);
		setting.add(rdbtnSha);
		//MD-5�㷨
		final JRadioButton rdbtnMd = new JRadioButton("MD5", false);
		rdbtnMd.setFont(new Font("����", Font.PLAIN, 14));
		rdbtnMd.setBounds(91, 220, 52, 23);
		setting.add(rdbtnMd);
		//���뵽һ����ť��
		ButtonGroup shaAlgsGroup = new ButtonGroup();
		shaAlgsGroup.add(rdbtnSha);
		shaAlgsGroup.add(rdbtnMd);
		//Ϊsha������Ӧ
		rdbtnSha.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				shaAlgs = "SHA-1";
			}
		});
		//Ϊmd5������Ӧ
		rdbtnMd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				shaAlgs = "MD5";
			}
		});
		//RSA�㷨���ȵ�ѡ��
		JLabel lblRsa = new JLabel("RSA\u4F4D\u6570\uFF1A");
		lblRsa.setFont(new Font("����", Font.PLAIN, 14));
		lblRsa.setBounds(10, 249, 103, 35);
		setting.add(lblRsa);
		
		final String[] dataLeng= {"200", "512", "1024"};
		final JComboBox RSALengthBox = new JComboBox(dataLeng);
		RSALengthBox.setBounds(77, 256, 66, 21);
		setting.add(RSALengthBox);
		RSALengthBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int index = RSALengthBox.getSelectedIndex();
				RSALength = dataLeng[index];
			}
		});
		//��Կ����Դ
		JLabel label_3 = new JLabel("\u5BF9\u79F0\u79D8\u94A5\u6765\u6E90\u9009\u62E9\uFF1A");
		label_3.setFont(new Font("����", Font.PLAIN, 14));
		label_3.setBounds(10, 287, 177, 35);
		setting.add(label_3);
		//��Կ�������
		final JRadioButton generate = new JRadioButton("\u81EA\u52A8\u751F\u6210", true);
		generate.setFont(new Font("����", Font.PLAIN, 14));
		generate.setBounds(10, 316, 91, 23);
		setting.add(generate);
		//��Կ����
		final JRadioButton input = new JRadioButton("\u6587\u672C\u6846\u8F93\u5165", false);
		input.setFont(new Font("����", Font.PLAIN, 14));
		input.setBounds(10, 344, 103, 23);
		setting.add(input);
		
		JScrollPane srollKeyValue = new JScrollPane();
		srollKeyValue.setBounds(10, 373, 160, 58);
		setting.add(srollKeyValue);
		final JTextArea keyValue = new JTextArea();
		keyValue.setBounds(10, 373, 160, 58);
		srollKeyValue.setViewportView(keyValue);
		keyValue.setLineWrap(true);
		keyValue.setEnabled(false);
		
		//���뵽һ����ť��
		ButtonGroup RSASelectGroup = new ButtonGroup();
		RSASelectGroup.add(generate);
		RSASelectGroup.add(input);
		//���ɰ�ť��Ӧ
		generate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				keySelect = "generate";
				keyValue.setText("");
				keyValue.setEnabled(false);
			}
		});
		//���밴ť��Ӧ
		input.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				keySelect = "input";
				keyValue.setEnabled(true);
			}
		});
		
		//���水ť
		final JButton btnSave = new JButton("\u4FDD\u5B58");
		//�޸İ�ť
		final JButton btnChange = new JButton("\u4FEE\u6539");
		//������������
		final JTextArea textData = new JTextArea();
		//ȷ�ϼ��ܰ�ť
		final JButton btnEncry = new JButton("\u52A0\u5BC6");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rdbtnDes.setEnabled(false);
				rdbtnAes.setEnabled(false);
				rdbtnCbc.setEnabled(false);
				rdbtnCfb.setEnabled(false);
				rdbtnOfb.setEnabled(false);
				rdbtnSha.setEnabled(false);
				rdbtnMd.setEnabled(false);
				generate.setEnabled(false);
				input.setEnabled(false);
				RSALengthBox.setEnabled(false);
				keyValue.setEnabled(false);
				btnSave.setEnabled(false);
				textData.setEnabled(true);
				btnEncry.setEnabled(true);
				btnChange.setEnabled(true);
				//TODO ����Ƿ�����Կ���룬���û��Ҫ��ʾ
			}
		});
//		btnSave.setEnabled(false);
		btnSave.setBounds(10, 446, 71, 23);
		setting.add(btnSave);
		
		btnChange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rdbtnDes.setEnabled(true);
				rdbtnAes.setEnabled(true);
				rdbtnCbc.setEnabled(true);
				rdbtnCfb.setEnabled(true);
				rdbtnOfb.setEnabled(true);
				rdbtnSha.setEnabled(true);
				rdbtnMd.setEnabled(true);
				generate.setEnabled(true);
				input.setEnabled(true);
				RSALengthBox.setEnabled(true);
				if (!keyValue.isEnabled())
				{
					keyValue.setEnabled(true);
				}
				btnSave.setEnabled(true);
				textData.setEnabled(false);
				btnEncry.setEnabled(false);
				textData.setText("");
				btnChange.setEnabled(false);
			}
		});
		btnChange.setBounds(109, 446, 66, 23);
		setting.add(btnChange);	
		btnChange.setEnabled(false);
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
		
		JScrollPane scrolSend = new JScrollPane();
		scrolSend.setBounds(10, 39, 249, 304);
		sendArea.add(scrolSend);
		JTextArea sendText = new JTextArea();
		sendText.setBounds(10, 39, 249, 304);
		scrolSend.setViewportView(sendText);
		sendText.setLineWrap(true);
		//���ó�ֻ��
		sendText.setEnabled(false);

		/*
		 * �����������
		 */
		JPanel receiveArea = new JPanel();
		receiveArea.setLayout(null);
		receiveArea.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		receiveArea.setBounds(505, 10, 269, 353);
		frame.getContentPane().add(receiveArea);
		
		JLabel label_5 = new JLabel("\u63A5\u6536\u65B9");
		label_5.setFont(new Font("����", Font.PLAIN, 20));
		label_5.setBounds(94, 5, 60, 24);
		receiveArea.add(label_5);
		
		JScrollPane scrolReceive = new JScrollPane();
		scrolReceive.setBounds(10, 39, 249, 304);
		receiveArea.add(scrolReceive);
		JTextArea receiveText = new JTextArea();
		receiveText.setBounds(10, 39, 249, 304);
		scrolReceive.setViewportView(receiveText);
		receiveText.setLineWrap(true);
		//���ó�ֻ��
		receiveText.setEnabled(false);
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
		
		JScrollPane scrolData = new JScrollPane();
		scrolData.setBounds(10, 30, 533, 107);
		data.add(scrolData);
		
		textData.setBounds(10, 30, 533, 107);
		scrolData.setViewportView(textData);
		textData.setLineWrap(true);
		textData.setEnabled(false);
		btnEncry.setFont(new Font("����", Font.PLAIN, 16));
		btnEncry.setBounds(436, 147, 93, 23);
		data.add(btnEncry);
		btnEncry.setEnabled(false);
		btnEncry.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO ����Ƿ����������룬���û��Ҫ����
				
			}
		});
	}
}
