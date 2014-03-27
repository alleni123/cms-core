package com.lj.core.model;

 


public enum RoleType {
	
	 
	 
	ROLE_ADMIN("管理类型"), ROLE_PUBLISH("发布类型"), ROLE_AUDIT("审查类型");
	
private String name;
	
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	private RoleType(String name){
		this.name=name;
	}
}
