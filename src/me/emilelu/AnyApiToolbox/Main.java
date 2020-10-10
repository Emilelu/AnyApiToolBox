package me.emilelu.AnyApiToolbox;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import mdlaf.MaterialLookAndFeel;
import mdlaf.themes.MaterialLiteTheme;

// A Thread of Getting Wallpapers
class GWP extends Thread {
	int num;

	public void run() {
		try {
			new WPFrame(num);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setNum(int num) {
		this.num = num;
	}

}

// A Thread of Getting Content which in WebPage
class GST extends Thread {
	int num;

	public void run() {
		new STFrame(num);
	}

	public void setNum(int num) {
		this.num = num;
	}

}

// A Thread of LoliconAPI
class LLC extends Thread {

	public void run() {
		new LoliconAPI();
	}

}

// A Thread of Downloading
class DL extends Thread {
	String url;
	String saveAddress;

	public void setUrl(String url) {
		this.url = url;
	}

	public void setSaveAddress(String saveAddress) {
		this.saveAddress = saveAddress;
	}

	public void run() {
		Download.downloadFile(url, saveAddress);
	}
}

public class Main extends JFrame {

	/**
	 * AnyApiToolBox | Main
	 * 
	 * (c) 2020 Emilelu. All rights reserved.
	 **/
	private static final long serialVersionUID = 5583924409602041567L;

	public Main() throws UnsupportedLookAndFeelException {
		super("AnyApiToolBox | 0.3.1-beta");
		UIManager.setLookAndFeel(new MaterialLookAndFeel(new MaterialOrientalFontsTheme()));
		this.setIconImage(new ImageIcon("./icon.png").getImage());

		JPanel p = new JPanel();
		p.setLayout(new FlowLayout(FlowLayout.LEADING, 20, 20));
		add(p);

		JButton b1 = new JButton("二次元动漫壁纸");
		b1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GWP g = new GWP();
				g.setNum(1);
				g.start();
			}
		});
		JButton b2 = new JButton("Menhera 酱壁纸");
		b2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GWP g = new GWP();
				g.setNum(2);
				g.start();
			}
		});
		JButton b3 = new JButton("高清壁纸");
		b3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GWP g = new GWP();
				g.setNum(3);
				g.start();
			}
		});
		JButton b4 = new JButton("一言语录");
		b4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GST g = new GST();
				g.setNum(4);
				g.start();
			}
		});
		JButton b5 = new JButton("舔狗日记");
		b5.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GST g = new GST();
				g.setNum(5);
				g.start();
			}
		});
		JButton b6 = new JButton("动漫壁纸");
		b6.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GWP g = new GWP();
				g.setNum(6);
				g.start();
			}
		});
		JButton b7 = new JButton("妹子壁纸");
		b7.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GWP g = new GWP();
				g.setNum(7);
				g.start();

			}
		});
		JButton b8 = new JButton("风景壁纸");
		b8.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GWP g = new GWP();
				g.setNum(8);
				g.start();
			}
		});
		JButton b9 = new JButton("毒鸡汤");
		b9.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GST g = new GST();
				g.setNum(9);
				g.start();
			}
		});

		JButton b10 = new JButton("文本生成二维码（Deprecated）");
		b10.setEnabled(false);

		JButton b11 = new JButton("随机色图");
		b11.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new LLC().start();
			}
		});
		JButton b12 = new JButton("自定义 URL 获取图片");
		b12.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GWP g = new GWP();
				g.setNum(114514);
				g.start();
			}
		});
		JButton b13 = new JButton("自定义 URL 获取文本");
		b13.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GST g = new GST();
				g.setNum(114514);
				g.start();
			}
		});
		p.add(new JLabel("小歪API："));
		p.add(b1);
		p.add(b2);
		p.add(b3);
		p.add(b4);
		p.add(b5);

		p.add(new JLabel("搏天API："));
		p.add(b6);
		p.add(b7);
		p.add(b8);
		p.add(b9);
		p.add(b10);

		p.add(new JLabel("Lolicon API："));
		p.add(b11);
		p.add(new JLabel("附加功能："));
		p.add(b12);
		p.add(b13);

		setSize(850, 300);
		setLocation(300, 500);
		setResizable(true);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) throws UnsupportedLookAndFeelException {
//		System.out.println("[Debug] Now your ip is " + Functions.getContent("https://api.ip.sb/jsonip"));
		new Main();
	}

}

class MaterialOrientalFontsTheme extends MaterialLiteTheme {

	@Override
	protected void installFonts() {
		this.fontBold = new javax.swing.plaf.FontUIResource("黑体", Font.BOLD, 16);
		this.fontItalic = new javax.swing.plaf.FontUIResource("黑体", Font.ITALIC, 16);
		this.fontMedium = new javax.swing.plaf.FontUIResource("黑体", Font.PLAIN, 16);
		this.fontRegular = new javax.swing.plaf.FontUIResource("黑体", Font.PLAIN, 16);
	}
}
