package designMode.create.builder;

/**
 * @author liu
 * @description Ӣ�۽�ɫ�����������彨����
 */
public class HeroBuilder extends ActorBuilder {

	public void buildType() {
		actor.setType("Ӣ��");
	}

	public void buildSex() {
		actor.setSex("��");
	}

	public void buildFace() {
		actor.setFace("Ӣ��");
	}

	public void buildCostume() {
		actor.setCostume("����");
	}

	public void buildHairstyle() {
		actor.setHairstyle("Ʈ��");
	}
}