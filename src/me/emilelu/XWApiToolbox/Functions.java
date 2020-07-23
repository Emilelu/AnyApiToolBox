package me.emilelu.XWApiToolbox;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Functions {

	public static void getWallpaper(int what) {
		String theAPI;
		if (what == 1) {
			theAPI = "https://api.ixiaowai.cn/api/api.php";
			System.out.println("您要下载几张 ACG 壁纸？");
		} else if (what == 2) {
			theAPI = "https://api.ixiaowai.cn/mcapi/mcapi.php";
			System.out.println("您要下载几张 Menhera 酱壁纸？");
		} else {
			theAPI = "https://api.ixiaowai.cn/gqapi/gqapi.php";
			System.out.println("您要下载几张 高清壁纸？");
		}
		try (Scanner getInfos = new Scanner(System.in)) {
			int times = getInfos.nextInt();
			if (times <= 0) {
				System.out.println("那您怕不是下个寂寞XD");
				System.exit(0);
			}
			System.out.println("保存的路径是？（务必输入绝对路径，准确无误，以 / 结尾！若对应文件夹不存在则会自动建立。）");
			String inputPath = new String(getInfos.next());

			/* Judge and fix input path */
			String userSystem = System.getProperties().getProperty("os.name");
			if (userSystem.contains("Windows")) {
				System.out.println("检测到您是 Windows 用户，正在检查路径...");
				if (!inputPath.endsWith("/")) {
					if (inputPath.endsWith(":")) {
						inputPath = inputPath + "/";
					} else {
						inputPath = inputPath + ":/";
					}
					System.out.println("程序已尝试自动纠正路径，现在的路径是 " + inputPath + "，请确认是否无误(y/n)。");
					String isCorrect = getInfos.next();
					if (isCorrect.equals("y")) {
						// CONTINUE
					} else {
						System.out.println("路径有误，请重新填写。");
						System.exit(0);
					}
				}
			} else {
				System.out.println("检测到您非 Windows 用户，正在检查路径...");
				if (!inputPath.endsWith("/")) {
					if (!inputPath.startsWith("/") || !inputPath.startsWith("~/")) {
						inputPath = "/" + inputPath;
					}
					if (!inputPath.endsWith("/")) {
						inputPath = inputPath + "/";
					}
					System.out.println("程序已尝试自动纠正路径，现在的路径是 " + inputPath + "，请确认是否无误(y/n)。");
					String isCorrect = getInfos.next();
					if (isCorrect.equals("y")) {
						// CONTINUE
					} else {
						System.out.println("路径有误，请重新填写。");
						System.exit(0);
					}
				}
			}
			File targetFile = new File(inputPath);
			if (!targetFile.exists() && targetFile.isDirectory()) {
				targetFile.mkdirs();
			} else if (targetFile.isDirectory()) {
				// CONTINUE
			} else {
				System.out.println("路径异常。");
				System.exit(0);
			}
			/* End */

			for (int i = 0; i < times; i++) {
				SimpleDateFormat df = new SimpleDateFormat("yy.MM.dd-HH.mm.ss.SSS");
				String filename = targetFile + df.format(new Date()) + ".png";
				downloadFile(theAPI, filename);
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
		String theAPI;
		if (what == 4) {
			theAPI = "https://api.ixiaowai.cn/ylapi/index.php";
			System.out.println("您要输出几句 一言语录？");
		} else {
			theAPI = "https://api.ixiaowai.cn/tgrj/index.php";
			System.out.println("您要输出几句 舔狗日记？");
		}
		try (Scanner getInfos = new Scanner(System.in)) {
			int times = getInfos.nextInt();
			for (int i = 0; i < times; i++) {
				getContent(theAPI);
			}
		} catch (Exception e) {
			System.out.print("程序异常退出，原因：");
			e.printStackTrace();
		}
	}

	public static void getContent(String link) {
		try {
			URL url = new URL(link);
			InputStream in = url.openStream();
			InputStreamReader isr = new InputStreamReader(in);
			BufferedReader br = new BufferedReader(isr);
			System.out.println(br.readLine() + "\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
