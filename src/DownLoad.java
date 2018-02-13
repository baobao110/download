import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DownLoad {
	
	static int name = 0;
	
	public static void getPage() throws Exception {
//		URL url = new URL("http://www.qq.com");
//		URL url = new URL("http://www.sina.com.cn");
		URL url = new URL("http://www.ifeng.com");
		URLConnection conn = url.openConnection();
		conn.connect();
		
		InputStream is = conn.getInputStream();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
			String data = null;
			String msg = "";
			while (null != (data = br.readLine())) {
				msg += data;
			}
			
			System.out.println(msg);
			
			processImage(msg);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static  void processImage(String html) {
		Document doc = Jsoup.parse(html);
		Elements es = doc.getElementsByTag("img");
		for (Element el : es) {
			String imgUrl = el.attr("src");
			System.out.println(imgUrl);
			
			try {
				downImage(imgUrl);
			} catch (Exception e) {
				continue;
			}
		}
	}
	
	public static void downImage(String imageUrl) throws Exception {
		
		URL url = new URL(imageUrl);
		URLConnection conn = url.openConnection();
		conn.connect();
		
		try(BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File("d:"+File.separator+"1"+File.separator+name++ + ".jpg")))) {
			byte[] data = new byte[1024];
			int length = -1;
			while (-1 != (length = bis.read(data))) {
				bos.write(data, 0, length);
				bos.flush();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
