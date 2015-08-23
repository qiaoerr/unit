package designMode.create.builder;

/**
 * @author liu
 * @description ��Ϸ��ɫ������������ָ����
 */
public class ActorController {
	// �𲽹������Ӳ�Ʒ����
	public Actor construct(ActorBuilder ab) {
		ab.buildType();
		ab.buildSex();
		ab.buildFace();
		ab.buildCostume();
		ab.buildHairstyle();
		return ab.createActor();
	}
}