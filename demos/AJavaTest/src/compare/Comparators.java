/**
  @Title: Comparators.java
  @Package compare
  @Description: TODO
  Copyright: Copyright (c) 2011 
  
  @author Comsys-Administrator
  @date 2014-3-6 ����06:06:07
  @version V1.0
 */

package compare;

/**
 * @ClassName: Comparators
 * @Description: TODO
 * @author Comsys-Administrator
 * @date 2014-3-6 ����06:06:07
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
					System.err.println("δ�ҵ����ʵıȽ���");
					return 1;// ���ظ�����������ߡ����������������ұߡ�δ�ҵ����ʵıȽ���ʱĬ�������ұ�
				}
			}

			public int compare(String o1, String o2) {

				String s1 = (String) o1;
				String s2 = (String) o2;
				/*
				 * System.out.println("s1=="+s1+"                s2=="+s2); 
				 * ȡ���������ڵ�������firstname������lastname
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
				 * ȡ���������ڵ������˵�����age
				 */
				return (val1 < val2 ? -1 : (val1 == val2 ? 0 : 1));

			}

			public int compare(char o1, char o2) {
				/*
				    * System.out.println("o1=="+o1+"                o2=="+o2); 
				    * ȡ���������ڵ������˵��Ա�
				    * ��return���߼����ͣ�
				    * 
				    ���������˵��Ա�ֱ����к�Ů������һ���˵��Ա��ǡ��С����򷵻�1
				    ������1�Ķ���compare������˵������Ϊo1���У�>��Ů��o2����o1>o2����1�������������������򼴽�С�ģ�Ů������ǰ�� )
				    
				    �������Ļ������ſ�compare�����Ľ���
				    int compare(Object o1, Object o2) ����һ���������͵�����
				     ���Ҫ������������
				     ��o1 С��o2������-1������������ȷ���0��01����02����1��������
				     ���Ҫ���ս�������
				     ��o1 С��o2������1������������ȷ���0��01����02����-1��������
				     ��01����02����-1������������ȷ���0��o1 С��o2������1��������
				     ����-1���������
				    */

				return ((o1 == o2) ? 0 : (o1 == '��' ? 1 : -1));

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
				/*return �߼��������£�
				 * ˭����ƴ��firstname��ǰ��˭����ǰ�档
				 * Ȼ�������lastname�����������ͬ����
				 * Ů��sex==false��ǰͷ����sex��ֵtrue��ʾ���ԣ�false��ʾŮ�ԣ�
				 * ������ֺ��Ա���ͬ��age����С����ǰͷ��
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
