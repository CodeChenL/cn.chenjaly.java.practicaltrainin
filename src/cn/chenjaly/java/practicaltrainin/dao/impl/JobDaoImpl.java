package cn.chenjaly.java.practicaltrainin.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import cn.chenjaly.java.practicaltrainin.bean.Dept;
import cn.chenjaly.java.practicaltrainin.bean.Job;
import cn.chenjaly.java.practicaltrainin.dao.JobDao;
import cn.chenjaly.java.practicaltrainin.utils.JDBCUtils;
import cn.chenjaly.java.practicaltrainin.utils.PageModel;

public class JobDaoImpl implements JobDao {

	@Override
	public List<Job> findJob(Job job, PageModel model) {
		List<Job> Jobs = new ArrayList<>();
		Connection conn = JDBCUtils.getConnection();
		StringBuffer  sql = new StringBuffer("select * from job_inf where 1=1 ");
		try {
			Statement pstm = conn.createStatement();
			
			if(StringUtils.isNoneBlank(job.getName())) {
				sql.append("and name like '%").append(job.getName()).append("%'");
				System.out.println("sql:"+sql);
			}
			if(StringUtils.isNotBlank(job.getRemark())) {
				sql.append("and remark like'%").append(job.getRemark()).append("%'");
				System.out.println("sql:"+sql);
			}
			
			sql.append(" limit ").append(model.getStartRow()).append(",").append(PageModel.pageSize);
			
			ResultSet rs = pstm.executeQuery(sql.toString());
			System.out.println("sql:"+sql);
			
			while(rs.next()) {
				Job j = new  Job();
				j.setId(rs.getInt("id"));
				j.setName(rs.getString("name"));
				j.setRemark(rs.getString("remark"));
				Jobs.add(j);
			}
			return Jobs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCUtils.closeConn(conn);
		}
		return null;
	}

	@Override
	public int getTotalCountByJob(Job job) {
		List<Job> jobs = new ArrayList<>();
		Connection conn = JDBCUtils.getConnection();
		StringBuffer sql =new StringBuffer("select count(*) from job_inf where 1=1 ");
		try {
			Statement pstm = conn.createStatement();
			if(StringUtils.isNoneBlank(job.getName())) {
				sql.append(" and name like '&").append(job.getName()).append("%'");
			}
			if(StringUtils.isNoneBlank(job.getRemark())) {
				sql.append(" and remark like '%").append(job.getRemark()).append("%'");
			}
			ResultSet rs = pstm.executeQuery(sql.toString());
			while(rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCUtils.closeConn(conn);
		}
		return 0;
	}

	@Override
	public Job findJob(Integer id) {
		Connection conn = JDBCUtils.getConnection();
		String sql = "select * from job_inf where id=?";
		try {
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1,id);
			ResultSet rs = pstm.executeQuery();
			while(rs.next()) {
				Job job = new Job();
				job.setId(rs.getInt("id"));
				job.setName(rs.getString("name"));
				job.setRemark(rs.getString("remark"));
				return job;
			}
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCUtils.closeConn(conn);
		}
		return null;
	}

	@Override
	public boolean addJob(Job job) {
		Connection conn = JDBCUtils.getConnection();
		String sql = "insert into job_inf (name,remark) values(?,?)";
		try {
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1,job.getName());
			pstm.setString(2, job.getRemark());
			
			int rs = pstm.executeUpdate();
			if(rs>0) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCUtils.closeConn(conn);
		}
		return false;
	}

	@Override
	public void updateJob(Job job) {
		Connection conn = JDBCUtils.getConnection();
		String sql = "update job_inf set name =? ,remark = ? where id= ?";
		try {
			PreparedStatement pstm = conn.prepareStatement(sql);
			
			pstm.setString(1,job.getName());
			pstm.setString(2, job.getRemark());
			pstm.setInt(3, job.getId());
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCUtils.closeConn(conn);
		}
		
	}

	@Override
	public void deleteJob(String[] id) {
		Connection conn = JDBCUtils.getConnection();
		try {
		for(int i=0;i<=id.length-1;i++) {
			String sql = "delete from job_inf where id = '"+id[i]+"'";
				PreparedStatement pstm = conn.prepareStatement(sql);
				pstm.executeUpdate();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				JDBCUtils.closeConn(conn);
			}
		
	}

}