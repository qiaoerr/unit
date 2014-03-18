/**
  @Title: Comparators.java
  @Package compare
  @Description: TODO
  Copyright: Copyright (c) 2011 
  
  @author Comsys-Administrator
  @date 2014-3-6 下午06:06:07
  @version V1.0
 */

package compare;

/**
 * @ClassName: Comparators
 * @Description: TODO
 * @author Comsys-Administrator
 * @date 2014-3-6 下午06:06:07
 * 
 */

public class Comparators {

	public static java.util.Comparator<Object> getComparator() {
		return new java.util.Comparator<Object>() {

			public int compare(Object o1, Object o2) {
				if (o1 instanceof String) {
					return compare((String) o1, (String) o2);
				} else if (o1 instanceof Integer) {
					return compare((Integer) o1, (Integer) o2);
				} else if (o1 instanceof Person) {
					return compare((Person) o1, (Person) o2);
				} else {
					System.err.println("未找到合适的比较器");
					return 1;// 返回负数，排在左边。返回正数，排在右边。未找到合适的比较器时默认排在右边
				}
			}

			public int compare(String o1, String o2) {

				String s1 = (String) o1;
				String s2 = (String) o2;
				/*
				 * System.out.println("s1=="+s1+"                s2=="+s2); 
				 * 取出数组相邻的两个姓firstname或者名lastname
				 */
				int len1 = s1.length();
				int len2 = s2.length();
				int n = Math.min(len1, len2);
				char v1[] = s1.toCharArray();
				char v2[] = s2.toCharArray();
				int pos = 0;
				while (n-- != 0) {
					char c1 = v1[pos];
					char c2 = v2[pos];
					if (c1 != c2) {
						return c1 - c2;
					}
					pos++;
				}
				return len1 - len2;
			}

			public int compare(Integer o1, Integer o2) {

				int val1 = o1.intValue();
				int val2 = o2.intValue();

				/*
				 * System.out.println("val1=="+val1+"                val2=="+val2); 
				 * 取出数组相邻的两个人的年龄age
				 */
				return (val1 < val2 ? -1 : (val1 == val2 ? 0 : 1));

			}

			public int compare(char o1, char o2) {
				/*
				    * System.out.println("o1=="+o1+"                o2=="+o2); 
				    * 取出数组相邻的两个人的性别
				    * 对return的逻辑解释：
				    * 
				    比如两个人的性别分别是男和女，当第一个人的性别是‘男’，则返回1
				    （返回1的对于compare方法来说，即认为o1（男）>（女）o2，而o1>o2返回1则按照升序排序，升序排序即将小的（女）排在前面 )
				    
				    还不懂的话：接着看compare方法的解释
				    int compare(Object o1, Object o2) 返回一个基本类型的整型
				     如果要按照升序排序，
				     则o1 小于o2，返回-1（负数），相等返回0，01大于02返回1（正数）
				     如果要按照降序排序
				     则o1 小于o2，返回1（正数），相等返回0，01大于02返回-1（负数）
				     即01大于02返回-1（负数），相等返回0，o1 小于o2，返回1（正数）
				     返回-1的排在左边
				    */

				return ((o1 == o2) ? 0 : (o1 == '男' ? 1 : -1));

			}

			public int compare(Person o1, Person o2) {

				String firstname1 = o1.getFirstname();
				String firstname2 = o2.getFirstname();
				String lastname1 = o1.getLastname();
				String lastname2 = o2.getLastname();
				char sex1 = o1.getSex();
				char sex2 = o2.getSex();
				Integer age1 = o1.getAge();
				Integer age2 = o2.getAge();
				/*return 逻辑解释如下：
				 * 谁的姓拼音firstname靠前，谁就排前面。
				 * 然后对名字lastname进行排序。如果同名，
				 * 女性sex==false排前头。（sex的值true表示男性，false表示女性）
				 * 如果名字和性别都相同，age年龄小的排前头。
				 */
				return (compare(firstname1, firstname2) == 0 ? (compare(
						lastname1, lastname2) == 0 ? (compare(sex1, sex2) == 0 ? (compare(
						age1, age2) == 0 ? 0 : compare(age1, age2)) : compare(
						sex1, sex2))
						: compare(lastname1, lastname2))
						: compare(firstname1, firstname2));

			}

		};
	}

}
