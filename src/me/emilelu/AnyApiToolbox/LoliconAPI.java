package me.emilelu.AnyApiToolbox;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

public class LoliconAPI extends JFrame {

	/**
	 * LoliconAPI in AnyApiToolBox
	 * 
	 * (c) 2020 Emilelu. All rights reserved.
	 **/
	private static final long serialVersionUID = 3471469203291461330L;
	String theAPI = "https://api.lolicon.app/setu/index.php";
	boolean generated = false;
	boolean focused = false;
	boolean isDetails = false;
	String theDirectory;

	public LoliconAPI() {
		super("LoliconAPI | AnyApiToolBox");
		this.setIconImage(new ImageIcon("./icon.png").getImage());
		JPanel panel = new JPanel();
		add(panel);

		// Set Variables
		JLabel apiKey = new JLabel("请输入您的 APIKEY，没有请留空");
		panel.add(apiKey);
		JTextField yourKey = new JTextField(20);
		panel.add(yourKey);

		JLabel type = new JLabel("何种类型的图片？");
		panel.add(type);
		ButtonGroup typeR = new ButtonGroup();
		JRadioButton allage = new JRadioButton("全年龄");
		JRadioButton r18 = new JRadioButton("R18");
		r18.setSelected(true);
		JRadioButton mix = new JRadioButton("混合");
		typeR.add(allage);
		typeR.add(r18);
		typeR.add(mix);
		panel.add(allage);
		panel.add(r18);
		panel.add(mix);

		JLabel key = new JLabel("是否指定关键字?");
		panel.add(key);
		ButtonGroup keyR = new ButtonGroup();
		JRadioButton yes1 = new JRadioButton("是");
		JRadioButton no1 = new JRadioButton("否");
		no1.setSelected(true);
		keyR.add(yes1);
		keyR.add(no1);
		panel.add(yes1);
		JTextField theKey = new JTextField("输入关键字", 10);
		yes1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!generated) {
					theKey.addFocusListener(new FocusListener() {

						@Override
						public void focusLost(FocusEvent arg0) {

						}

						@Override
						public void focusGained(FocusEvent arg0) {
							if (!focused) {
								theKey.setText("");
								focused = true;
							}
						}
					});
					panel.add(theKey);
					panel.updateUI();
					generated = true;
				}
			}
		});
		panel.add(no1);
		no1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				panel.remove(theKey);
				panel.updateUI();
				generated = false;
				focused = false;
				theKey.setText("输入关键字");
			}
		});

		JLabel usemas = new JLabel("是否使用 master_1200 缩略图？");
		panel.add(usemas);
		ButtonGroup usemasR = new ButtonGroup();
		JRadioButton yes2 = new JRadioButton("是");
		JRadioButton no2 = new JRadioButton("否");
		no2.setSelected(true);
		usemasR.add(yes2);
		usemasR.add(no2);
		panel.add(yes2);
		panel.add(no2);

		JLabel outdetails = new JLabel("是否输出详细信息至 txt？");
		panel.add(outdetails);
		ButtonGroup outdetailsR = new ButtonGroup();
		JRadioButton yes3 = new JRadioButton("是");
		JRadioButton no3 = new JRadioButton("否");
		yes3.setSelected(true);
		outdetailsR.add(yes3);
		outdetailsR.add(no3);
		panel.add(yes3);
		panel.add(no3);

		JLabel times = new JLabel("下载几张图片？");
		panel.add(times);
		JTextField timesR = new JTextField(5);
		panel.add(timesR);

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
					theDirectory = file.getAbsolutePath() + "/";
					yourVol.setText(theDirectory);
					panel.add(yourVol);
					panel.updateUI();
				}
			}
		});
		panel.add(selVol);

		JButton start = new JButton("开始");
		panel.add(start);
		start.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!(theDirectory == null) && !(timesR.getText().equals("0"))) {
					try {
						start.setEnabled(false);
						setExtendedState(JFrame.MAXIMIZED_BOTH);

						if (yourKey.getText().equals("")) {
							theAPI = theAPI + "?";
						} else {
							theAPI = theAPI + "?apikey=" + yourKey.getText();
						}

						if (allage.isSelected()) {
							theAPI = theAPI + "&r18=0";
						} else if (r18.isSelected()) {
							theAPI = theAPI + "&r18=1";
						} else {
							theAPI = theAPI + "&r18=2";
						}

						if (yes1.isSelected()) {
							String key = theKey.getText();
							key = URLEncoder.encode(key, "UTF-8");
							theAPI = theAPI + "&keyword=" + key;
						}

						if (yes2.isSelected()) {
							theAPI = theAPI + "&size1200=true";
						}

						if (yes3.isSelected()) {
							isDetails = true;
						}
//						System.out.println(theAPI);
						SimpleDateFormat df = new SimpleDateFormat("yy.MM.dd-HH.mm.ss.SSS");
						String inputPath2 = theDirectory + "HentaiLogs-" + df.format(new Date()) + ".txt";
						List<String> hentaiLogs = new ArrayList<String>();

						for (int i = 0; i < Integer.parseInt(timesR.getText()); i++) {
							try {
								String line = Download.getContent(theAPI);

								// Judge r18
								String isR18 = null;
								if (line.contains("\"r18\":false")) {
									isR18 = "不是色图";
								} else {
									isR18 = "是色图";
								}

								// Replace \ and " to empty
								line = line.replace("\\", "");
								line = line.replace("\"", "");

								if (line.contains("没有符合条件的色图")) {
									panel.add(new JLabel("没有符合条件的色图"));
									System.exit(0);
								}

								// Define the pattern
								String pattern = "(title:.*,url)|(http.*jpg|http.*png)|(tags:\\[.*\\])";

								// Compile the pattern
								Pattern r = Pattern.compile(pattern);

								// new Matcher
								Matcher m = r.matcher(line);

								// Variables
								int t = 1;
								String ant = null;
								String ant2 = null;
								String url = null;
								String tags = null;
								String filename = theDirectory;

								// Find
								while (m.find()) {
									if (t == 1) {
										ant = m.group();
									}
									if (t == 2) {
										url = m.group();
									}
									if (t == 3) {
										tags = m.group();
									}
									t = t + 1;
								}
								ant = ant.replace(",", "");
								ant = ant.replace("url", "");
								ant2 = ant;
								ant = ant.replace("title:", "标题：");
								ant = ant.replace("author:", "\n作者：");
								ant2 = ant2.replace("title:", "");
								ant2 = ant2.replace("author:", " - ");
								tags = tags.replace("tags:", "标签：");
								tags = tags.replace("[", "");
								tags = tags.replace("]}]", "");
								if (isDetails) {
									String[] log = { "类型：" + isR18, ant, "链接：" + url, tags };
									for (int j = 0; j < log.length; j++) {
										hentaiLogs.add(log[j]);
									}
									hentaiLogs.add("——————————");
								}
								if (ant2.contains("\\")) {
									ant2 = ant2.replace("\\", "~");
								}
								if (ant2.contains("/")) {
									ant2 = ant2.replace("/", "~");
								}
								if (ant2.contains(":")) {
									ant2 = ant2.replace(":", "~");
								}
								if (ant2.contains("*")) {
									ant2 = ant2.replace("*", "~");
								}
								if (ant2.contains("?")) {
									ant2 = ant2.replace("?", "~");
								}
								if (ant2.contains("\"")) {
									ant2 = ant2.replace("\"", "~");
								}
								if (ant2.contains("<")) {
									ant2 = ant2.replace("<", "~");
								}
								if (ant2.contains(">")) {
									ant2 = ant2.replace(">", "~");
								}
								if (ant2.contains("|")) {
									ant2 = ant2.replace("|", "~");
								}
								filename = filename + ant2 + ".jpg";
								if (url.contains(".png")) {
									filename.replace(".jpg", ".png");
								}
//								System.out.println("[Debug] Now the filename is " + filename);
								DL d = new DL();
								d.setUrl(url);
								d.setSaveAddress(filename);
								d.start();
								panel.add(new JLabel("下载第 " + (i + 1) + " 张色图完成，文件已存至 " + filename + "。"));
							} catch (Exception e1) {
								continue;
							}
						}
						if (isDetails) {
							FileOutputStream fs = new FileOutputStream(new File(inputPath2));
							PrintStream p = new PrintStream(fs);
							for (int j = 0; j < hentaiLogs.size(); j++) {
								p.println(hentaiLogs.get(j));
							}
							fs.close();
							p.close();
							panel.add(new JLabel("详细信息以及色图皆已存至 " + theDirectory + "。"));
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					start.setEnabled(true);
					panel.updateUI();
				}

			}
		});

		// Set Visible
		setSize(427, 300);
		setLocation(500, 500);
		setVisible(true);
	}

}
