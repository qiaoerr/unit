package tools.decimalFormat;

import java.text.DecimalFormat;

public class FormatDemo {

	/**
	 * @param args
	 * 
	 *  ��   ��                        λ    ��                                                    ��  ��
	 *    0                              ����                   �����������֣�ÿһ��0��ʾһλ���������֣������λ����������ʾ0
	 *    #               ����                   �����������֣�ÿһ��#��ʾһλ���������֣������λ����������ʾ
	 *    .               ����                   С����ָ�������ҵ�С���ָ���
	 *    -               ����                   С����ָ�������ҵ�С���ָ���
	 *    ,               ����                   ����ָ���
	 *    E               ����                   �ָ���ѧ�������е�β����ָ��
	 *    ;            ��ģʽ�߽�                �ָ������͸�����ģʽ
	 *    %            ǰ׺���׺                ���ֳ���100����ʾΪ�ٷ���
	 *    ��            ǰ׺���׺                ���ֳ���1000����ʾΪ�ٷ���
	 *    
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		format1("###,###.###", 111222.34567);//111,222.346
		// ǰ�߲�����0 ��߶����������� ������0
		format1("000,000.000", 11222.34567);//011,222.346
		format1("###,###.###��", 111222.34567);//111,222.346��
		format1("000,000.000��", 11222.34567);//011,222.346��
		// �ٷ�֮����
		format1("##.###%", 0.345678);//34.568%
		// �ٷ�֮����
		format1("00.###%", 0.0345678);//03.457%
		// ǧ��֮����
		format1("###.###��", 0.345678);//345.678��
		// ָ����ʽ
		DecimalFormat df1 = new DecimalFormat();
		// �����������applypattern
		df1.applyPattern("0.000E0000"); 
		System.out.println(df1.format(111222.34567));//1.112E0005
	}
	
	static void format1(String pattern, double value) {
		// ����һ��DecimalFormat����
		DecimalFormat df = new DecimalFormat(pattern);
		String str = df.format(value);
		System.out.println("ʹ��" + pattern + "��ʽ��ʽ����" + value + " �õ���" + str);
	}
}
