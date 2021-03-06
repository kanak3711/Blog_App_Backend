package com.example.demo.controllers;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.config.AppContants;
import com.example.demo.entities.Post;
import com.example.demo.payloads.ApiResponse;
import com.example.demo.payloads.CategoryDto;
import com.example.demo.payloads.PostDto;
import com.example.demo.payloads.PostResponse;
import com.example.demo.payloads.UserDto;
import com.example.demo.services.FileService;
import com.example.demo.services.PostService;
import com.example.demo.services.UserService;

@RestController
@RequestMapping("/api/")
public class PostController {

	@Autowired
	private UserService userServices;
	
	@Autowired
	private FileService fileServices;
	
	@Value("${project.image}")
	private String path;
	@Autowired
	private PostService postServices;
	// POSt-create user

	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost( @RequestBody PostDto postDto
			,@PathVariable Integer userId,@PathVariable Integer categoryId)
	{
		PostDto createPostDto=this.postServices.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createPostDto,HttpStatus.CREATED);
	}
	
	
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId)
	{
		List<PostDto> posts=this.postServices.getPostByUser(userId);
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	}
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId)
	{
		List<PostDto> posts=this.postServices.getPostByCategory(categoryId);
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	}
	
	//get all posts
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(value="pageNumber",defaultValue=AppContants.PAGE_NUMBER,required=false)Integer pageNumber,
	@RequestParam(value="pageSize",defaultValue=AppContants.PAFE_SIZE,required=false) Integer pageSize,
	@RequestParam(value="sortBy",defaultValue=AppContants.SORT_BY,required=false) String sortBy,
	@RequestParam(value="sortDir",defaultValue=AppContants.SORT_DIR,required=false) String sortDir)
	{
		
		PostResponse cat=this.postServices.getAllPost(pageNumber,pageSize,sortBy,sortDir);
		return ResponseEntity.ok(cat);
	}
	///get post detail 
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getCategory(@PathVariable Integer postId)
	{
		PostDto categoryDto=this.postServices.getPostById(postId);
		
		return new ResponseEntity<PostDto>(categoryDto,HttpStatus.OK);
				//.getUserById(catId));
	}
	
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer postId)
	{
		this.postServices.deletePost(postId);
		return new ResponseEntity(new ApiResponse("Post deleted Successfully",true),HttpStatus.OK);
	}
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updateCategory(@Valid @RequestBody PostDto postDto,@PathVariable Integer postId)
	{
		PostDto updated=this.postServices.updatePost(postDto,postId);
		return new ResponseEntity<PostDto>(updated,HttpStatus.OK);
	}
	
	//search
	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(
			@PathVariable("keywords") String keywords
			)
	{
		List<PostDto> result=this.postServices.searchPosts(keywords);
		return new ResponseEntity<List<PostDto>>(result,HttpStatus.OK);
	}
	
	//post imageupload
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(
			@RequestParam("image") MultipartFile  image,
			@PathVariable Integer postId
	)throws IOException{
		
		PostDto postDto=this.postServices.getPostById(postId);
		
		String fileName=this.fileServices.uploadImage(path, image);
		
		postDto.setImageName(fileName);
		PostDto updatepost=this.postServices.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatepost,HttpStatus.OK);
		
	}
	//post imageupload
		@GetMapping(value="post/image/{imageName}",produces=MediaType.IMAGE_JPEG_VALUE)
		public void downloadImage(
				@PathVariable("imageName") String imageName,
				HttpServletResponse response
		)throws IOException{
			InputStream resource =this.fileServices.getResource(path, imageName);
			response.setContentType(MediaType.IMAGE_JPEG_VALUE);
			StreamUtils.copy(resource, response.getOutputStream());
			
		}

}
