package com.javaex.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.PersonVo;

@Repository
public class PhoneDao {

	@Autowired
	private SqlSession sqlSession;

	// 전체리스트 가져오기\
	public List<PersonVo> getPersonList() {
		System.out.println("phoneDao.getpersonList()");

		List<PersonVo> personList = sqlSession.selectList("phonebook.selectList");
		System.out.println(personList);

		return personList;
	}

	// 전화번호 추가
	public int personInsert(PersonVo personVo) {
		System.out.println("phoneDao.personinsert()");
		int count = sqlSession.insert("phonebook.insert", personVo);
		System.out.println(count + "건 저장되었습니다");
		return count;
	}
	//삭제
	public int personDelete(int personId) {
		System.out.println("phoneDao.persondelete");
	
		int count =sqlSession.delete("phonebook.delete",personId);
		 System.out.println(count+ "건 저장됬습니다");
			return count;
	}

	//수정
	public int personUpdate(PersonVo personVo) {
		System.out.println("phoneDao.personUpdate");
		int count=sqlSession.update("phonebook.update",personVo);
		System.out.println(count +"건 저장되었습니다");
		return count;
	}
	public PersonVo getPerson(int personId) {
	System.out.println("phoneDao.getPerson");

	return sqlSession.selectOne("phonebook.selectOne", personId);
	
	}
}