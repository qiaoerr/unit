package com.star.general.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.star.baseFramework.model.BannerModel;
import com.star.baseFramework.util.BaseCommonUtil;
import com.star.baseFramework.widget.BannerView;
import com.star.general.R;
import com.star.general.adapter.NewsItemAdapter;
import com.star.general.model.News;
import com.star.general.util.ResourceUtil;
import com.star.general.widget.ButtomBar;
import com.star.general.widget.ButtomBar.OnclickListener;

public class NewsActivity extends Activity implements OnItemClickListener {
	private Context context;
	private RelativeLayout container;
	private float ratio = 142f / 300;
	private int screenWidth;
	private LayoutParams params;
	private ArrayList<BannerModel> models;
	private ButtomBar buttomBar;
	private ListView listView;
	private NewsItemAdapter adapter;
	private ArrayList<News> news;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news_layout);
		context = this;
		initData();
		initView();
	}

	private void initData() {
		models = new ArrayList<BannerModel>();
		news = new ArrayList<News>();
		ArrayList<Integer> top_imgs = ResourceUtil.getPrefixImages(context,
				"top_");
		for (int i = 0; i < top_imgs.size(); i++) {
			BannerModel model = new BannerModel(BannerModel.TYPE_LOCAL,
					top_imgs.get(i));
			models.add(model);
		}
		screenWidth = BaseCommonUtil.getScreenWidth(context);
		// 添加本地数据
		News news1 = new News();
		news1.setTitle("Joico嘉珂细节发型席卷暖冬");
		news1.setDetail("        对于暖冬的发型，Joico嘉珂国际艺术总监 Damien Carney 达米安•卡尼从最小的细节萌生了时尚转变的灵感，利用角度和弧形的设计来展现女性的奢华魅力。卡尼表示，这种流行的趋势深远而宽泛，即使是CK的崇尚简约的设计师 Francisco Costa 弗朗西斯科• 科斯塔，Max Azria 和 Dolce & Gabbana 等服装品牌，都利用紧身衣、短裙、圆领以及色块的金属细节来突出女性的性感曲线之美。卡尼将这种趋势带到了美发界，由头顶中心旋转开去剪出一个合适的圆形，利用发梢、侧发、后发以及刘海去修饰出丰富的细节，做出三维立体感的雕塑发型。卡尼说，“这样不会显得夸张，只是增加了一点不失摩登实用的戏剧化设计。”\n对于冬季来说，亮丽而永恒时尚的金铜色会带来更多的温暖与热情。无论是刘海与发梢的挑染还是闪耀的全染，都预示着成熟的潮流气息。搭配浓重的哥特式烟熏眼妆，以及带着金属亮片的嗲味服装，即显奢华与尊荣。\nJOICO嘉珂是结合高端科技、创新产品开发、专业培训以及承诺产品质量于一身的专业美发品牌，致力于打造健康而个性的魅力发型，领跑美发业界。");
		news.add(news1);
		News news2 = new News();
		news2.setTitle("2014第二十四届中国北京国际美容化妆品博览会(春季)");
		news2.setDetail("展览时间：2014年4月8日-10日  （6-7日为布展时间）\n展览地点：中国国际展览中心（老馆/北京朝阳区北三环东路6号）\n主办单位：北京世博联展览服务有限公司(SBL) 北京市美发美容行业协会(BHBA) \n协办单位：北京美博汇(BEH)\n展会管理机构：北京世博联展览服务有限公司\n地址：北京市朝阳区三元桥曙光西里甲1号第三置业B座1006室\n联系人：江涛先生 13552089378\n电话：13552089378 010-58220408\n传真：+86-10-58850839\nE-mail：taopg@163.com     \nQQ：542315105\n全国三大美展之一      华北美业品牌展\n展会介绍：\n中国北京国际美容化妆品博览(北京美博会)会由北京世博联展览服务有限公司、北京市美发美容行业协会共同主办，创办于1997年，2008年以来每年举办两届，至2013年10月已成功举办23届。中国北京国际美容化妆品博览会（简称北京美博会）已成为华北地区最大、影响力最强的美容行业专业盛会，也成为国内外企业开拓海外市场的一个对接平台，被誉为“华北美业品牌展”。为国内外美容化妆品企业开拓国内和国际市场，为买方和卖方市场沟通和增进交流起到了积极的推动作用。北京美博会作为行业专业美容化妆品展会，不仅见证了中国美容行业的崛起与繁荣，更为行业企业成功搭建起了最为直接商贸洽谈的平台，成为行业企业每年首选的品牌展会。经过十余年发展，BJBE展会规模和影响力稳居国内行业前列，是继上海、广州美容展之后中国第三大专业美容行业盛会，在业界享有极高声誉。我们期待「2013第二十四届中国北京国际美容化妆品博览会」与您携手共创商机。");
		news.add(news2);
		News news3 = new News();
		news3.setTitle("发廊员工自我管理-意识很重要");
		news3.setDetail("        1， 成本意识：怎样做才能更省钱？\n成本意识就是在做任何一件事情之前，都应衡量要付出多少代价，是不是有更节省，更好的方法，你不妨从下面的小事做起\n☆为顾客冲洗头时，冲头床水龙头里热水前的冷水用来冲洗发尾部份。\n☆接近晚上打烊时，关掉部分区域的电灯。\n☆店里的工具，设备轻拿轻放。\n☆下班时将电灯、空调等开关关掉。\n☆没有超过一定温度不要使用空调。\n☆使用过的纸张，非重要文件的背面还可以使用。\n☆休息室的手机、对讲机充电完成后拨出插座，杜绝浪费电力资源。\n☆随手将水龙头关掉、关紧。\n2， 效率意识：怎样做才能更节约时间？\n如何在有限的工作时间内，为店、公司创造最大的价值，提高工作效率，这是每一个老板所关心的，也是每一个员工必须做到的。成为一个高效能的美发人、应该注意：\n☆明确工作目标，并按目标分解到每年、每月、每日。\n☆制定可行的工作计划。按照自我管理的PDCA循环法。\n☆抓住每天的工作重点。\n☆立即行动。\n3， 结果意识：你完成目标了吗？\n老板凭什么给你发工资？这个“什么”就是你应该给老板看的结果，或者说，你应给老板一个令他信服的给你发工资的理由。达到结果，有以下指标可供对照\n☆以达成目标为原则，不因为困难而放弃努力；\n☆以获得最终结果为标准，没有任何借口；\n☆在目标面前没有“苦劳”与“辛苦”之说，只有完成或者没有完成；\n☆在结果面前，没有感情和面子可言，只有成功或失败；\n☆在过程面前，你可以有一千个理由，一万个原因，十万个无能为力、百万个尽心尽力，可是在结果面前，却只有一个简单的要求，事情做完了吗？业绩完成了吗？技术满意达到100%了吗？\n☆在结果面前，放弃就意味着投降和失败，店里的事情没有搞定，你也就没有业绩可言，你可以心安地下班吗？你靠什么吃饭？\n☆不要用假设影响结果，假设往往是懒惰和不负责任的借口。\n4， 品质意识：你做到最好了么？\n“最好”当然没有一个绝对的标准，只是说我们做任何事情都要尽最大的努力。发廊做的是“发型作品”，需求的是作品质量、服务质量；发廊更需要品质意识和品质文化。“品质，是价值与尊严的起点。”要做到最好，必须：\n☆具有责任意识；\n☆时刻关注细节；\n☆第一次就做对；\n5， 品牌意识：你关心发廊的形象吗？\n品牌有“三度”：知名度、美誉度、忠诚度。现在的美发业的竞争不再是人对人的竞争，也不是店与店的竞争，而是品牌的竞争。打响和提高品牌的“三度”，最主要的还是要靠具有老板心态的员工。\n☆你就是发廊的代表和形象；\n☆时时处处宣传自己的品牌；\n☆永远不要指责发廊的不是。");
		news.add(news3);
		News news4 = new News();
		news4.setTitle("怎么留住人才？--发廊留才策略");
		news4.setDetail("       “谁不想快速扩张？谁不想持续创造高业绩？谁不想超越竞争对手？谁不想留下自己的员工？谁又不想轻轻松松地赚钱？……这些问题我们想得都快疯了。但是，前门进不来人，后门又留不住人，没有人，一切都是扯淡。”相信以上的问题，都是现阶段每一名老板心中“永远的痛”。其实，没有解决不了的问题，也没有搞不定的人，关键在于我们用什么方法，用什么钥匙开什么锁。当你还在为员工的不断离职感到烦恼的时候，不妨尝试以下20种方法中的一种或几种。\n第一招：招人不如留人\n中国有句古话叫做“外来的和尚会念经”。同样是和尚，为什么外来的会念经呢？这反映出许多管理者舍近求远的思想：总觉得没有得到的才是最好的，已经得到的却不知道珍惜。同样的情况发生在现在的许多发廊：发廊前门大量招聘，后门人才大量溜走。\n我们要明白替换一个员工的成本有哪些？它包括招聘的费用，还包括因为员工离开发廊而失去的顾客，新员工在学习阶段的培训成本、低效率和适应时间的成本，以及他们融入你的团队的适应成本——据调查替换员工的成本可以高达辞职者工资的150%。因此，我们的第一招就是在你挖空心思招聘人才的时候，首先想的和做的就是如何留住人才。\n第二招：招聘合适的员工\n留住人才的前提是招聘到合适的员工。很多发廊的经验表明，你当初招聘什么样的人对你能否留住他有着重大的关系。匆匆忙忙地抓人来，而他几个月不到就离职了是不可取的。最好是花一些时间去确定一个应聘者是否可能留下来或可能留多长。我们过去在招聘人才的时候，并不强调或不去了解他们要长时间地呆在我们发廊的意愿如何，或者能否适应企业文化，结果导致整个团队的紧张和不和谐。因此，对新进员工进行科学的测评来确定合适的人才应该是我们开始考虑的问题了。我们都不希望雇佣那些老想跳槽的人，即使他是很有才华的人才。\n第三招：让每一个人都有事可干\n在某种程度上，一个发廊就象一支足球队，员工就象足球队员。高薪可以为球队聘到大腕的球星，但是，如果这位球星一年都没有上场或者上场次数很少而不能为球队提高球队的成绩，他肯定会离开这支球队。发廊也是这样。有的发廊炫耀自己有多少多少高级设计师，国家技师，但这些设计师、技师们却在发廊无法创造应有的价值。过不了多久，他们也都会走的，而且，发廊留人的目的本身就是要发挥他们的作用。为了让每一个员工都有事可干，发廊必须将自己的总体目标细化，使每一个员工都有自己的明确的工作目标，并以此作为对员工进行考核的标准。目标的制定要遵循SMART原则：一是具体的，二是可测量和考量的，三是可实现的，四是与工作相关联的，五是有一定的时间的。只有每一个员工都有了自己明确的奋斗目标，他才会感到自己在发廊“是有用的人”，“是有奔头的”，才愿意在发廊长期地干下去。");
		news.add(news4);
		News news5 = new News();
		news5.setTitle("发廊员工激励也是一门学问");
		news5.setDetail("        《太公兵法》云：“夫用兵之要，在崇礼而重禄。礼崇则智士至，禄重则义士轻死……故，礼者士之所归，赏者士之所死。礼赏不倦，则士争死。”激励机制是自古有之，而现在我们提倡的激励机制实际上就是营造一种积极的团队文化。也就是说发廊管理者如何针对不同员工、不同情况给与其不同的激励，以使其精神振作，发挥最大潜能为发廊效力。一个以人为本的发廊企业文化中，激励方式便会无处不在、层出不错，这些激励方式往往会使员工表现出高昂的斗志和良好的团队作战状态。激励的方式很多，这主要看管理者如何来运用激励艺术。很多发廊误以为激励只能以物质奖励的形式，然而物质激励只能把员工留下来，若它作为单一形式表现就不能真正起到激发员工斗志的作用。好的激励方式是多样化、集中化但又因人而异的。\n有老板曾问我这样四个问题：\n1.照顾好员工的家人方面，管理者应怎样做好激励？\n2.在举办娱乐活动方面，管理者应怎样做好激励？\n3.在培训方面，管理者应怎样做好激励？\n4.在帮员工做职业规划方面发廊应如何做好员工激励?\n从以上四个问题来看，或许有人会认为只要给出一两种解决方法就可以了，但一个发廊的激励制度不是用一两个行动就能建立的，好的激励机制能形成发廊良好的企业文化，这种企业文化是发廊管理层与员工在平时工作和学习的点滴中形成的。\n激励最有效的途径就是满足员工的需求，从而使其心甘情愿为发廊做事。人的需求包括生理需求、安全需求、社会需求、新生需求和自我实现需求等若干层次。当一种需求得到满足之后，员工就会转向其它需求。对员工家人的照顾、举办娱乐活动、培训及帮助员工做职业规划都是发廊的一种激励制度，它更多的是从员工的被理解、被尊重、社交及对生活的满足等新生需求出发。管理者在这些方面也要根据实际情况、员工的不同性格做出相应对策，给予适当的正确的激励。\n在照顾好员工家人方面：对家里特别困难的员工，可以给予经济补助；对成绩特别突出的员工，可以帮助家里其他人员的工作等问题；另外我们也可以在每年的探亲、员工家属探访等方面做好激励工作，让员工体会到发廊的人性化。发廊管理者也可以采取不同方式来关心员工，如可以适时问候员工家人，在一些会餐时邀请员工家属一同出席等。\n在举办娱乐活动方面：管理者应给与极大的支持，应多说夸奖和鼓励的话，让更多员工融入到集体中，加强沟通。也可买些小礼品，用礼品去犒劳员工，使他们感受企业文化。一件小礼品虽然价钱并不高，但它却能让员工从中体会到发廊的人文与关爱，企业文化是激发员工热情的良药。\n培训是帮助员工学习成长的极好手段。发廊可以通过培训来帮助员工增长业务、管理、交际等方面的知识，同时也能通过培训来稳定人心，激发员工的潜能。如我们可以通过培训来让员工摆正浮躁心态，鼓励他们的献身精神及竞争意识，这些都可以刺激他们努力工作。\n帮助员工做职业规划这是一个负责的发廊应该做到的。员工进入发廊就应该为员工安排一个与其性格相匹配的职务。与员工个人相匹配的工作才能让员工感到满意、舒适。比如说，喜欢稳定、程序化工作的传统型员工适宜干收银等工作，而充满自信、进取心强的员工则适宜让他们担任技师、店长助手等职务。但发廊还应为每个员工设定具体而恰当的目标。有了目标，便会激励员工更加努力地去完成，但目标不能不切实际，也不能太简单，应该是富有挑战性但又是他们通过努力能完成的。发廊还应该对完成这些阶段性目标的员工进行一定奖励，再设定新的目标。通过适当奖励使他们的意识正强化，能不断向前发展，从而一步步迈向更高的目标。\n除以上四方面外，发廊对员工的激励政策还是很多的。但管理者必须记住奖励机制一定要公平，不然再诱人的奖励都会引起员工的不满。同时管理者还要学会针对不同员工进行不同奖励。因为人的需求不同，有些人希望得到更高的工资，但有些人或许更在乎工资以外的激励。但不管以什么方式，管理者都应该记住这句话：“小功不赏，则大功不立；小怨不赦，则大怨必生。”激励是一门学问，管理者要在最佳时机，做出公平、准确的激励就对了。");
		news.add(news5);

	}

	private void initView() {
		container = (RelativeLayout) findViewById(R.id.container);
		BannerView bannerView = new BannerView(context, models, screenWidth
				- screenWidth / 14, (int) (screenWidth * ratio) - screenWidth
				/ 14);
		bannerView.hideIndex();
		listView = new ListView(context);
		listView.setOnItemClickListener(this);
		listView.setVerticalScrollBarEnabled(false);
		listView.setScrollingCacheEnabled(false);
		listView.setDivider(context.getResources().getDrawable(
				R.color.divider_color));
		listView.setDividerHeight(2);
		listView.addHeaderView(bannerView);
		params = new LayoutParams(-1, -1);
		params.setMargins(screenWidth / 14, 0, screenWidth / 14, 0);
		params.addRule(RelativeLayout.ABOVE, 10001);
		listView.setLayoutParams(params);
		adapter = new NewsItemAdapter(news, context);
		listView.setAdapter(adapter);
		container.addView(listView);
		// buttomBar
		buttomBar = new ButtomBar(context);
		buttomBar.setConfig(R.drawable.bottom_return, R.drawable.bottom_share,
				R.drawable.bottom_setting, new OnclickListener() {

					@Override
					public void rightClick() {
						Intent intent = new Intent(context,
								SettingActivity.class);
						startActivity(intent);
					}

					@Override
					public void midClick() {
						share();
					}

					@Override
					public void leftClick() {
						finish();
					}
				});
		buttomBar.setId(10001);
		params = new LayoutParams(-2, -2);
		params.setMargins(0, 0, 0, 0);
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		buttomBar.setLayoutParams(params);
		container.addView(buttomBar);
	}

	// 分享
	private void share() {
		String shareContent = "";
		if (shareContent == null || shareContent.equals("")) {
			shareContent = "嗨，我正在使用星火客户端，赶快来试试吧！！";
		}

		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("image/*");
		intent.putExtra(Intent.EXTRA_SUBJECT, "好友推荐");
		intent.putExtra(Intent.EXTRA_TEXT, shareContent); // 分享的内容
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(Intent.createChooser(intent, "软件分享"));
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		position = position - listView.getHeaderViewsCount();
		News temp = news.get(position);
		Intent intent = new Intent(context, NewsDetailActivity.class);
		intent.putExtra("news", temp);
		startActivity(intent);
	}

}
