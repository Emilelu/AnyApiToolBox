package me.emilelu.AnyApiToolbox;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Functions {

	public static void Lolicon() {
		String theAPI = "https://api.lolicon.app/setu/index.php";

		System.out.println("欢迎使用 Lolicon API 提供的 随机色图功能。");
		System.out.println("如果您是首次使用本 API，请访问 https://api.lolicon.app/#/setu 仔细阅读页面内容！");
		System.out.println("首先，请输入您的 APIKEY，若无请去按照页面指引进行申请，或者输入 n，但会有诸多限制。（例如调用次数极少）");

		try (Scanner getInfo = new Scanner(System.in)) {
			String apiKey = getInfo.next();
			if (apiKey.equals("n")) {
				theAPI = theAPI + "?";
			} else {
				theAPI = theAPI + "?apikey=" + apiKey;
			}
			System.out.println("您要获取何种类型的图片？0.全年龄 || 1.R18 || 2.混合");
			String type = getInfo.next();
			if (type.equals("0")) {
				theAPI = theAPI + "&r18=0";
			} else if (type.equals("1")) {
				theAPI = theAPI + "&r18=1";
			} else {
				theAPI = theAPI + "&r18=2";
			}
			System.out.println("是否要指定图片关键字？是则输入要指定的关键字，否则输入 n");
			System.out.println("若指定关键字，将会返回从插画标题、作者、标签中模糊搜索的结果。");
			@SuppressWarnings("unused")
			String fucknextline = getInfo.nextLine();
			String key = getInfo.nextLine();
			if (!key.equals("n")) {
				key = URLEncoder.encode(key, "UTF-8");
				theAPI = theAPI + "&keyword=" + key;
			} else {
				// CONTINUE
			}
			System.out.println("是否使用 master_1200 缩略图？(y/n) 即长或宽最大为 1200px 的缩略图，以节省流量或提升加载速度（某些原图的大小可以达到十几MB）");
			String size1200 = getInfo.next();
			if (size1200.equals("y")) {
				theAPI = theAPI + "&size1200=true";
			} else {
				// CONTINUE
			}
			System.out.println("是否在下载过程中输出图片信息并保存为 txt（标题、作者、链接、标签）？(y/n)");
			String outputDetails = getInfo.next();
			boolean isDetails = false;
			if (outputDetails.equals("y")) {
				isDetails = true;
			} else {
				// CONTINUE
			}
			System.out.println("[Debug] Now the link is " + theAPI);
			System.out.println("您要下载几张图片？");
			int times = getInfo.nextInt();
			if (times <= 0) {
				System.out.println("那您怕不是下个寂寞XD");
				System.exit(0);
			}
			System.out.println("保存的路径是？（务必输入绝对路径，准确无误，以 / 结尾！若对应文件夹不存在则会自动建立。）");
			String inputPath = getInfo.next();
			inputPath = fixPath(inputPath);
			diretoryDetector(inputPath, getInfo);

			SimpleDateFormat df = new SimpleDateFormat("yy.MM.dd-HH.mm.ss.SSS");
			String inputPath2 = inputPath + "/HentaiLogs-" + df.format(new Date()) + ".txt";
			List<String> hentaiLogs = new ArrayList<String>();

			for (int i = 0; i < times; i++) {
				String line = getContent(theAPI);

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
				String filename = inputPath;

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
				filename = filename + ant2 + ".jpg";
				if (isDetails) {
					String[] log = { "类型：" + isR18, ant, "链接：" + url, tags };
					for (int j = 0; j < log.length; j++) {
						System.out.println(log[j]);
						hentaiLogs.add(log[j]);
					}
					hentaiLogs.add("——————————");
				}
				if (url.contains(".png")) {
					filename.replace(".jpg", ".png");
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
				downloadFile(url, filename);
				System.out.println("下载第 " + (i + 1) + " 张色图完成，文件已存至 " + filename + "。");
				System.out.println("——————————");
			}
			if (isDetails) {
				FileOutputStream fs = new FileOutputStream(new File(inputPath2));
				PrintStream p = new PrintStream(fs);
				for (int j = 0; j < hentaiLogs.size(); j++) {
					p.println(hentaiLogs.get(j));
				}
				fs.close();
				p.close();
				System.out.println("所有详细信息已保存至 " + inputPath2 + "。");
			}
		} catch (Exception e) {
			System.out.print("程序异常退出，原因：");
			e.printStackTrace();
		}
	}

	public static String fixPath(String inputPath) {
		if (!inputPath.endsWith("/")) {
			String userSystem = System.getProperties().getProperty("os.name");
			if (userSystem.contains("Windows")) {
				System.out.println("检测到您是 Windows 用户，正在纠正路径...");
				if (inputPath.endsWith(":")) {
					inputPath = inputPath + "/";
				}
				if (inputPath.length() == 1) {
					inputPath = inputPath + ":/";
				}
				if (!inputPath.endsWith("/")) {
					inputPath = inputPath + "/";
				}
			} else {
				System.out.println("检测到您非 Windows 用户，正在纠正路径...");
				if (!inputPath.startsWith("/") || !inputPath.startsWith("~/")) {
					inputPath = "/" + inputPath;
				}
				if (!inputPath.endsWith("/")) {
					inputPath = inputPath + "/";
				}
			}
		}
		return inputPath;
	}

	public static void diretoryDetector(String inputPath, Scanner getInfos) {
		System.out.println("现在的路径是 " + inputPath + "，请确认是否无误(y/n)。");
		String isCorrect = getInfos.next();
		if (isCorrect.equals("y")) {
			// CONTINUE
		} else {
			System.out.println("路径有误，请重新填写。");
			System.exit(0);
		}
		File targetFile = new File(inputPath);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		if (targetFile.isDirectory()) {
			// CONTINUE
		} else {
			System.out.println("路径异常。");
			System.exit(0);
		}
	}

	public static void getWallpaper(int what) {
		String theAPI = null;

		if (what == 1) {
			theAPI = "https://api.ixiaowai.cn/api/api.php";
			System.out.println("您要下载几张 二次元动漫壁纸？");
		}
		if (what == 2) {
			theAPI = "https://api.ixiaowai.cn/mcapi/mcapi.php";
			System.out.println("您要下载几张 Menhera 酱壁纸？");
		}
		if (what == 3) {
			theAPI = "https://api.ixiaowai.cn/gqapi/gqapi.php";
			System.out.println("您要下载几张 高清壁纸？");
		}
		if (what == 6) {
			theAPI = "http://api.btstu.cn/sjbz/api.php?lx=dongman&format=json";
			System.out.println("您要下载几张 动漫壁纸？");
		}
		if (what == 7) {
			theAPI = "http://api.btstu.cn/sjbz/api.php?lx=meizi&format=json";
			System.out.println("您要下载几张 妹子壁纸？");
		}
		if (what == 8) {
			theAPI = "http://api.btstu.cn/sjbz/api.php?lx=fengjing&format=json";
			System.out.println("您要下载几张 风景壁纸？");
		}

		try (Scanner getInfos = new Scanner(System.in)) {
			if (what == 114514) {
				System.out.println("请输入自定义的 API 以批量下载壁纸。");
				theAPI = getInfos.next();
				System.out.println("您要下载几张壁纸？");
			}

			int times = getInfos.nextInt();
			if (times <= 0) {
				System.out.println("那您怕不是下个寂寞XD");
				System.exit(0);
			}

			if (what == 6 || what == 7 || what == 8) {
				System.out.println("如果您需要下载电脑壁纸，请输入 1。手机壁纸则输入除 1 外的任意字符。");
				String wp = getInfos.next();
				if (wp.equals("1")) {
					// CONTINUE
				} else {
					theAPI = theAPI + "&method=mobile";
				}
			}

			System.out.println("保存的路径是？（务必输入绝对路径，准确无误，以 / 结尾！若对应文件夹不存在则会自动建立。）");
			String inputPath = getInfos.next();

			inputPath = fixPath(inputPath);
			diretoryDetector(inputPath, getInfos);

			for (int i = 0; i < times; i++) {
				SimpleDateFormat df = new SimpleDateFormat("yy.MM.dd-HH.mm.ss.SSS");
				String filename = inputPath + df.format(new Date()) + ".jpg";

				if (what == 6 || what == 7 || what == 8) {
					String temp = null;
					String json = getContent(theAPI);
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
					downloadFile(temp, filename);
				} else {
					if (theAPI.contains(".png")) {
						filename = filename.replace(".jpg", ".png");
					}
					downloadFile(theAPI, filename);
				}

				System.out.println("下载第 " + (i + 1) + " 张壁纸完成，文件已存至 " + filename + "。");
			}
		} catch (Exception e) {
			System.out.print("程序异常退出，原因：");
			e.printStackTrace();
		}
	}

	public static void downloadFile(String url, String saveAddress) {
		try {
			URL fileUrl = new URL(url);
			HttpURLConnection conn;
			InputStream is;
			if (url.contains("pixiv")) {
				conn = (HttpURLConnection) fileUrl.openConnection();
				conn.setRequestProperty("referer", "https://www.pixiv.net/");
				conn.setRequestMethod("GET");
				is = conn.getInputStream();
			} else {
				is = fileUrl.openStream();
			}
			OutputStream os = new FileOutputStream(saveAddress);
			byte bf[] = new byte[1024];
			int length = 0;
			while ((length = is.read(bf, 0, 1024)) != -1) {
				os.write(bf, 0, length);
			}
			is.close();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void printSentence(int what) {
		String theAPI = null;

		if (what == 4) {
			theAPI = "https://api.ixiaowai.cn/ylapi/index.php";
			System.out.println("您要输出几句 一言语录？");
		}
		if (what == 5) {
			theAPI = "https://api.ixiaowai.cn/tgrj/index.php";
			System.out.println("您要输出几句 舔狗日记？");
		}
		if (what == 9) {
			theAPI = "http://api.btstu.cn/yan/api.php";
			System.out.println("您要输出几句 毒鸡汤？");
		}

		try (Scanner getInfos = new Scanner(System.in)) {
			if (what == 114514) {
				System.out.println("请输入自定义的 API 以批量输出文本。");
				theAPI = getInfos.next();
				System.out.println("您要输出几句文本？");
			}

			int times = getInfos.nextInt();
			if (times <= 0) {
				System.out.println("那您怕不是看个寂寞XD");
				System.exit(0);
			}

			System.out.println("您是否要将输出的内容保存为 txt？(y/n)");
			String isTXT = getInfos.next();
			if (isTXT.equals("y")) {
				System.out.println("保存的路径是？（务必输入绝对路径，准确无误，以 / 结尾！若对应文件夹不存在则会自动建立。）");
				String inputPath = getInfos.next();

				inputPath = fixPath(inputPath);
				diretoryDetector(inputPath, getInfos);
				SimpleDateFormat df = new SimpleDateFormat("yy.MM.dd-HH.mm.ss.SSS");
				inputPath = inputPath + "/Sentences-" + df.format(new Date()) + ".txt";

				FileOutputStream fs = new FileOutputStream(new File(inputPath));
				PrintStream p = new PrintStream(fs);
				for (int i = 0; i < times; i++) {
					String theSentence = getContent(theAPI);
					p.println(theSentence);
					System.out.println(theSentence);
				}
				System.out.println("所有语句已保存至 " + inputPath + "。");
				fs.close();
				p.close();
			} else {
				for (int i = 0; i < times; i++) {
					System.out.println(getContent(theAPI));
				}
			}
		} catch (Exception e) {
			System.out.print("程序异常退出，原因：");
			e.printStackTrace();
		}
	}

	public static String getContent(String link) throws IOException {
		URL url = new URL(link);
		InputStream in = url.openStream();
		InputStreamReader isr = new InputStreamReader(in, Charset.forName("UTF-8"));
		BufferedReader br = new BufferedReader(isr);
		String theSentense = br.readLine() + "\n";
		in.close();
		isr.close();
		br.close();
		return theSentense;
	}

	public static void newQR() {
		try (Scanner getInfos = new Scanner(System.in)) {
			System.out.print("请输入二维码的大小（默认值 300，单位 px，属于正整数）：");
			int size = getInfos.nextInt();
			if (size <= 0) {
				System.out.println("那您怕不是生成个寂寞XD");
			}
			System.out.print("请输入文本内容：");
			String text = getInfos.next();
			String theAPI = "http://api.btstu.cn/qrcode/api.php?text=" + text + "&size=" + size;
			System.out.println("保存的路径是？（务必输入绝对路径，准确无误，以 / 结尾！若对应文件夹不存在则会自动建立。）");
			String inputPath = getInfos.next();

			inputPath = fixPath(inputPath);
			diretoryDetector(inputPath, getInfos);

			SimpleDateFormat df = new SimpleDateFormat("yy.MM.dd-HH.mm.ss.SSS");
			String filename = inputPath + "QR_" + df.format(new Date()) + ".jpg";

			downloadFile(theAPI, filename);
			System.out.println("二维码已保存至 " + filename);
		} catch (Exception e) {
			System.out.print("程序异常退出，原因：");
			e.printStackTrace();
		}
	}

	public static void custom() {
		System.out.println("您要自定义 1. 壁纸下载 还是 2. 文本输出 的 URL？");
		try (Scanner getInfo = new Scanner(System.in)) {
			String theInfo = getInfo.next();
			if (theInfo.equals("1")) {
				getWallpaper(114514);
			} else {
				printSentence(114514);
			}
		} catch (Exception e) {
			System.out.print("程序异常退出，原因：");
			e.printStackTrace();
		}
	}

}
