package me.emilelu.XWApiToolbox;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		System.out.println("欢迎使用 小歪API 工具箱。");
		System.out.println("本软件可以批量下载指定数量的 小歪API 提供的壁纸。");
		System.out.println("也可以批量输出指定数量的其提供的随机句子。");
		System.out.println("\n请选择功能项");
		System.out.println("1. ACG 壁纸下载 || 2. Menhera 酱壁纸下载 || 3. 高清壁纸下载\n4. 一言语录 || 5. 舔狗日记");

		try (Scanner getInfo = new Scanner(System.in)) {
			int theInfo = getInfo.nextInt();
			if (theInfo == 1 || theInfo == 2 || theInfo == 3) {
				Functions.getWallpaper(theInfo);
			} else if (theInfo == 4 || theInfo == 5) {
				Functions.printSentence(theInfo);
			} else {
				System.out.println("输入的内容有误！");
			}
		} catch (Exception e) {
			System.out.print("程序异常退出，原因：");
			e.printStackTrace();
		}

		System.out.println("\n任务完成，感谢您的使用！");
	}

}
