package libraryservlet;
import java.util.Scanner;

/**
 * @author �̻�������Ρ���½��
 * @version 0.20
 * 
 * ������ԵĶ���
 */
public class Book {
	/**
	 * ��������
	 */
	public String num;//�����
	
	/**
	 * ����
	 */
	public String bookName;
	
	/**
	 * ������
	 */
	public String author;//������
	
	/**
	 * ��������
	 */
	public String borrowDate;//��������
	
	/**
	 * Ӧ������
	 */
	public String returnDate;//Ӧ������
	
	/**
	 * �ݲص�
	 */
	public String place;//�ݲص�
	
	@Override
	public String toString() {
		return "�����:" + num+ " "+" ����:" + bookName+" "+"������:"+ author+" "+"��������:" + borrowDate+" Ӧ������:"+returnDate+" �ݲص�:"+place;
	}
}
