package me.emilelu.AnyApiToolbox;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class WPFrame extends JFrame {
	/**
	 * AnyApiToolBox | WallPaper Getter Frame
	 * 
	 * (c) 2020 Emilelu. All rights reserved.
	 **/
	private static final long serialVersionUID = -6616646953150355103L;
	String theAPI;
	String theDirectory;
	JRadioButton pc = new JRadioButton("电脑");
	JRadioButton mb = new JRadioButton("手机");
	JTextField cusURL = new JTextField("http://", 50);

	public WPFrame(int what) throws IOException {
		super("获取壁纸");
		this.setIconImage(new ImageIcon("./icon.png").getImage());
		JPanel p = new JPanel();
		add(p);

		if (what == 1) {
			theAPI = "https://api.ixiaowai.cn/api/api.php";
			p.add(new JLabel("您要下载几张 二次元动漫壁纸？"));
		}
		if (what == 2) {
			theAPI = "https://api.ixiaowai.cn/mcapi/mcapi.php";
			p.add(new JLabel("您要下载几张 Menhera 酱壁纸？"));
		}
		if (what == 3) {
			theAPI = "https://api.ixiaowai.cn/gqapi/gqapi.php";
			p.add(new JLabel("您要下载几张 高清壁纸？"));
		}
		if (what == 6) {
			theAPI = "http://api.btstu.cn/sjbz/api.php?lx=dongman&format=json";
			p.add(new JLabel("您要下载几张 动漫壁纸？"));
		}
		if (what == 7) {
			theAPI = "http://api.btstu.cn/sjbz/api.php?lx=meizi&format=json";
			p.add(new JLabel("您要下载几张 妹子壁纸？"));
		}
		if (what == 8) {
			theAPI = "http://api.btstu.cn/sjbz/api.php?lx=fengjing&format=json";
			p.add(new JLabel("您要下载几张 风景壁纸？"));
		}

		if (what == 114514) {
			p.add(new JLabel("请输入自定义的 API 以批量下载壁纸。"));
			p.add(cusURL);
			p.add(new JLabel("您要下载几张壁纸？"));
		}

		JTextField times = new JTextField("1", 5);
		p.add(times);

		if (what == 6 || what == 7 || what == 8) {
			p.add(new JLabel("壁纸类型"));
			ButtonGroup bg = new ButtonGroup();
			bg.add(pc);
			bg.add(mb);
			p.add(pc);
			p.add(mb);
		}

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
					if (what == 114514)
						theAPI = cusURL.getText();
					start.setEnabled(false);
					setExtendedState(JFrame.MAXIMIZED_BOTH);
					for (int i = 0; i < Integer.parseInt(times.getText()); i++) {
						SimpleDateFormat df = new SimpleDateFormat("yy.MM.dd-HH.mm.ss.SSS");
						String filename = theDirectory + df.format(new Date()) + ".jpg";

						if (what == 6 || what == 7 || what == 8) {
							if (pc.isSelected()) {
								// CONTINUE
							} else {
								theAPI = theAPI + "&method=mobile";
							}
							String temp = null;
							String json = null;
							try {
								json = Download.getContent(theAPI);
							} catch (IOException e2) {
								e2.printStackTrace();
							}
							String pattern = "http.*jpg|http.*png";
							json = json.replace("\\", "");
							Pattern r = Pattern.compile(pattern);
							Matcher m = r.matcher(json);
							while (m.find()) {
								temp = m.group();
							}
							if (temp.contains(".png")) {
								filename = filename.replace(".jpg", ".png");
							}
							try {
								DL d = new DL();
								d.setUrl(temp);
								d.setSaveAddress(filename);
								d.start();
							} catch (Exception e1) {
								p.add(new JLabel("下载失败，原因："));
								e1.printStackTrace();
								p.add(new JLabel("正在跳至下一个任务...\n"));
								continue;
							}
						} else {
							if (theAPI.contains(".png")) {
								filename = filename.replace(".jpg", ".png");
							}
							try {
								DL d = new DL();
								d.setUrl(theAPI);
								d.setSaveAddress(filename);
								d.start();
							} catch (Exception e1) {
								p.add(new JLabel("下载失败，原因："));
								e1.printStackTrace();
								p.add(new JLabel("正在跳至下一个任务...\n"));
								continue;
							}
						}

						p.add(new JLabel("下载第 " + (i + 1) + " 张壁纸完成，文件已存至 " + filename + "。"));
						p.updateUI();
					}
					start.setEnabled(true);
				}
			}
		});

		// Set Visible
		if (what == 6 || what == 7 || what == 8) {
			setSize(280, 200);
		} else if (what == 114514) {
			setSize(800, 200);
		} else {
			setSize(350, 200);
		}
		setLocation(500, 500);
		setVisible(true);
	}
}