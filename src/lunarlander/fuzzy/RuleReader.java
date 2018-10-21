package lunarlander.fuzzy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class RuleReader {
	
	public List<String> read() {
		InputStream is;
		try {
			is = this.getClass().getResource("default_rules.txt").openStream();
			return read(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}
	
	public List<String> read(InputStream is){
		List<String> rules = new ArrayList<>();
		String line = null;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			while((line = reader.readLine()) != null) {
				if (line.trim().startsWith("//") || line.trim().isEmpty()) continue;
				rules.add(line.trim());
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rules;
	}
}
