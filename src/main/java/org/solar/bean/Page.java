package org.solar.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * 分页
 * 
 * @author wuxianchuan
 * @version 2.0
 */
public class Page<T> implements Serializable {

	private static final long serialVersionUID = -2053800594583879853L;

	/** 内容 */
	private List<T> list;

	/** 总记录数 */
	private final long totalRecord;

	/** 总页数 */
	private long totalPage;

	/** 分页信息 */
	private final Pageable pageable;

	/**
	 * 初始化一个新创建的Page对象
	 */
	public Page() {
		this.totalRecord = 0L;
		this.pageable = new Pageable();
	}

	/**
	 *
	 *            总记录数
	 * @param pageable
	 *            分页信息
	 */
	public Page(List<T> totalRecordlist,Pageable pageable) {
		int max = (int) pageable.getPageSize();
		int start = (int) pageable.getPageNumber() - 1;
		start = start * max;
		int sizemax = start + max;
		if (sizemax > totalRecordlist.size()) {
			sizemax = totalRecordlist.size();
		}
		List<T> list = new ArrayList<T>();
		for (int i = start; i < sizemax; i++) {
			list.add(totalRecordlist.get(i));
		}
		this.list=list;
		this.totalRecord = totalRecordlist.size();
		this.pageable = pageable;
	}
	public Page(List<T> list, long totalRecord, Pageable pageable) {
		this.list=list;
		this.totalRecord = totalRecord;
		this.pageable = pageable;
	}
	
	
	
	

	/**
	 * 获取页码
	 * 
	 * @return 页码
	 */
	public long getPageNumber() {
		return pageable.getPageNumber();
	}

	/**
	 * 获取每页记录数
	 * 
	 * @return 每页记录数
	 */
	public long getPageSize() {
		return pageable.getPageSize();
	}

	/**
	 * 获取总页数
	 * 
	 * @return 总页数
	 */
	public long getTotalPage() {
		if (totalRecord == 0) {
			totalPage = 1;
		} else if (pageable.getPageSize() == 0) {
			totalPage = 1;
		} else {
			totalPage = totalRecord / pageable.getPageSize();
			if ((totalRecord % pageable.getPageSize()) > 0) {
				totalPage++;
			}
		}

		return totalPage;
	}




	public void setList(List<T> list) {
		this.list = list;
	}


	/**
	 * 获取内容
	 * 
	 * @return 内容
	 */
	public List<T> getList() {
		return list;
	}

	/**
	 * 获取总记录数
	 * 
	 * @return 总记录数
	 */
	public long getTotalRecord() {
		return totalRecord;
	}

	/**
	 * 获取分页信息
	 * 
	 * @return 分页信息
	 */
	public Pageable getPageable() {
		return pageable;
	}

}