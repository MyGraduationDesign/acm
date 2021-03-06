package com.lxg.acm.context;

import com.alibaba.fastjson.JSON;
import com.lxg.acm.entity.Link;
import com.lxg.acm.mapper.LinkMapper;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Beetl 模版配置加载
 * @author Administrator
 *
 */
public class BeetlConfigLoader extends BeetlGroupUtilConfiguration {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private LinkMapper linkMapper;

	@Override
	protected void initOther() {
		logger.info("前端模板配置信息加载start");
		Map<String, Object> sharedVars = new HashMap<String, Object>();
		sharedVars.put("languages", OJConfig.instance.languages);
		// 分页大小
		sharedVars.put("RANK_PAGE_SIZE", 10);
		sharedVars.put("CONTEST_PAGE_SIZE", 5);
		sharedVars.put("STATUS_PAGE_SIZE", 10);
		sharedVars.put("PROBLEM_PAGE_SIZE", 10);
		sharedVars.put("CLASSIFIER_PAGE_SIZE", 5);

		List<Link> links = linkMapper.selectAll();
		sharedVars.put("links",links);
		groupTemplate.setSharedVars(sharedVars);
		groupTemplate.registerFunctionPackage("so", new BeetlFunction());
		logger.info("配置文件信息={}", JSON.toJSON(sharedVars));
		logger.info("前端模板配置信息加载end");
	}

}
