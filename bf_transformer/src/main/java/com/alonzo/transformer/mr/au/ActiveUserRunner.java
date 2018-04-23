package com.alonzo.transformer.mr.au;

import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.log4j.Logger;

import com.alonzo.common.EventLogConstants;
import com.alonzo.transformer.model.dim.StatsUserDimension;
import com.alonzo.transformer.model.value.map.TimeOutputValue;
import com.alonzo.transformer.model.value.reduce.MapWritableValue;
import com.alonzo.transformer.mr.TransformerBaseRunner;

/**
 * 统计active user的入口类
 * 
 * @author alonzo
 *
 */
public class ActiveUserRunner extends TransformerBaseRunner {
	private static final Logger logger = Logger.getLogger(ActiveUserRunner.class);

	public static void main(String[] args) {
		System.setProperty("hadoop.home.dir", "E:\\大数据（全）\\大数据软件工具\\hadoop-2.5.0-cdh5.3.6\\hadoop-2.5.0-cdh5.3.6");
		ActiveUserRunner runner = new ActiveUserRunner();
		runner.setupRunner("active-user", ActiveUserRunner.class, ActiveUserMapper.class, ActiveUserReducer.class, StatsUserDimension.class, TimeOutputValue.class, StatsUserDimension.class,
				MapWritableValue.class);
		try {
			runner.startRunner(args);
		} catch (Exception e) {
			logger.error("运行active user任务出现异常", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	protected Filter fetchHbaseFilter() {
		FilterList filterList = new FilterList();
		// 定义mapper中需要获取的列名
		String[] columns = new String[] { EventLogConstants.LOG_COLUMN_NAME_UUID, // 用户id
				EventLogConstants.LOG_COLUMN_NAME_SERVER_TIME, // 服务器时间
				EventLogConstants.LOG_COLUMN_NAME_PLATFORM, // 平台名称
				EventLogConstants.LOG_COLUMN_NAME_BROWSER_NAME, // 浏览器名称
				EventLogConstants.LOG_COLUMN_NAME_BROWSER_VERSION // 浏览器版本号
		};
		filterList.addFilter(this.getColumnFilter(columns));
		return filterList;
	}
}
