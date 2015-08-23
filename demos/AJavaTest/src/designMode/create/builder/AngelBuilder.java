package designMode.create.builder;

/**
 * @author liu
 * @description ��ʹ��ɫ�����������彨����
 */
public class AngelBuilder extends ActorBuilder {

	public void buildType() {
		actor.setType("��ʹ");
	}

	public void buildSex() {
		actor.setSex("Ů");
	}

	public void buildFace() {
		actor.setFace("Ư��");
	}

	public void buildCostume() {
		actor.setCostume("��ȹ");
	}

	public void buildHairstyle() {
		actor.setHairstyle("���糤��");
	}
}
