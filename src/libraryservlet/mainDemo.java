package libraryservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class mainDemo extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		System.out.println(request.getParameter("user"));
		
		
		try {
			test1(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void test1(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//设置response使用的码表，以控制response以什么码表向浏览器写出数据
		//((ServletRequest) response).setCharacterEncoding("UTF-8");
		
		//指定浏览器以什么码表打开
		response.setHeader("content-type","text/html;charset=UTF-8");
		//response.setContentType("text/html;charset=UTF-8");
		
		PrintWriter out = response.getWriter();
		/*out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">"
				+ "<html>"
				+ "<head>"
				+"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">"
				+"<title>山东理工大学图书馆</title>"
				+"<style type=\"text/css\">"
				+"body{"
				+"background-size: 100%;"
				+"text-align:left;"
				+"background-image:url('/LibraryServlet/img/bookph3.jpg');"
				+"background-repeat:no-repeat;"
				+"height: 200%;"
				+"width: 100%;}"
				+"#para2{"
				+"text-align:center;"
				+"font-size:25px;"
				+"color:red;"
				+"font-size:40px;}"
				+"img{"
				+"position:fixed;bottom:0px;right:1px}\n"
				+".pitem{margin-left:80px;font-size:17px;}"
				+"</style></head><body><p id=\"para2\">山东理工大学图书馆</p>"
				
				);	*/
		
		
		Library Me = new Library(request.getParameter("user"), request.getParameter("psw"));// 构造函数
		if (Me.Login() == false) {
			System.out.println("对不起，用户名或密码错误，请查实！");
		} else {
			ArrayList<Book> books = Me.getBookList();

			for (int j = 0; j < books.size(); j++) {
				Book a = books.get(j);
				
				//System.out.println(a.toString());
				
				//PrintWriter out = response.getWriter();
				out.write("\n<p class=\"pitem\">"+a.toString()+"</p>\n");
			}
		}
		out.write("<img  src=\"/LibraryServlet/img/img_flwr.gif\" tppabs=\"http://w3schools.com/css3/img_flwr.gif\" alt=\"Flowers\" width=\"224\" height=\"162\">"
		+"</body></html>");
	
		
		out.close();
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/mainDemojsp.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}

