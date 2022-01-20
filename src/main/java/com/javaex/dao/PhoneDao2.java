package com.javaex.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.PersonVo;

@Repository
public class PhoneDao2 {

	// 0. import java.sql.*;
	private	Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	@Autowired
	private DataSource dataSource;

	private void getConnection() {
		try {

			// 2. Connection ������
			conn = dataSource.getConnection();
			// System.out.println("���Ӽ���");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	public void close() {
		// 5. �ڿ�����
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	// ��� �߰�
	public int personInsert(PersonVo personVo) {
		int count = 0;
		getConnection();

		try {

			// 3. SQL�� �غ� / ���ε� / ����
			String query = ""; // ������ ���ڿ������, ? ����
			query += " INSERT INTO person ";
			query += " VALUES (seq_person_id.nextval, ?, ?, ?) ";
			// System.out.println(query);

			pstmt = conn.prepareStatement(query); // ������ �����

			pstmt.setString(1, personVo.getName()); // ?(����ǥ) �� 1��°, �����߿�
			pstmt.setString(2, personVo.getHp()); // ?(����ǥ) �� 2��°, �����߿�
			pstmt.setString(3, personVo.getCompany()); // ?(����ǥ) �� 3��°, �����߿�

			count = pstmt.executeUpdate(); // ������ ����

			// 4.���ó��
			// System.out.println("[" + count + "�� �߰��Ǿ����ϴ�.]");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
		return count;
	}

	// ��� ����Ʈ(�˻����Ҷ�)
	public List<PersonVo> getPersonList() {
		return getPersonList("");
	}

	// ��� ����Ʈ(�˻��Ҷ�)
	public List<PersonVo> getPersonList(String keword) {
		List<PersonVo> personList = new ArrayList<PersonVo>();

		getConnection();

		try {

			// 3. SQL�� �غ� / ���ε� / ���� --> �ϼ��� sql���� �����ͼ� �ۼ��Ұ�
			String query = "";
			query += " select  person_id, ";
			query += "         name, ";
			query += "         hp, ";
			query += "         company ";
			query += " from person";

			if (keword != "" || keword == null) {
				query += " where name like ? ";
				query += " or hp like  ? ";
				query += " or company like ? ";
				pstmt = conn.prepareStatement(query); // ������ �����

				pstmt.setString(1, '%' + keword + '%'); // ?(����ǥ) �� 1��°, �����߿�
				pstmt.setString(2, '%' + keword + '%'); // ?(����ǥ) �� 2��°, �����߿�
				pstmt.setString(3, '%' + keword + '%'); // ?(����ǥ) �� 3��°, �����߿�
			} else {
				pstmt = conn.prepareStatement(query); // ������ �����
			}

			rs = pstmt.executeQuery();

			// 4.���ó��
			while (rs.next()) {
				int personId = rs.getInt("person_id");
				String name = rs.getString("name");
				String hp = rs.getString("hp");
				String company = rs.getString("company");

				PersonVo personVo = new PersonVo(personId, name, hp, company);
				personList.add(personVo);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();

		return personList;

	}

	// ��� 1�������� �����ö�
	public PersonVo getPerson(int personId) {
		PersonVo personVo = null;

		getConnection();

		try {

			// 3. SQL�� �غ� / ���ε� / ���� --> �ϼ��� sql���� �����ͼ� �ۼ��Ұ�
			String query = "";
			query += " select  person_id, ";
			query += "         name, ";
			query += "         hp, ";
			query += "         company ";
			query += " from person ";
			query += " where person_id = ? ";

			pstmt = conn.prepareStatement(query); // ������ �����

			pstmt.setInt(1, personId); // ?(����ǥ) �� 1��°, �����߿�

			rs = pstmt.executeQuery();

			// 4.���ó��
			rs.next();
			int id = rs.getInt("person_id");
			String name = rs.getString("name");
			String hp = rs.getString("hp");
			String company = rs.getString("company");

			personVo = new PersonVo(id, name, hp, company);

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();

		return personVo;

	}

	// ��� ����
	public int personUpdate(PersonVo personVo) {
		int count = 0;
		getConnection();

		try {

			// 3. SQL�� �غ� / ���ε� / ����
			String query = ""; // ������ ���ڿ������, ? ����
			query += " update person ";
			query += " set name = ? , ";
			query += "     hp = ? , ";
			query += "     company = ? ";
			query += " where person_id = ? ";

			pstmt = conn.prepareStatement(query); // ������ �����

			pstmt.setString(1, personVo.getName()); // ?(����ǥ) �� 1��°, �����߿�
			pstmt.setString(2, personVo.getHp()); // ?(����ǥ) �� 2��°, �����߿�
			pstmt.setString(3, personVo.getCompany()); // ?(����ǥ) �� 3��°, �����߿�
			pstmt.setInt(4, personVo.getPersonId()); // ?(����ǥ) �� 4��°, �����߿�

			count = pstmt.executeUpdate(); // ������ ����

			// 4.���ó��
			// System.out.println(count + "�� �����Ǿ����ϴ�.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		return count;
	}

	// ��� ����
	public int personDelete(int personId) {
		int count = 0;
		getConnection();

		try {
			// 3. SQL�� �غ� / ���ε� / ����
			String query = ""; // ������ ���ڿ������, ? ����
			query += " delete from person ";
			query += " where person_id = ? ";
			pstmt = conn.prepareStatement(query); // ������ �����

			pstmt.setInt(1, personId);// ?(����ǥ) �� 1��°, �����߿�

			count = pstmt.executeUpdate(); // ������ ����

			// 4.���ó��
			// System.out.println(count + "�� �����Ǿ����ϴ�.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		return count;
	}

}