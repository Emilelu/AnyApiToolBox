package me.emilelu.AnyApiToolbox;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class STFrame extends JFrame {

	/**
	 * AnyApiToolBox | Sentence Getter Frame
	 * 
	 * (c) 2020 Emilelu. All rights reserved.
	 **/
	private static final long serialVersionUID = -4479090473500099084L;
	String theAPI;
	String theDirectory;
	JTextField cusURL = new JTextField("http://", 50);

	public STFrame(int what) {
		super("输出语句");
		this.setIconImage(new ImageIcon("./icon.png").getImage());
		JPanel p = new JPanel();
		add(p);

		if (what == 4) {
			theAPI = "https://api.ixiaowai.cn/ylapi/index.php";
			p.add(new JLabel("您要输出几句 一言语录？"));
		}
		if (what == 5) {
			theAPI = "https://api.ixiaowai.cn/tgrj/index.php";
			p.add(new JLabel("您要输出几句 舔狗日记？"));
		}
		if (what == 9) {
			theAPI = "http://api.btstu.cn/yan/api.php";
			p.add(new JLabel("您要输出几句 毒鸡汤？"));
		}

		if (what == 114514) {
			p.add(new JLabel("请输入自定义的 API 以批量输出语句。"));
			p.add(cusURL);
			p.add(new JLabel("您要输出几句？"));
		}

		JTextField times = new JTextField("1", 5);
		p.add(times);

		JButton selVol = new JButton("选择目录");
		JLabel yourVol = new JLabel();
		selVol.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser volume = new JFileChooser();
				volume.setMultiSelectionEnabled(false);
				volume.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				volume.showOpenDialog(new JFrame());
				File file = volume.getSelectedFile();
				if (file == null) {
				} else {
					theDirectory = file.getAbsolutePath();
					yourVol.setText(theDirectory);
					p.add(yourVol);
					p.updateUI();
				}
			}
		});
		p.add(selVol);

		JButton start = new JButton("开始");
		p.add(start);
		start.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!(theDirectory == null) && !(times.getText().equals("0"))) {
					try {
						if (what == 114514)
							theAPI = cusURL.getText();
						start.setEnabled(false);
						setExtendedState(JFrame.MAXIMIZED_BOTH);
						SimpleDateFormat df = new SimpleDateFormat("yy.MM.dd-HH.mm.ss.SSS");
						theDirectory = theDirectory + "/Sentences-" + df.format(new Date()) + ".txt";

						FileOutputStream fs = new FileOutputStream(new File(theDirectory));
						PrintStream p1 = new PrintStream(fs);
						for (int i = 0; i < Integer.parseInt(times.getText()); i++) {
							try {
								String theSentence = Download.getContent(theAPI);
								p1.println(theSentence);
							} catch (Exception e1) {
								System.out.print("获取失败，原因：");
								e1.printStackTrace();
								System.out.println("正在跳至下一个任务...\n");
								continue;
							}
						}
						p.add(new JLabel("所有语句已保存至 " + theDirectory + "。"));
						fs.close();
						p1.close();
						p.updateUI();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					start.setEnabled(true);
				}

			}
		});

		// Set Visible
		if (what == 114514) {
			setSize(800, 200);
		} else if (what == 9) {
			setSize(300, 200);
		} else {
			setSize(350, 200);
		}
		setLocation(500, 500);
		setVisible(true);
	}
}