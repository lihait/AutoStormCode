package com.dom4j.test;
/**
 * 处理用户输入的不同的数据流分发方式
 * @author Administrator
 *
 */
public class GroupingWay {
       
	public static String getGroupWay(String grouping,String dataId,String data){
		
		String groupCode = null;
		
		// 随即分组和不分组
		if (grouping.equals("shuffleGrouping")) {

			groupCode = grouping + "(" + "\"" + dataId + "\"" + ")";

		}
		// 按字段分组
		else if (grouping.equals("fieldsGrouping")) {

			groupCode = grouping + "(" + "\"" + dataId + "\"" + ","
					+ "new Fields(" + "\"" + data + "\"" + "))";

		}
		// 广播发送
		else if (grouping.equals("allGrouping")) {

			groupCode = grouping + "(" + "\"" + dataId + "\"" + ","
					+ "\"" + data + "\"" + ")";

		}
		// 全局分组
		else if (grouping.equals("globalGrouping")) {

			groupCode = grouping + "(" + "\"" + dataId + "\"" + ")";

		}
		// 直接分组
		else if (grouping.equals("directGrouping")) {

			groupCode = grouping + "(" + "\"" + dataId + "\"" + ")";

		}

		
		return groupCode;
		
	}
	
}
