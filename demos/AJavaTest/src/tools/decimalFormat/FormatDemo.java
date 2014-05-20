package tools.decimalFormat;

import java.text.DecimalFormat;

public class FormatDemo {

	/**
	 * @param args
	 * 
	 *  标   记                        位    置                                                    描  述
	 *    0                              数字                   代表阿拉伯数字，每一个0表示一位阿拉伯数字，如果该位不存在则显示0
	 *    #               数字                   代表阿拉伯数字，每一个#表示一位阿拉伯数字，如果该位不存在则不显示
	 *    .               数字                   小数点分隔符或货币的小数分隔符
	 *    -               数字                   小数点分隔符或货币的小数分隔符
	 *    ,               数字                   分组分隔符
	 *    E               数字                   分隔科学计数法中的尾数和指数
	 *    ;            子模式边界                分隔正数和负数子模式
	 *    %            前缀或后缀                数字乘以100并显示为百分数
	 *    ‰            前缀或后缀                数字乘以1000并显示为百分数
	 *    
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		format1("###,###.###", 111222.34567);//111,222.346
		// 前边不够补0 后边多余四舍五入 不够补0
		format1("000,000.000", 11222.34567);//011,222.346
		format1("###,###.###￥", 111222.34567);//111,222.346￥
		format1("000,000.000￥", 11222.34567);//011,222.346￥
		// 百分之多少
		format1("##.###%", 0.345678);//34.568%
		// 百分之多少
		format1("00.###%", 0.0345678);//03.457%
		// 千分之多少
		format1("###.###‰", 0.345678);//345.678‰
		// 指数形式
		DecimalFormat df1 = new DecimalFormat();
		// 用下这个东西applypattern
		df1.applyPattern("0.000E0000"); 
		System.out.println(df1.format(111222.34567));//1.112E0005
	}
	
	static void format1(String pattern, double value) {
		// 声明一个DecimalFormat对象
		DecimalFormat df = new DecimalFormat(pattern);
		String str = df.format(value);
		System.out.println("使用" + pattern + "样式格式化：" + value + " 得到：" + str);
	}
}
