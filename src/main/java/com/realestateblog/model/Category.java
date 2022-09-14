package com.realestateblog.model;

public enum Category {
	News("부동산 소식"), Board("자유게시판");
	private final String description;

	private Category(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
	
}
