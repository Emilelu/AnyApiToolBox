package me.emilelu.AnyApiToolbox;

import java.io.IOException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException {
//		System.out.println("[Debug] Now your ip is " + Functions.getContent("https://api.ip.sb/jsonip"));
		System.out.println("欢迎使用 AnyApi 工具箱 [Version 0.2.2-beta]");
		System.out.println("目前，本软件接入了 小歪、搏天以及 Lolicon API，使用到了各 API 的部分功能。");
		System.out.println("本软件的核心功能为「批量」，包括但不限于批量下载、批量输出+保存。");
		System.out.println("当然，您也可以自行输入一个 URL 来使用功能。");
		System.out.println();
		System.out.println("请输入对应编号以使用对应功能");
		System.out.println("小歪API 提供：1. 二次元动漫壁纸 || 2. Menhera 酱壁纸 || 3. 高清壁纸 || 4. 一言语录 || 5. 舔狗日记");
		System.out.println("搏天API 提供：6. 动漫壁纸 || 7. 妹子壁纸 || 8. 风景壁纸 || 9. 毒鸡汤 || 10. 文本生成二维码");
		System.out.println("Lolicon API 提供：11. 随机色图");
		System.out.println("[附加功能] 自定义 URL 请输入 114514");

		try (Scanner getInfo = new Scanner(System.in)) {
			int theInfo = getInfo.nextInt();
			if (theInfo == 11) {
				Functions.Lolicon();
			} else if (theInfo == 1 || theInfo == 2 || theInfo == 3 || theInfo == 6 || theInfo == 7 || theInfo == 8) {
				Functions.getWallpaper(theInfo);
			} else if (theInfo == 4 || theInfo == 5 || theInfo == 9) {
				Functions.printSentence(theInfo);
			} else if (theInfo == 10) {
				Functions.newQR();
			} else if (theInfo == 114514) {
				Functions.custom();
			} else {
				System.out.println("输入的内容有误！");
				System.exit(0);
			}
		} catch (Exception e) {
			System.out.print("程序异常退出，原因：");
			e.printStackTrace();
		}

		System.out.println("任务完成，感谢您的使用！");
	}

}
