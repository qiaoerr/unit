package study.lrc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import study.download.FileUtils;
import study.model.Mp3Info;

public class LrcProcessor {
	public ArrayList<Queue<?>> process(Mp3Info mp3Info) {
		ArrayList<Queue<?>> timesLyric = new ArrayList<Queue<?>>();
		try {
			Queue<Long> times = new LinkedList<Long>();
			Queue<String> lyrics = new LinkedList<String>();

			String lrcPath = new FileUtils().getSDPATH() + "mp3"
					+ File.separator + mp3Info.getLrcName();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(lrcPath)));
			// System.out.println(System.getProperty("file.encoding"));
			String msg = reader.readLine();
			while (msg != null) {
				String time_s = msg.substring(msg.lastIndexOf("[") + 1,
						msg.lastIndexOf("]"));
				String lyric = msg.substring(msg.lastIndexOf("]") + 1,
						msg.length());
				long time = string2long(time_s);
				times.offer(time);
				lyrics.offer(lyric);
				msg = reader.readLine();
			}
			reader.close();
			timesLyric.add(times);
			timesLyric.add(lyrics);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return timesLyric;
	}

	private long string2long(String time) {
		int minute = Integer.parseInt(time.substring(0, time.indexOf(":")));
		int second = Integer.parseInt(time.substring(time.indexOf(":") + 1,
				time.indexOf(".")));
		int millisecond = Integer.parseInt(time.substring(
				time.indexOf(".") + 1, time.length()));

		return minute * 60 * 1000 + second * 1000 + millisecond * 10;
	}
}
