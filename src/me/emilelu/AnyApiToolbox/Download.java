package me.emilelu.AnyApiToolbox;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

public class Download {

	public static void downloadFile(String url, String saveAddress) {
		try {
			URL fileUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) fileUrl.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4217.0 Safari/537.36 Edg/86.0.601.0");
			InputStream is = conn.getInputStream();
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

	public static String getContent(String link) throws IOException {
		URL url = new URL(link);
		HttpURLConnection mlgb = (HttpURLConnection) url.openConnection();
		mlgb.setRequestProperty("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4217.0 Safari/537.36 Edg/86.0.601.0");
		InputStream in = mlgb.getInputStream();
		InputStreamReader isr = new InputStreamReader(in, Charset.forName("UTF-8"));
		BufferedReader br = new BufferedReader(isr);
		String theSentense = br.readLine() + "\n";
		in.close();
		isr.close();
		br.close();
		return theSentense;
	}

}
