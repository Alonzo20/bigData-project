package com.alonzo.transformer.mr.sessions;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.mapreduce.Reducer;

import com.alonzo.common.GlobalConstants;
import com.alonzo.common.KpiType;
import com.alonzo.transformer.model.dim.StatsUserDimension;
import com.alonzo.transformer.model.value.map.TimeOutputValue;
import com.alonzo.transformer.model.value.reduce.MapWritableValue;
import com.alonzo.transformer.util.TimeChain;

/**
 * 计算会话个数和会话时长的一个reducer类
 * 
 * @author alonzo
 *
 */
public class SessionsReducer extends Reducer<StatsUserDimension, TimeOutputValue, StatsUserDimension, MapWritableValue> {
	private Map<String, TimeChain> timeChainMap = new HashMap<String, TimeChain>();
	private MapWritableValue outputValue = new MapWritableValue();
	private MapWritable map = new MapWritable();

	@Override
	protected void reduce(StatsUserDimension key, Iterable<TimeOutputValue> values, Context context) throws IOException, InterruptedException {
		for (TimeOutputValue value : values) {
			TimeChain chain = this.timeChainMap.get(value.getId());
			if (chain == null) {
				chain = new TimeChain(value.getTime());
				this.timeChainMap.put(value.getId(), chain);// 保存
			}
			chain.addTime(value.getTime()); // 更新时间
		}

		// 计算总的间隔秒数
		int sessionsLength = 0;
		// 1. 计算间隔毫秒数
		for (Map.Entry<String, TimeChain> entry : this.timeChainMap.entrySet()) {
			long tmp = entry.getValue().getTimeOfMillis();// 间隔毫秒数
			if (tmp < 0 || tmp > GlobalConstants.DAY_OF_MILLISECONDS) {
				// 如果计算的值是小于0或者是大于一天的毫秒数，直接过滤
				continue;
			}
			sessionsLength += tmp;
		}
		// 2. 计算间隔秒数, 如果毫秒不足一秒，算做一秒
		if (sessionsLength % 1000 == 0) {
			sessionsLength = sessionsLength / 1000;
		} else {
			sessionsLength = sessionsLength / 1000 + 1;
		}

		// 填充value
		this.map.put(new IntWritable(-1), new IntWritable(this.timeChainMap.size()));// 填充会话个数
		this.map.put(new IntWritable(-2), new IntWritable(sessionsLength));// 会话长度
		this.outputValue.setValue(this.map);
		// 填充kpi
		this.outputValue.setKpi(KpiType.valueOfName(key.getStatsCommon().getKpi().getKpiName()));
		context.write(key, this.outputValue);
	}

}
