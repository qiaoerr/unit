package test;

public class Fan_xing {

	public static void main(String args[]) {
		Info<Integer> i = new Info<Integer>(); // ʹ��StringΪ��������
		i.setVar(1); // ��������
		fun(i);
	}

	public static void fun(Info<?> temp) { // ���Խ�������ķ��Ͷ���
		System.out.println("���ݣ�" + temp);
	}

}

class Info<T> {
	private T var; // ���巺�ͱ���

	public void setVar(T var) {
		this.var = var;
	}

	public T getVar() {
		return this.var;
	}

	public String toString() { // ֱ�Ӵ�ӡ
		return this.var.toString();
	}
};