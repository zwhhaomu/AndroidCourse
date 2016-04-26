package com.example.remeberword;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.example.util.HttpUtil;
import com.example.util.StreamTool;

public class WordService {
	public  List<Word> getLastJsonUser() throws Exception{
		String path=HttpUtil.BASE_URL+"ListWordServlet?format=json";
		URL url=new URL(path);
		URLConnection conn=url.openConnection();
		conn.setConnectTimeout(5000);
		conn.setDoOutput(true);
		conn.setDoInput(true);
		InputStream inStream=conn.getInputStream();
		return parseJSON(inStream);
	}

	private  List<Word> parseJSON(InputStream inStream) throws Exception {
		List<Word> words=new ArrayList<Word>();
		byte[] data=com.example.util.StreamTool.read(inStream);
		String json=new String(data);
		JSONArray array=new JSONArray(json);
		for(int i=0;i<array.length();i++){
			JSONObject jsonObject=array.getJSONObject(i);
			Word word=new Word(jsonObject.getInt("id"),jsonObject.getString("word"),jsonObject.getString("detail"));	
			words.add(word);
		}
		return words;
	}
	private  List<Word> parseXML(InputStream inStream) throws Exception {
		List<Word> words=new ArrayList<Word>();
		byte[] data=StreamTool.read(inStream);
		String xml=new String(data);
		// step 1: ���dom���������������������������ڴ�������Ľ�������
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		// step 2:��þ����dom������ 
		DocumentBuilder builder=factory.newDocumentBuilder();
		// step3: ����һ��xml�ĵ������Document���󣨸���㣩  
		Document doc=(Document) builder.parse(inStream);
		NodeList list=doc.getElementsByTagName("words");
		for(int i=0;i<list.getLength();i++){
			Element wordElement = (Element)list.item(i); 
			Word word=new Word(
					Integer.parseInt(wordElement.getElementsByTagName("id").item(0).getFirstChild().getNodeValue()),
					wordElement.getElementsByTagName("word").item(0).getFirstChild().getNodeValue(),
					wordElement.getElementsByTagName("detail").item(0).getFirstChild().getNodeValue()
					);
			words.add(word);
		}
		return words;
	}
}
