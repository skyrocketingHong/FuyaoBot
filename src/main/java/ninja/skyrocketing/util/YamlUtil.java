package ninja.skyrocketing.util;

import com.esotericsoftware.yamlbeans.YamlReader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class YamlUtil {
	public Map<String, Object> getYaml() throws IOException {
		InputStream is = new FileInputStream("config.yml");
		String file = FileUtil.readAsString(is);
		YamlReader reader = new YamlReader(file);
		Object object = reader.read();
		return (Map<String, Object>) object;
	}
}
