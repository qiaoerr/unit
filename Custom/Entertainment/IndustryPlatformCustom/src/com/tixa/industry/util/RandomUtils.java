/**
 * FileName: RandomUtils.java  
 * PackageName:com.tixa.portal.utils
 * @author Taransky   
 * CreateTime: 2012-5-14 ����3:44:11   
 * Description:   
*/  
package com.tixa.industry.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomUtils {
	
	
	/**
	 * ����һ�������
	 * @param maxNum
	 * @return
	 */
	public  static int getRandom(int maxNum) {
		List<Integer> list = getRandom(maxNum,1);
		return list.get(0);
	}
	
	
	/**
	 * @param maxNum  ������ȡ�����ֵ 
	 * @param count	      ���������ĸ���
	 * @return
	 */
	public static List<Integer> getRandom(int maxNum,int count){
		List<Integer> a = new ArrayList<Integer>();
		Random random = new Random();
		for(int i=0;i<count;i++){
			int tmp = random.nextInt(maxNum);
			boolean canBe = isUseable(tmp, a);
			if(canBe){
				a.add(new Integer(tmp));
			}else{
				i--;
			}
		}
		return a;
	}
	
	/**
	 * @param number ����������   
	 * @param array	  ���������
	 * @return
	 */
	private static boolean isUseable(int number,List<Integer> array){
		boolean flag = true;
		for(int i=0;i<array.size();i++){
			if(number == array.get(i).intValue()){
				flag = false;
			}
		}
		return flag;
	}
	
	/**
	 * ����һ���µĲ��ظ��������
	 * @param maxNum
	 * @param list
	 * @return
	 */
	public static int getUnusedRandom(int maxNum,List<Integer> list){
		boolean flag = true;
		int tmp = 0;
		while(flag){
			Random random = new Random();
			tmp = random.nextInt(maxNum);
			boolean canBe = isUseable(tmp, list);
			if(canBe){
				flag = false;
				list.add(new Integer(tmp));
			}
		}
		return tmp;
	}
}
