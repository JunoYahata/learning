package com.myworkbench.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.myworkbench.model.Cd;
import com.myworkbench.model.Memo;
import com.myworkbench.repository.CdRepository;
import com.myworkbench.repository.MemoRepository;

@Service
public class MemoService {

	@Autowired
	MemoRepository memoRepository;
	@Autowired
	CdRepository cdRepository;

	private final String TAG_CODE = "10";

	public List<Memo> findAll() {

		List<Memo> memos = memoRepository.findAll();
		for (Memo memo : memos) {
			setString(memo);
		}

		return memos;
	}
	
	public Memo findByUId(UUID uid) {
		return memoRepository.findById(uid).orElseThrow();
	}

	public boolean insertOrUpdate(Memo memo) {
		return memoRepository.save(memo) != null;
	}

	public void delete(Memo memo) {
		memoRepository.delete(memo);
	}

	public void setString(Memo memo) {
		Cd probe = new Cd();
		probe.setCategory(TAG_CODE);
		probe.setCd(memo.getTagCd());
		memo.setTagCdName(cdRepository.findOne(Example.of(probe)).orElseThrow().getName());

		memo.setCreateTimeStr(memo.getCreateTime().toString());
		memo.setUpdateTimeStr(memo.getUpdateTime().toString());

	}

}
