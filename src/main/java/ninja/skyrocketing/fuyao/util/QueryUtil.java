package ninja.skyrocketing.fuyao.util;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;

import java.util.List;

/**
 * @author skyrocketing Hong
 * @date 2022-05-14 20:12
 */

public class QueryUtil {
	public static List<String> nbnhhshQuery(String str) {
		//拼接查询参数
		String param = "{\"text\" : \"" + str + "\"}";
		//存储返回的json字符串
		String nbnhhsh = cn.hutool.http.HttpUtil.post("https://lab.magiconch.com/api/nbnhhsh/guess", param);
		//如果字符串是json数组，则返回消息
		if (JSONUtil.isTypeJSONArray(nbnhhsh)) {
			//将字符串转换为json数组
			JSONArray nbnhhshArray = JSONUtil.parseArray(nbnhhsh);
			//获取可能的结果，去掉中括号并分割成String数组
			String[] trans = nbnhhshArray.getByPath("trans", String.class)
					.replaceAll("\\[|]", "")
					.split(",");
			if (trans[0].equals("null")) {
				return List.of();
			}
			return List.of(trans);
		}
		return null;
	}
}
