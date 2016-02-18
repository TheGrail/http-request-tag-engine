import java.util.HashMap;
import java.util.List;


public interface IPlugin {
	public void tagging(String line, String seperator, List<String> fields);
}
