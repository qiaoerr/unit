//XML解析地址
package clientA;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class Clientxml {

	private static XPathFactory xpf = XPathFactory.newInstance();
	private static XPath xp = xpf.newXPath();

	public static String XPathc() {
		String path = null;
		try {
			path = xp.evaluate("/client/path/musics", new InputSource(
					"conf/client.xml"));
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return path;
	}

	// 修改下载文件路径
	public static void amend(String path) {
		Document document = load("conf/client.xml");
		Node root = document.getDocumentElement();
		if (root.hasChildNodes()) {
			NodeList ftpnodes = root.getChildNodes();
			for (int i = 0; i < ftpnodes.getLength(); i++) {
				NodeList ftplist = ftpnodes.item(i).getChildNodes();
				for (int k = 0; k < ftplist.getLength(); k++) {
					Node subnode = ftplist.item(k);
					if (subnode.getNodeType() == Node.ELEMENT_NODE
							&& subnode.getNodeName() == "musics") {
						subnode.getFirstChild().setNodeValue(path);
					}
				}
			}

		}
		doc2XmlFile(document, "conf/client.xml");
	}

	public static boolean doc2XmlFile(Document document, String filename) {
		boolean flag = true;
		try {
			/** 将document中的内容写入文件中 */
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(new File(filename));
			transformer.transform(source, result);
		} catch (Exception ex) {
			flag = false;
			ex.printStackTrace();
		}
		return flag;
	}

	public static Document load(String filename) {
		Document document = null;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.parse(new File(filename));
			document.normalize();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return document;
	}

	// public static void main(String [] args){
	//
	// System.out.println(Port());
	// }
}
