package com.myworkbench.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myworkbench.model.KintaiMemo;
import com.myworkbench.repository.KintaiMemoRepository;

@Service
public class KintaiMemoService {

	@Autowired
	KintaiMemoRepository repository;

	public KintaiMemo findByDay(String day) {

		KintaiMemo kintai = repository.findById(day).orElse(null);
		if (kintai != null)
			kintai.calcTimes();
		return kintai;
	}

	public List<KintaiMemo> findAll() {
		return repository.findAllByOrderByDayDesc();
	}

	public boolean insertOrUpdate(KintaiMemo kintai) {
		return repository.save(kintai) != null;
	}

}
