package com.jd.scrt.common.task.support;

import org.apache.commons.lang.StringUtils;

/**
 * 默认任务队列生成器
 *
 * Created by wangjunlei on 2016-01-24 17:19:50.
 * 
 * @since 1.0.7
 * 
 */
public class DefaultTaskQueueGenerator extends AbstractTaskQueueGenerator {

	/**
	 * 分配queue队列
	 * 
	 * 优先寻找配置顺序: <br/>
	 * 1 queue.list.doXxxxTask <br/>
	 * 2 queue.list.default <br/>
	 * 3 queue.size.doXxxxTask <br/>
	 * 4 queue.size.default <br/>
	 *
	 * Created by wangjunlei on 2016-01-24 17:19:50.
	 * @param queueKey
	 * @param ref
	 *            参照如果为null，将会使用随机数，不为空将取其hash值。
	 * @return
	 * @throws Exception
	 */
	@Override
	public String generateTaskQueue(String queueKey, String ref) throws Exception {
		if (StringUtils.isEmpty(queueKey)) {
			logger.warn("generateTaskQueue: queueKey cannot be empty,return defaultQueueVal! ");
			return this.getDefaultQueueVal();
		}
		try {
			String queueIds = this.getProperty(this.getQueueListPrefix() + queueKey);
			if (queueIds == null || queueIds.length() == 0) {
				queueIds = this.getProperty(this.getQueueListPrefix() + this.getDefaultQueueKey());
			}
			if (queueIds != null && queueIds.length() > 0) {
				return this.generateQueueByList(queueIds, ref);
			}

			String queueSize = this.getProperty(this.getQueueSizePrefix() + queueKey);
			if (queueSize == null || queueSize.length() == 0) {
				queueSize = this.getProperty(this.getQueueSizePrefix() + this.getDefaultQueueKey());
			}
			if (queueSize != null && queueSize.length() > 0) {
				return this.generateQueueBySize(queueSize, ref);
			}

			logger.info("generateTaskQueue: queueKey[" + queueKey + "] return defaultQueueVal[" + this.getDefaultQueueVal() + "]!");
			return this.getDefaultQueueVal();
		} catch (Exception e) {
			logger.error("generateTaskQueue-error: queueKey[" + queueKey + "]", e);
			return this.getDefaultQueueVal();
		}
	}

}
