package com.dom4j.test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.Reader;

public class fileRead {
	public static void readTxtFileHead(String filePath) {
		try {
			String encoding = "GBK";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				//输出到.java文件中
				BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("D:/stormCode/main/Topology1.java"));
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					bufferedWriter.write(lineTxt + "\r\n");
					bufferedWriter.flush();
					System.out.println(lineTxt);
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}

	}
	public static void readTxtFile(String filePath) {
		try {
			String encoding = "GBK";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				//输出到.java文件中
				BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("D:/stormCode/main/Topology3.java"));
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					bufferedWriter.write(lineTxt+ "\r\n");
					bufferedWriter.flush();
					System.out.println(lineTxt);
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}

	}
	public static void readTxtFileSpout(String filePath,String spoutName) {
		try {
			String encoding = "GBK";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				//输出到.java文件中
				BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("D:/stormCode/spouts/" + spoutName + ".java"));
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					bufferedWriter.write(lineTxt+ "\r\n");
					bufferedWriter.flush();
					System.out.println(lineTxt);
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}

	}
	public static void readTxtFileBolt(String filePath, String boltName) {
		try {
			String encoding = "GBK";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				//输出到.java文件中
				BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("D:/stormCode/bolts/" + boltName + ".java") );
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					bufferedWriter.write(lineTxt+ "\r\n");
					bufferedWriter.flush();
					System.out.println(lineTxt);
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}

	}
    /**
     * public static void main(String argv[]) {
		String filePath = "src/com/dom4j/test/spouts.txt";
		readTxtFile(filePath);
	}
     * @param argv
     */
	public static String readImport(String filePath) {
		
		StringBuilder str = new StringBuilder();
		
		try {
			String encoding = "GBK";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				//输出到.java文件中
				//BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("D:/stormCode/main/Topology1.java"));
				String lineTxt = null;
				
				while ((lineTxt = bufferedReader.readLine()) != null) {
					//bufferedWriter.write(lineTxt + "\r\n");
					//bufferedWriter.flush();
					str.append(lineTxt + "\r\n");
					//System.out.println(lineTxt);
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		return str.toString();

	}
public static String[] readTabFile(String filePath) {
		
		String[] lineNum = new String[2];
		
		try {
			String encoding = "GBK";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);		
				String lineTxt = null;
				int i = 0;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					lineNum[i] = lineTxt;
					System.out.println(lineNum[i]);
					i = i + 1;
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		return lineNum;
	}
}
