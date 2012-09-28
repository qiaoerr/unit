/*
 * 这是服务器XML解析类
 */
package server;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.xml.sax.InputSource;

public class Serverinfor_parse {

	private static XPathFactory xpf = XPathFactory.newInstance();
	private static XPath xp = xpf.newXPath();

	public static String Musicpath() {
		String music = null;
		try {
			music = xp.evaluate("/server/music", new InputSource(
					"conf/server.xml"));
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return music;

	}

}
