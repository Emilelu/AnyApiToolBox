package me.emilelu.AnyApiToolbox;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Functions {

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
			String inputPath = new String(getInfos.next());

			inputPath = fixPath(inputPath);
			diretoryDetector(inputPath, getInfos);

			for (int i = 0; i < times; i++) {
				SimpleDateFormat df = new SimpleDateFormat("yy.MM.dd-HH.mm.ss.SSS");
				String filename = inputPath + df.format(new Date()) + ".jpg";

				if (what == 6 || what == 7 || what == 8) {
					String temp = null;
					String json = getContent(theAPI);
					String pattern = "http.*jpg";
					json = json.replace("\\", "");
					Pattern r = Pattern.compile(pattern);
					Matcher m = r.matcher(json);
					while (m.find()) {
						temp = m.group();
					}
					downloadFile(temp, filename);
				} else {
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
			InputStream is = fileUrl.openStream();
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
				String inputPath = new String(getInfos.next());

				inputPath = fixPath(inputPath);
				diretoryDetector(inputPath, getInfos);

				FileOutputStream fs = new FileOutputStream(new File(inputPath + "/Sentences.txt"));
				PrintStream p = new PrintStream(fs);
				for (int i = 0; i < times; i++) {
					String theSentence = getContent(theAPI);
					p.println(theSentence);
					System.out.println(theSentence);
				}
				System.out.println("所有语句已保存至 " + inputPath + " 下的 Sentences.txt。");
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
			String inputPath = new String(getInfos.next());

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
