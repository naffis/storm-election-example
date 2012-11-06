package storm.starter;

import storm.starter.bolt.LinkFilterBolt;
import storm.starter.bolt.RedisGooseExtractor;
import storm.starter.bolt.RedisLinksPublisherBolt;
import storm.starter.bolt.RedisMarketBolt;
import storm.starter.bolt.RedisRetweetBolt;
import storm.starter.bolt.RedisTweetPublisherBolt;
import storm.starter.bolt.RedisTagsPublisherBolt;
import storm.starter.bolt.TwitterFilterBolt;
import storm.starter.spout.TwitterSearchSpout;
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.topology.TopologyBuilder;

public class TwitterTopology {

	public static void main(String[] args) throws Exception {
		TopologyBuilder builder = new TopologyBuilder();
				
		TwitterSearchSpout twitterSearch = new TwitterSearchSpout("username", "password", 
			new String[]{"romney", "mittromney", "santorum", "ricksantorum", "gingrich", "newtgingrich", "ron paul", "ronpaul"}, 
			new double[][]{{-122.75,36.8},{-121.75,37.8}}
		);
		
		builder.setSpout("twitterSearch", twitterSearch);
			
		builder.setBolt("filter", new TwitterFilterBolt()).shuffleGrouping("twitterSearch");
				
		builder.setBolt("tweetsRomney", new RedisTweetPublisherBolt("tweets-romney")).shuffleGrouping("filter", "romney");
		builder.setBolt("tweetsSantorum", new RedisTweetPublisherBolt("tweets-santorum")).shuffleGrouping("filter", "santorum");
		builder.setBolt("tweetsGingrich", new RedisTweetPublisherBolt("tweets-gingrich")).shuffleGrouping("filter", "gingrich");
		builder.setBolt("tweetsPaul", new RedisTweetPublisherBolt("tweets-paul")).shuffleGrouping("filter", "paul");						

		builder.setBolt("tagsRomney", new RedisTagsPublisherBolt("tags-romney")).shuffleGrouping("filter", "romney");
		builder.setBolt("tagsSantorum", new RedisTagsPublisherBolt("tags-santorum")).shuffleGrouping("filter", "santorum");
		builder.setBolt("tagsGingrich", new RedisTagsPublisherBolt("tags-gingrich")).shuffleGrouping("filter", "gingrich");
		builder.setBolt("tagsPaul", new RedisTagsPublisherBolt("tags-paul")).shuffleGrouping("filter", "paul");						


		Config conf = new Config();
    conf.setDebug(false);
    
    if(args!=null && args.length > 0) {
	    conf.setNumWorkers(3);    
	    StormSubmitter.submitTopology(args[0], conf, builder.createTopology());
    } else {
     	LocalCluster cluster = new LocalCluster();
    	cluster.submitTopology("twitter", conf, builder.createTopology());
    }
	}

}
