package read

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import read.bean.BookDetailBean

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var s =
            "{    \"_gg\": false,    \"_id\": \"53663ae356bdc93e49004474\",    \"_le\": false,    \"advertRead\": true,    \"allowBeanVoucher\": false,    \"allowFree\": false,    \"allowMonthly\": false,    \"allowVoucher\": true,    \"anchors\": [],    \"author\": \"白马出淤泥\",    \"authorDesc\": \"\",    \"banned\": 3,    \"buytype\": 0,    \"cat\": \"经典武侠\",    \"chaptersCount\": 3077,    \"contentLevel\": -1,    \"contentType\": \"txt\",    \"copyright\": \"\",    \"copyrightDesc\": \"本书由上海元聚进行电子本制作与发行\",    \"copyrightInfo\": \"本书数字版权由“”提供，并由其授权上海元聚网络科技有限公司制作发行。若书中含有不良信息，请积极告知客服。\",    \"cover\": \"/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F42118%2F42118_18f417bba52d4090b23e515513485bec.jpg%2F\",    \"creater\": \"sprd DESAY TS808\",    \"currency\": 0,    \"donate\": false,    \"followerCount\": 0,    \"gender\": [        \"male\"    ],    \"hasCopyright\": true,    \"hasCp\": true,    \"isAllowNetSearch\": true,    \"isFineBook\": false,    \"isForbidForFreeApp\": true,    \"isMakeMoneyLimit\": false,    \"isSerial\": true,    \"lastChapter\": \"VIP卷 第3047章 我的拳头\",    \"latelyFollower\": 0,    \"latelyFollowerBase\": 0,    \"limit\": false,    \"longIntro\": \"金庸武侠中有不少的神秘高手，书中或提起名字，或不曾提起，总之他们要么留下了绝世秘笈，要么就名震武林。独孤九剑的创始者，独孤求败，他真的只创出九剑吗？残本葵花宝典造就了东方不败，那么葵花宝典的创始者，无名太监是何等功力？侠客岛两位岛主功力深不可测，却不是石破天一朝领悟之敌，那么太玄经创始者的功力又如何？十三层密宗龙象般若功据说后三层非人力可练，那么功法创始者是否只练到第十三层？逍遥三老功力莫测，逍遥派绝学神秘无比，那么开派祖师逍遥老祖的功力如何？这些人的实力难以想象，基本可归于‘仙’一类了。如果这些人都在同一时代，那将是什么样的一个江湖。主角，逍遥派老祖。当然，现在他只是一个十六岁，手无缚鸡之力的书生。\",    \"majorCate\": \"武侠\",    \"majorCateV2\": \"武侠\",    \"minorCate\": \"经典武侠\",    \"minorCateV2\": \"经典武侠\",    \"originalAuthor\": \"\",    \"postCount\": 1613,    \"retentionRatio\": 0,    \"safelevel\": 70,    \"serializeWordCount\": 0,    \"sizetype\": -1,    \"starRatingCount\": 0,    \"starRatings\": [        {            \"count\": 0,            \"star\": 0        },        {            \"count\": 0,            \"star\": 1        },        {            \"count\": 0,            \"star\": 2        },        {            \"count\": 0,            \"star\": 3        },        {            \"count\": 0,            \"star\": 4        },        {            \"count\": 0,            \"star\": 5        }    ],    \"superscript\": \"\",    \"tags\": [        \"孤儿\",        \"热血\",        \"练功流\"    ],    \"title\": \"逍遥派\",    \"totalFollower\": 0,    \"totalRetentionRatio\": 0,    \"updated\": \"2019-05-16T15:59:50.626Z\",    \"wordCount\": 8079032}";
        val  bookDetailBean = Gson().fromJson(s, BookDetailBean::class.java)
        ReadActivity.startActivity(this, bookDetailBean.collBookBean, false)
    }
}
