package com.alonzo.transformer.mr.inbound.bounce;

import java.io.IOException;

import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.log4j.Logger;

import com.alonzo.common.EventLogConstants;
import com.alonzo.common.EventLogConstants.EventEnum;
import com.alonzo.transformer.model.dim.StatsInboundBounceDimension;
import com.alonzo.transformer.model.dim.StatsInboundDimension;
import com.alonzo.transformer.model.value.reduce.InboundBounceReduceValue;
import com.alonzo.transformer.mr.TransformerBaseRunner;
import com.alonzo.transformer.mr.inbound.InboundMapper;
import com.alonzo.transformer.mr.inbound.bounce.InboundBounceSecondSort.InboundBounceGroupingComparator;
import com.alonzo.transformer.mr.inbound.bounce.InboundBounceSecondSort.InboundBouncePartitioner;

/**
 * 计算inbound 跳出率的入口类
 * 
 * @author alonzo
 *
 */
public class InboundBounceRunner extends TransformerBaseRunner {
	private static final Logger logger = Logger.getLogger(InboundBounceRunner.class);

	public static void main(String[] args) {
		InboundBounceRunner runner = new InboundBounceRunner();
		runner.setupRunner("inbound_bounce", InboundBounceRunner.class, InboundBounceMapper.class, InboundBounceReducer.class, StatsInboundBounceDimension.class, IntWritable.class,
				StatsInboundDimension.class, InboundBounceReduceValue.class);
		try {
			runner.startRunner(args);
		} catch (Exception e) {
			logger.error("执行异常", e);
            throw new RuntimeException("执行异常", e);
		}
	}
	
	@Override
	protected void beforeRunJob(Job job) throws IOException {
		super.beforeRunJob(job);
		job.setGroupingComparatorClass(InboundBounceGroupingComparator.class);
		job.setPartitionerClass(InboundBouncePartitioner.class);
	}
	
	 @Override
	    protected Filter fetchHbaseFilter() {
	        FilterList list = new FilterList();
	        String[] columns = new String[] { EventLogConstants.LOG_COLUMN_NAME_REFERRER_URL, // 前一个页面的url
	                EventLogConstants.LOG_COLUMN_NAME_SESSION_ID, // 会话id
	                EventLogConstants.LOG_COLUMN_NAME_PLATFORM, // 平台名称
	                EventLogConstants.LOG_COLUMN_NAME_SERVER_TIME, // 服务器时间
	                EventLogConstants.LOG_COLUMN_NAME_EVENT_NAME // 事件名称
	        };
	        list.addFilter(this.getColumnFilter(columns));
	        list.addFilter(new SingleColumnValueFilter(InboundMapper.family, Bytes.toBytes(EventLogConstants.LOG_COLUMN_NAME_EVENT_NAME), CompareOp.EQUAL, Bytes.toBytes(EventEnum.PAGEVIEW.alias)));
	        return list;
	    }
}
