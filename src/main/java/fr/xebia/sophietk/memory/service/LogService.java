package fr.xebia.sophietk.memory.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.inject.Singleton;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Singleton
public class LogService {

	List<Map<String, Object>> logs;

	public LogService() {
		reset();
	}

	public void addLog(String player, String action) {
		Map<String, Object> log = Maps.newHashMap();
		log.put("date", new Date().getTime());
		log.put("player", player);
		log.put("action", action);
		logs.add(log);
	}

	public List<Map<String, Object>> getLogs() {
		return logs;
	}

	public void reset() {
		logs = Lists.newArrayList();
	}
}
