package _01实验一_基础的IO流编程技术;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Wu_Being
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class IOTest6_1 {
	@SuppressWarnings("unchecked")
	private ArrayList list = new ArrayList();

	@SuppressWarnings("unchecked")
	public IOTest6_1(String fn) throws IOException {
		FileReader fr = new FileReader(fn);
		BufferedReader br = new BufferedReader(fr);
		String ln;
		while ((ln = br.readLine()) != null) {
			list.add(ln);
		}
		br.close();
	}

	public String getLine(int n) {
		if (n < 0) {
			throw new IllegalArgumentException();
		}

		return (n < list.size() ? (String) list.get(n) : null);
	}

	public static void main(String args[]) {
		String s_FileName = "test.txt";
		try {
			IOTest6_1 lc = new IOTest6_1(s_FileName);
			int i = 0;
			String ln;
			while ((ln = lc.getLine(i++)) != null) {
				System.out.println(ln);
			}
		} catch (IOException e) {
			System.err.println(e);
		}
	}
}
