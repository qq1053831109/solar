package org.solar.bean;


import java.util.List;


public class Tree {

	/**唯一id*/
	private Integer id;
	/**code*/
	private String code;

	/**名称*/
	private String name;

	/**完整数据*/
	private Object data;
	
	/**是否为父节点*/
	private Boolean isParent;

	/**是否为叶子节点*/
	private String isLeaf;
	
	/**子节点*/
	private List<Tree> child;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Boolean getParent() {
		return isParent;
	}

	public void setParent(Boolean parent) {
		isParent = parent;
	}

	public String getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(String leaf) {
		isLeaf = leaf;
	}

	public List<Tree> getChild() {
		return child;
	}

	public void setChild(List<Tree> child) {
		this.child = child;
	}


}
