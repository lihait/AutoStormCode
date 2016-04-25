package com.dom4j.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

public class PrintClass {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		printSpoutBoltClass();
		
	}
    //实现Spout类和Bolt类的自动生成
	public static void printSpoutBoltClass() throws Exception {
		
		String filePath1 = "src/com/dom4j/test/spouts.txt";
		
		String filePath2 = "src/com/dom4j/test/bolts.txt";
		
		String filePath3 = "src/com/dom4j/test/spoutImport.txt";
		
		String filePath4 = "src/com/dom4j/test/boltImport.txt";

		Document document = stormCode.readXmlFile();

		int size = stormCode.i;
		
		String[] nodeTitle = new String[size];
		
		String[] nodeName = new String[size];
		
		nodeTitle = stormCode.readNode(document);
		
		nodeName = stormCode.readNodeName(document);
		
		//String[] nodeSpout = new String[size];
		//分别获得title属性为Spout和bolt的节点
		ArrayList<String> nodeSpouts = new ArrayList<String>();
		
		ArrayList<String> nodeBolts = new ArrayList<String>();
		
		for(int m=0;m<size;m++){
			
			if(nodeName[m].equals("File")){

				nodeSpouts.add(nodeTitle[m]);
	
			}else if(nodeName[m].equals("Concatenate")){
				
				nodeBolts.add(nodeTitle[m]);
				
			}
		
		}
		//自动生成spout类
		for(int k=0;k<nodeSpouts.size();k++){
			
			String spout = nodeSpouts.get(k).substring(0, nodeSpouts.get(k).length()-2);

			//System.out.println("public class " + spout + " implements IRichSpout {");
			
			String impFileSpout = fileRead.readImport(filePath3);
			
			String head = "public class " + spout + " implements IRichSpout {";
			
			String body = fileRead.readImport(filePath1);
			
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("D:/stormCode/spouts/" + spout + ".java"));
			
			bufferedWriter.write(impFileSpout + "\r\n" + head + "\r\n" + body + "\r\n");
			
			bufferedWriter.flush();
			
			//fileRead.readTxtFileSpout(filePath1,nodeSpouts.get(k));
			
			System.out.println();
			
		}
		//自动生成bolt类
		for(int l=0;l<nodeBolts.size();l++){
			
			String bolt = nodeBolts.get(l).substring(0, nodeBolts.get(l).length()-2);
			
			System.out.println("public class " + bolt + " implements IRichBolt{");
			
			String head = "public class " + bolt + " implements IRichBolt{";
			
			String impFileBolt = fileRead.readImport(filePath4);
			
			String bodyBolt = fileRead.readImport(filePath1);
			
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("D:/stormCode/bolts/"+ bolt + ".java")); 
			
			bufferedWriter.write(impFileBolt + "\r\n" + head + "\r\n" + bodyBolt + "\r\n");
			
			bufferedWriter.flush();
			
			//fileRead.readTxtFileBolt(filePath2,nodeBolts.get(l));
			
			System.out.println();
			
		}
		

	}
}
