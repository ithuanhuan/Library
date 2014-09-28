package libraryservlet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 * ���ฺ����ͼ��ݵĽ���������������¼����ѯ�Ȳ�����
 * @author �̻�������Ρ���½��
 * @version 0.20
 * 
 * 
 */
public class Library {
	private String inputNumber;
	private String inputPassword;
	
	private HttpClient httpClient = new DefaultHttpClient(
			new ThreadSafeClientConnManager());
	
	/**
	 * ���캯��
	 * @param inputNumber ѧ��
	 * @param inputPassword ����
	 */
	public Library(String inputNumber, String inputPassword) {
		this.inputNumber = inputNumber;
		this.inputPassword = inputPassword;
	}
	
	/**
	 * ��¼���������Լ����û����Ƿ���ȷ�����еĲ�������Ҫ��¼����С�
	 * @return ��¼�ɹ����� <b>true</b>����¼ʧ�ܷ��� <b>false</b>
	 * 
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public boolean Login() {
		String url = "http://222.206.65.12/reader/redr_verify.php";
		
		// ��������������ò���
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("number", inputNumber));
		params.add(new BasicNameValuePair("passwd", inputPassword));
		params.add(new BasicNameValuePair("select", "cert_no"));
		params.add(new BasicNameValuePair("returnUrl", ""));
		
		try {
			String result1 = post(url, params, httpClient);// ��½ҳ��ɹ�Ϊnull
			
			if(result1 == null) {
				return true;
			}
			else  
				return false;
			
		} catch (Exception e) {
			return false;
		}
	}
	
	
	/**
	 * ��ȡͼ����н��ĵ�ͼ����Ϣ��
	 * ���ص� Book ������
	 * 
	 * @see Book
	 * 
	 * @return ���ĵ����е� Book �ļ���
	 */
	public ArrayList<Book> getBookList() throws Exception {
		String url1 = "http://222.206.65.12/reader/book_lst.php";
		
		String result = get(url1, httpClient);// ��ȡͼ���б������ȵ�½��
		Document doc = Jsoup.parse(result);
				
		//Element table = doc.getElementsByTag("table").get(0);
		Element table = doc.getElementsByTag("table").get(0);
		Elements trs = table.getElementsByTag("tr");

		ArrayList<Book> list=new ArrayList();
		
		for (int i = 1; i < trs.size(); i++) {
			Element tr1 = trs.get(i);
			Elements tds = tr1.getElementsByTag("td");
			
			Book book = new Book();
			
			for (int j = 0; j < tds.size() - 2; j++) {	
				
				
				Element td = tds.get(j);
				String text = td.text();
				if(j==0) {
					book.num=text;
				}
				else if(j==1) {
					book.bookName=text;
				}
				else if(j==2) {
					book.author=text;
				}
					
				else if(j==3) {
					book.borrowDate=text;
				}
				else if(j==4) {
					book.returnDate=text;
				}
				else if(j==5) {
					book.place=text;
				}
				
			}
			
			list.add(book);
		}

		return list;
	}


	private String post(String url, List<NameValuePair> params,
			HttpClient httpClient) throws ClientProtocolException, IOException {
		// �����������

		HttpPost request = new HttpPost(url);

		// ���Ӳ���
		request.addHeader("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		request.addHeader("Accept-Encoding", "gzip,deflate,sdch");
		request.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
		request.addHeader("Cache-Control", "max-age=0");
		request.addHeader("Connection", "keep-alive");
		request.addHeader("Host", "222.206.65.12");
		request.addHeader("Referer",
				"http://222.206.65.12/reader/redr_cust_result.php");
		request.addHeader(
				"User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.143 Safari/537.36");
		request.addHeader("Origin", "http://222.206.65.12");
		request.addHeader(
				"User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.143 Safari/537.36");

		// ��������ʵ�岢ͳһ����
		HttpEntity httpEntity = new UrlEncodedFormEntity(params, "utf-8");
		request.setEntity(httpEntity);

		// �ͻ���ִ��������Ӧ
		HttpResponse response = httpClient.execute(request);

		// ��ȡ���ж���Ӧ�룬״̬����ֵ����200
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			// ��ȡ��Ӧʵ�����
			HttpEntity entity = response.getEntity();

			// //���ظö�����ַ�����ʾ�����ı���ʽ��ʾ�˶�����ַ�����
			String result = EntityUtils.toString(entity);

			// ͳһ����תΪutf-8����
			result = new String(result.getBytes("iso8859-1"), "utf-8");

			// �������û�йر�
			request.releaseConnection();

			return result;
		}

		return null;
	}

	private String get(String url, HttpClient httpClient)
			throws ClientProtocolException, IOException {

		// �����������
		HttpGet request = new HttpGet(url);

		// ���Ӳ���
		request.addHeader("Accept", "*");
		request.addHeader("Accept-Encoding", "gzip,deflate,sdch");
		request.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
		request.addHeader("Connection", "keep-alive");
		request.addHeader("Content-Type", "application/x-www-form-urlencoded");
		request.addHeader("Accept-Charset", "utf-8");
		request.addHeader("Host", "222.206.65.12");
		request.addHeader("Referer",
				"http://222.206.65.12/reader/redr_verify.php");
		request.addHeader(
				"User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.143 Safari/537.36");

		// ��ȡ��Ӧ����
		HttpResponse response = httpClient.execute(request);

		// ��ȡ��Ӧʵ���ȡ���ж���Ӧ�룬״̬����ֵ����200
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

			// ��ȡ��Ӧʵ�����
			HttpEntity entity = response.getEntity();

			String result = EntityUtils.toString(entity);// ���ظö�����ַ�����ʾ�����ı���ʽ��ʾ�˶�����ַ�����

			result = new String(result.getBytes("iso8859-1"), "utf-8");

			// �������û�йر�
			request.releaseConnection();

			return result;
		}

		return null;

	}

}
