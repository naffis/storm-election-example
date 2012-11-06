package storm.starter.bolt;

import java.util.Map;

import twitter4j.HashtagEntity;
import twitter4j.Status;
import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class TwitterFilterBolt implements IRichBolt {

	OutputCollector collector;

	@Override
	public void execute(Tuple tuple) {
		Status status = (Status)tuple.getValue(0);		
		String text = status.getText();

		System.out.println("Fields in tuple are:");
		System.out.println(tuple.getFields().toList());
		System.out.println(tuple.getValues());
		
		
		if(text.toLowerCase().indexOf("romney") > 0) {
			collector.emit("romney", tuple, new Values(status));
			collector.ack(tuple);
		}
		if(text.toLowerCase().indexOf("santorum") > 0) {
			collector.emit("santorum", tuple, new Values(status));
			collector.ack(tuple);
		}
		if(text.toLowerCase().indexOf("gingrich") > 0) {
			collector.emit("gingrich", tuple, new Values(status));
			collector.ack(tuple);
		}
		if(text.toLowerCase().indexOf("paul") > 0) {
			collector.emit("paul", tuple, new Values(status));
			collector.ack(tuple);
		}						
		
	}

	@Override
	public void cleanup() {

	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declareStream("romney", new Fields("FJJ")); 
		declarer.declareStream("santorum", new Fields("FJJ")); 
		declarer.declareStream("gingrich", new Fields("FJJ")); 
		declarer.declareStream("paul", new Fields("FJJ"));		
	}


	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this.collector = collector;
		System.out.println("Context is:");
		System.out.println(context.toJSONString());
	}

}
