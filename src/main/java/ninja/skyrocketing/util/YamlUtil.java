package ninja.skyrocketing.util;

import com.esotericsoftware.yamlbeans.YamlReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

public class YamlUtil {
	public Map<String, Object> getYaml() throws IOException {
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("config.yml");
		BufferedReader br=new BufferedReader(new InputStreamReader(is));
		String file = FileUtil.readAsString(is);
		YamlReader reader = new YamlReader(file);
		Object object = reader.read();
		return (Map<String, Object>) object;
	}
}
