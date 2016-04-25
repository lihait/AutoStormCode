package com.dom4j.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.*;

public class stormCode {

	public static int i = 0;

	public static int j = 0;

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		Document document = readXmlFile();

		list(document.getRootElement());

		readNode(document);

		readNodeName(document);

		PrintClass.printSpoutBoltClass();

		topology();

	}

	public static Document readXmlFile() throws Exception {

		// 得到saxReader解析器
		SAXReader saxReader = new SAXReader();
		// 指定要解析的xml文件
		Document document = saxReader.read(new File(UI.filePath));

		return document;
	}

	// 获取node节点的tilte元素属性值
	public static String[] readNode(Document document) {

		Element root = document.getRootElement();

		Element node = (Element) root.elements("nodes").get(0);

		String[] title = new String[i];

		// 将node节点对应的title属性值存储到数组中
		for (int m = 0; m < i; m++) {

			Element node_name = (Element) node.elements("node").get(m);

			title[m] = node_name.attributeValue("title");

		}
		/**
		 * 
		 * for(int m=0;m<i;m++){
		 * 
		 * System.out.println(title[m]);
		 * 
		 * } System.out.println("title = " + node_name.attributeValue("title"));
		 */
		return title;
	}

	// 获取node节点的name元素属性值
	public static String[] readNodeName(Document document) {

		Element root = document.getRootElement();

		Element node = (Element) root.elements("nodes").get(0);

		String[] name = new String[i];

		// 将node节点对应的title属性值存储到数组中
		for (int m = 0; m < i; m++) {

			Element node_name = (Element) node.elements("node").get(m);

			name[m] = node_name.attributeValue("name");

		}
		return name;
	}

	// 获取link节点的属性值
	public static String readLink(Document document) throws Exception {

		int numWorkers = 0, maxTaskParallelism = 0;

		String outPut1 = null, outPut2 = null, outPut3 = null, outPut4 = null, outPut5 = null, outPut6 = null, outPut7 = null;

		ArrayList<String> outPuts = new ArrayList<String>();

		Element root = document.getRootElement();

		Element link = (Element) root.elements("links").get(0);

		String[] node = readNode(document);

		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(
				"D:/stormCode/main/Topology2.java"));

		// 获取所有连接线的sink_node_id
		int[] link_id = new int[j];

		for (int n = 0; n < j; n++) {

			Element link_name = (Element) link.elements("link").get(n);

			String sink_node_id = link_name.attributeValue("sink_node_id");

			int sink_id = Integer.parseInt(sink_node_id);

			link_id[n] = sink_id;
		}
		// 遍历每个连接线节点
		for (int n = 0; n < j; n++) {

			Element link_name = (Element) link.elements("link").get(n);

			// 获取当前连接线的属性值
			String source_channel = link_name.attributeValue("source_channel");

			// 获取与该数据节点连接的节点的ID
			String source_node_id = link_name.attributeValue("source_node_id");

			String sink_node_id = link_name.attributeValue("sink_node_id");

			// 发出数据的节点ID
			int source_id = Integer.parseInt(source_node_id);

			// 接受数据的节点ID
			int sink_id = Integer.parseInt(sink_node_id);

			// String nodeInfor = node[source_id];

			// String data = node[sink_id];

			if (source_channel.equals("Data")) {

				String nodeInfor = node[source_id];

				String data = node[sink_id];
				// 获取需要的节点名字
				String nodeInforStr = nodeInfor.substring(0,
						nodeInfor.length() - 2);
				// 获取节点对应的并行度
				String parallelismStr = nodeInfor.substring(
						nodeInfor.length() - 1, nodeInfor.length());

				int parallelism = Integer.parseInt(parallelismStr);
				// 判断是否是数据源节点
				if (nodeInforStr.equals("Spout")) {

					String SpoutData = data;

					outPut1 = "	" + "builder.setSpout(" + "\"" + nodeInforStr
							+ "\"" + ",new Spout()," + parallelism + ")" + ";";

					outPuts.add(outPut1);

					bufferedWriter.write(outPut1 + "\r\n");

					bufferedWriter.flush();

				}

			} else if (source_channel.equals("Selected Data")) {

				String data = node[source_id];
				// 获取数据节点中的实际数据流名称和该数据流对应的分发方式
				char[] data1 = data.toCharArray();

				int tag = 0;

				for (int i = 0; i < data.length(); i++) {
					if (data1[i] == '/') {
						tag = i;
						break;
					}
				}
				// 获取实际数据流名
				String trueData = data.substring(0, tag);
				// 获取该数据流对应的分发方式
				String grouping = data.substring(tag + 1, data.length());

				// 不同的分组对应的代码实现方式
				String groupCode = GroupingWay.getGroupWay(grouping, "Spout",
						trueData);

				String nodeInfor = node[sink_id];

				// 获取需要的节点名字
				String nodeInforStr = nodeInfor.substring(0,
						nodeInfor.length() - 2);
				// 获取节点对应的并行度
				String parallelismStr = nodeInfor.substring(
						nodeInfor.length() - 1, nodeInfor.length());

				int parallelism = Integer.parseInt(parallelismStr);

				if (node[source_id - 1].substring(0,
						node[source_id - 1].length() - 2).equals("Spout")) {

					outPut2 = "	" + "builder.setBolt(" + "\"" + nodeInforStr
							+ "\"" + ",new " + nodeInforStr + "()" + ","
							+ parallelism + ")." + groupCode + ";";

					outPuts.add(outPut2);

					bufferedWriter.write(outPut2 + "\r\n");

					bufferedWriter.flush();

				} else {

					int[] sink_node_id1 = new int[j];

					for (int m1 = 0, k1 = 0; m1 < j; m1++) {

						for (int m2 = m1; m2 < j; m2++) {

							if (m1 != m2 && link_id[m1] == link_id[m2]) {

								sink_node_id1[k1] = link_id[m1];

								k1++;

							}

						}

					}

					// 获取不含重复值的数组，存储对应多个数据流的bolt的id
					int[] sink_node_id11 = new int[j];

					int b1 = 1;

					for (int a1 = 1; a1 < j; a1++) {

						if (sink_node_id1[a1] - sink_node_id1[a1 - 1] != 0) {

							sink_node_id11[b1 - 1] = sink_node_id1[a1 - 1];

							sink_node_id11[b1] = sink_node_id1[a1];

							b1++;
						}

					}

					// 判断是多对一的情况还是一对一的情况
					if (sink_node_id11[0] != 0) {

						int a = 0;

						int c = 0;

						String[] dataNode = new String[i];

						String[] boltNode = new String[i];

						for (int n1 = 0; n1 < b1 - 1; n1++) {

							int b = 0;

							c = c + 1;

							for (int n2 = 0, n3 = 0; n2 < j; n2++) {

								// 根据多条数据线的目的id相同来获取对应的bolt节点，并且得到该数据线源端数据和发出该数据的bolt
								if (link_id[n2] == sink_node_id11[n1]
										&& sink_node_id11[n1] != 0) {

									b = b + 1;

									a = n2;

									Element link_name3 = (Element) link
											.elements("link").get(a);

									String source_node_id3 = link_name3
											.attributeValue("source_node_id");

									int source_id3 = Integer
											.parseInt(source_node_id3);

									String dataStr = node[source_id3];
									//
									// 获得数据节点信息
									dataNode[n3] = dataStr;

									int k = 0;

									for (int n4 = 0; n4 < j; n4++) {

										if (link_id[n4] == source_id3) {

											k = n4;

											break;

										}
									}

									Element link_name4 = (Element) link
											.elements("link").get(k);

									String source_node_id4 = link_name4
											.attributeValue("source_node_id");

									int source_id4 = Integer
											.parseInt(source_node_id4);

									String nodeStr = node[source_id4];

									// 获得bolt节点信息
									boltNode[n3] = nodeStr;

									n3++;

								}
							}

							// 输出setBolt
							if (sink_node_id11[n1] != 0) {

								// 获取数据流名称与该数据流分发方式的分界点'/'

								String nodeInfor1 = node[sink_node_id11[n1]];

								System.out.println(nodeInfor1);

								// 获取需要的节点名字
								String nodeInforStr1 = nodeInfor1.substring(0,
										nodeInfor1.length() - 2);
								// 获取节点对应的并行度
								String parallelismStr1 = nodeInfor1.substring(
										nodeInfor1.length() - 1,
										nodeInfor1.length());

								int parallelism1 = Integer
										.parseInt(parallelismStr1);

								// 获取节点的数据流名和对应的数据分发方式

								int MAX_INT = 10000;

								int[] tags = new int[MAX_INT];

								String[] groupings = new String[MAX_INT];

								String[] groupCodes = new String[MAX_INT];

								for (int i = 0; i < dataNode.length; i++) {

									if (dataNode[i] != null) {

										char[] trueDatas = dataNode[i]
												.toCharArray();

										for (int j = 0; j < dataNode[i]
												.length(); j++) {
											// 获取‘/’的位置，位置坐标为tag.
											if (trueDatas[j] == '/') {

												tags[i] = j;

												break;

											}

										}

										groupings[i] = dataNode[i].substring(
												tags[i] + 1,
												dataNode[i].length());

										groupCodes[i] = GroupingWay
												.getGroupWay(
														groupings[i],
														boltNode[i].substring(
																0,
																boltNode[i]
																		.length() - 2),
														dataNode[i].substring(
																0, tags[i]));
									}

								}

								if (b == 2) {

									outPut3 = "	" + "builder.setBolt(" + "\""
											+ nodeInforStr1 + "\"" + ",new "
											+ nodeInforStr1 + "()" + ","
											+ parallelism1 + ")."
											+ groupCodes[0] + "."
											+ groupCodes[1] + ";";

									System.out.println("++++++++++++");

									System.out.println(groupCodes[0]);

									outPuts.add(outPut3);

									bufferedWriter.write(outPut3 + "\r\n");

									bufferedWriter.flush();

								} else if (b == 3) {

									outPut4 = "	" + "builder.setBolt(" + "\""
											+ nodeInforStr1 + "\"" + ",new "
											+ nodeInforStr1 + "()" + ","
											+ parallelism1 + ")."
											+ groupCodes[0] + "."
											+ groupCodes[1] + "."
											+ groupCodes[2] + ";";

									outPuts.add(outPut4);

									bufferedWriter.write(outPut4 + "\r\n");

									bufferedWriter.flush();

								}

								else if (b == 4) {

									outPut5 = "	" + "builder.setBolt(" + "\""
											+ nodeInforStr1 + "\"" + ",new "
											+ nodeInforStr1 + "()" + ","
											+ parallelism1 + ")."
											+ groupCodes[0] + "."
											+ groupCodes[1] + "."
											+ groupCodes[2] + "."
											+ groupCodes[3] + ";";

									outPuts.add(outPut5);

									bufferedWriter.write(outPut5 + "\r\n");

									bufferedWriter.flush();

								} else if (b == 5) {

									outPut6 = "	" + "builder.setBolt(" + "\""
											+ nodeInforStr1 + "\"" + ",new "
											+ nodeInforStr1 + "()" + ","
											+ parallelism1 + ")."
											+ groupCodes[0] + "."
											+ groupCodes[1] + "."
											+ groupCodes[2] + "."
											+ groupCodes[3] + "."
											+ groupCodes[4] + ";";

									outPuts.add(outPut6);

									bufferedWriter.write(outPut6 + "\r\n");

									bufferedWriter.flush();

								}
							}

						}

						break;

					} else {

						// 获取bolt节点id
						int k = 0;

						for (int n1 = 0; n1 < j; n1++) {

							if (link_id[n1] == source_id) {

								k = n1;

								break;

							}
						}

						Element link_name2 = (Element) link.elements("link")
								.get(k);

						String source_node_id1 = link_name2
								.attributeValue("source_node_id");

						int source_id1 = Integer.parseInt(source_node_id1);

						String nodeStr = node[source_id1];
						// 获取需要的节点名字
						String nodeInforStr2 = nodeStr.substring(0,
								nodeStr.length() - 2);

						String groupCodeInfor = GroupingWay.getGroupWay(
								grouping, nodeInforStr2, trueData);

						outPut7 = "	" + "builder.setBolt(" + "\""
								+ nodeInforStr + "\"" + ",new " + nodeInforStr
								+ "()" + "," + parallelism + ")."
								+ groupCodeInfor + ";";

						outPuts.add(outPut7);

						bufferedWriter.write(outPut7 + "\r\n");

						bufferedWriter.flush();

					}

				}

			}

		}
		String outPut = "";

		for (int i = 0; i < outPuts.size(); i++) {
			outPut = outPut + outPuts.get(i) + "\r\n";
		}
		// String outPut = outPut1 + "\r\n"+ outPut2 + "\r\n" + outPut3 + "\r\n"
		// + outPut4 + "\r\n" + outPut5 + "\r\n" + outPut6 + "\r\n" + outPut7 +
		// "\r\n";

		return outPut;
	}

	// 遍历所有节点信息，并统计node节点和link节点的数量值
	public static void list(Element element) {

		// System.out.println(element.getName());

		if (element.getName().equals("node")) {
			i++;
		} else if (element.getName().equals("link")) {
			j++;
		}

		Iterator iterator = element.elementIterator();

		while (iterator.hasNext()) {

			Element e = (Element) iterator.next();

			list(e);
		}
	}

	/**
	 * 自动生成topology类，即main函数
	 * 
	 * @throws Exception
	 */
	public static void topology() throws Exception {

		Document document = readXmlFile();

		int paralle1, paralle2;

		String topology1, topology2, topology3, topology4, topology5, topology6, topology7;

		String filePath1 = "src/com/dom4j/test/topology1.txt";

		String filePath2 = "src/com/dom4j/test/topology2.txt";

		String filePath3 = "src/com/dom4j/test/topology3.txt";

		String filePath4 = "src/com/dom4j/test/topology4.txt";

		topology2 = readLink(document);

		topology1 = fileRead.readImport(filePath1);

		topology3 = fileRead.readImport(filePath2);

		topology5 = fileRead.readImport(filePath3);

		topology7 = fileRead.readImport(filePath4);

		String workNumbers[] = new String[2];

		workNumbers = fileRead.readTabFile("D:\\stormCode\\workNumber.txt");

		paralle1 = Integer.parseInt(workNumbers[0]);

		paralle2 = Integer.parseInt(workNumbers[1]);

		topology4 = "config.setNumWorkers(" + paralle1 + ")" + ";";

		topology6 = "config.setMaxTaskParallelism(" + paralle2 + ");";

		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(
				"D:/stormCode/main/" + "Topology.java"));

		bufferedWriter.write(topology1 + "\r\n" + topology2 + "\r\n"
				+ topology3 + "\r\n" + topology4 + "\r\n" + topology5 + "\r\n"
				+ topology6 + "\r\n" + topology7);

		bufferedWriter.flush();

	}
}
