package designMode.create.builder;

public class MainClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		ActorBuilder ab = new AngelBuilder(); // ��Գ������߱��
		ActorController ac = new ActorController();
		Actor actor;
		actor = ac.construct(ab); // ͨ��ָ���ߴ��������Ľ����߶���

		String type = actor.getType();
		System.out.println(type + "����ۣ�");
		System.out.println("�Ա�" + actor.getSex());
		System.out.println("���ݣ�" + actor.getFace());
		System.out.println("��װ��" + actor.getCostume());
		System.out.println("���ͣ�" + actor.getHairstyle());

	}

}
