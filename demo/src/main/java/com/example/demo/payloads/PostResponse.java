package com.example.demo.payloads;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@Setter
@Getter
public class PostResponse {
	
	private List<PostDto> content;
	private Integer pageNumber;
	private int pageSize;
	private long totalElement;
	private long totalPages;
	private boolean lastPage;
	

}
