package com.example.demo.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.ManyToOne;

import com.example.demo.entities.Category;
import com.example.demo.entities.Comment;
import com.example.demo.entities.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@Setter
@Getter
public class PostDto {

	
	private Integer postId;
	private String title;
	private String content;
	private String imageName="default.png";
	private Date addDate;
	private CategoryDto category;
	
	private UserDto user;
	private Set<CommentDto> comments=new HashSet<>();
}
