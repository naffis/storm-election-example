package storm.starter.bolt;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;

import twitter4j.Status;


public class RedisTweetPublisherBolt extends RedisBolt {
		
	public RedisTweetPublisherBolt(String channel) {
		super(channel);
	}

	@Override
	public List<Object> filter(Status status) {
		
		JSONObject msg = new JSONObject();
		msg.put("user", status.getUser().getScreenName());
		msg.put("photo", status.getUser().getProfileImageURL().toString());
		msg.put("tweet", status.getText());
		msg.put("id", status.getId());
		msg.put("count", status.getRetweetCount() > 100 ? "> 100" : status.getRetweetCount());
		
		publish(msg.toJSONString());		
		
		List<Object> result = new ArrayList<Object>();
		result.add(status);
		return result;
	}

}
