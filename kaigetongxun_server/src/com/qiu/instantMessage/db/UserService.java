package com.qiu.instantMessage.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.management.Query;

public class UserService {

	/**
	 * 使用email账户进行登录
	 * 
	 * @param email
	 * @param passowrd
	 * @return
	 * @throws UsernameNotFoundException
	 *             用户不存在
	 * @throws PasswordException
	 *             密码错误
	 * @throws StateException
	 *             账户被锁定
	 * @throws SQLException
	 *             数据库连接失败
	 */

	public String loginForEmail(String email, String password)
			throws UsernameNotFoundException, PasswordException, StateException, SQLException {
		return login(email, password, "SELECT * FROM users where email=?");
	}

	private String login(String key, String password, String sql)
			throws UsernameNotFoundException, PasswordException, StateException, SQLException {

		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, key);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				if (rs.getInt("state") == 0) {
					if (rs.getString("password").equals(password)) {// 询问密码是否相同
						return rs.getString(1);
					} else {
						throw new PasswordException();
					}
				} else {
					throw new StateException();
				}

			} else {
				throw new UsernameNotFoundException();
			}

		} catch (SQLException e) {
			throw e;
		} finally {
			conn.close();
		}

	}

	/**
	 * 使用手机号码来登录
	 * 
	 * @param phone
	 * @param password
	 * @return
	 * @throws UsernameNotFoundException
	 *             用户不存在
	 * @throws PasswordException
	 *             密码错误
	 * @throws StateException
	 *             账户被锁定
	 */

	public String loginForPhone(String phone, String password)
			throws UsernameNotFoundException, PasswordException, StateException, SQLException {
		return login(phone, password, "SELECT * FROM users where phonenumber=?");
	}

	/**
	 * 获得自己好友列表信息
	 * 
	 * @param uid
	 *            自己的编号
	 * @return 好友列表信息
	 */
	public Vector<UserInfo> getHaoyouliebiao(String uid) throws SQLException {

		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			PreparedStatement pst = conn.prepareStatement("SELECT u.`uid`,u.`img`,u.`netname`,u.`info` FROM hy h"
					+ " INNER JOIN users u ON u.`uid`=h.`hyuid` AND h.`uid`=?");
			pst.setString(1, uid);
			ResultSet rs = pst.executeQuery();
			Vector<UserInfo> vector = new Vector();
			while (rs.next()) {
				UserInfo userInfo = new UserInfo();
				userInfo.setUid(rs.getString(1));
				userInfo.setImg(rs.getString(2));
				userInfo.setNetname(rs.getString(3));
				userInfo.setInfo(rs.getString(4));
				vector.add(userInfo);
			}

			return vector;

		} catch (SQLException e) {
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 个人资料查询 好友资料查询
	 * 
	 * @param uid
	 * @return 返回信息
	 * @throws SQLException
	 */
	public UserInfo2 getUserinfo(String uid) throws SQLException {
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			PreparedStatement pst = conn.prepareStatement("SELECT * FROM USERS WHERE UID=?");
			pst.setString(1, uid);
			ResultSet rs = pst.executeQuery();
			UserInfo2 userInfo2 = new UserInfo2();
			if (rs.next()) {
				userInfo2.setUid(rs.getString("uid"));
				userInfo2.setPhonenumber(rs.getString("phonenumber"));
				userInfo2.setEmail(rs.getString("email"));
				userInfo2.setNetname(rs.getString("netname"));
				userInfo2.setInfo(rs.getString("info"));
				userInfo2.setName(rs.getString("name"));
				userInfo2.setImg(rs.getString("img"));
				userInfo2.setBack(rs.getString("back"));
				userInfo2.setSex(rs.getString("sex"));
				userInfo2.setYy(rs.getInt("yy"));
				userInfo2.setMm(rs.getInt("mm"));
				userInfo2.setDd(rs.getInt("dd"));
			}
			return userInfo2;

		} catch (SQLException e) {
			throw e;
		} finally {
			conn.close();
		}

	}

	public void regUser(String username, String password) throws UsernameException, SQLException {
		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			PreparedStatement pst = conn.prepareStatement("SELECT * FROM USERS WHERE phonenumber=? or email=?");
			pst.setString(1, username);
			pst.setString(2, username);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				throw new UsernameException();
			}
			if (username.indexOf("@") >= 0) {
				pst = conn.prepareStatement("INSERT INTO users(uid,email,PASSWORD,createtime) VALUES(?,?,?,SYSDATE())");
			} else if (username.trim().length() == 11) {
				pst = conn.prepareStatement(
						"INSERT INTO users(uid,phonenumber,PASSWORD,createtime) VALUES(?,?,?,SYSDATE())");
			}

			pst.setString(1, System.currentTimeMillis() + "R" + (int) (Math.random() * 10000));
			pst.setString(2, username);
			pst.setString(3, password);
			if (pst.executeUpdate() <= 0) {
				throw new SQLException();
			}

		} catch (SQLException e) {
			throw e;
		} finally {
			conn.close();
		}

	}

	public static void main(String[] args) {
		try {
			new UserService().loginForPhone("1397572464", "123456");
			System.out.println("成功");
		} catch (UsernameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PasswordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
