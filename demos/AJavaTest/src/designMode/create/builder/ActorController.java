package designMode.create.builder;

/**
 * @author liu
 * @description 游戏角色创建控制器：指挥者
 */
public class ActorController {
	// 逐步构建复杂产品对象
	public Actor construct(ActorBuilder ab) {
		ab.buildType();
		ab.buildSex();
		ab.buildFace();
		ab.buildCostume();
		ab.buildHairstyle();
		return ab.createActor();
	}
}