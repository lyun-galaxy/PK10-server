package com.pk10.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pk10.bean.LotteryHistory;
import com.pk10.dao.LotteryHistoryDao;
import com.pk10.service.LotteryHistoryService;

@Service
public class LotteryHistoryServiceImpl implements LotteryHistoryService {

	@Autowired
	private LotteryHistoryDao lotteryHistoryDao;

	@Override
	public Integer save(LotteryHistory t) throws Exception {
		if (lotteryHistoryDao.update(t)>0) {
			return 0;
		}else{			
			return lotteryHistoryDao.save(t);
		}
	}

	@Override
	public Integer update(LotteryHistory t) throws Exception {
		return lotteryHistoryDao.update(t);
	}

	@Override
	public List<LotteryHistory> getAll() throws Exception {
		return lotteryHistoryDao.getAll();
	}

	@Override
	public LotteryHistory getOneById(LotteryHistory t) throws Exception {
		for (int i = 0; i < 20; i++) {
			LotteryHistory lottery = lotteryHistoryDao.getOneById(t);
			if (lottery == null) { //还没有获取到最新的开奖结果，等待收集器收集结果
				Thread.sleep(3000);
				continue;
			}else{
				return lottery;
			}
		}
		throw new Exception("get lottery num has a error");
	}

	@Override
	public Integer deleteOneById(LotteryHistory t) throws Exception {
		return lotteryHistoryDao.deleteOneById(t);
	}

	@Override
	public LotteryHistory getLastLottery() throws Exception {
		return lotteryHistoryDao.getLastLottery();
	}

	@Override
	public List<LotteryHistory> getLastLottery(Integer num) {
		return lotteryHistoryDao.getLastNumLottery(num);
	}
	
}
