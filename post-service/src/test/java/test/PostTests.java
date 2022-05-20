package test;

import guc.bttsBtngan.post.PostMain;
import guc.bttsBtngan.post.data.Comment;
import guc.bttsBtngan.post.data.Post;
import guc.bttsBtngan.post.services.OrderService;
import guc.bttsBtngan.post.services.PostService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@SpringBootTest(classes = PostMain.class)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)

public class PostTests {
	@Autowired
	private OrderService orderService;

	@Autowired
	private PostService postService;

	Post makePost(){

		Post post = new Post();

		post.setUserId("user btts");
		post.setContent("testing post");
		post.setDate(new Timestamp(System.currentTimeMillis()) );

		post.setNoOfFollwer(0);

		post.setComments(new ArrayList<Comment>());

		post.setPostVotes(new ArrayList<Post.PostVote>());

		post.setPostReports(new ArrayList<Post.PostReport>());

		post.setPostTags(new ArrayList<String>());

		post.setPostFollowers(new ArrayList<String>());

		return post;
	}

	@Test
	public void createPostTest(){
		UUID uuid = UUID.randomUUID();
		ArrayList userID = new ArrayList<String>();
		userID.add(uuid.toString());
		try {
			Post post=makePost();
			String realOut=postService.createPost(post);
			String expected="DONE, created post is: "+(post).toString();
			Assert.assertEquals(expected, realOut);

		} catch (ExecutionException e) {
			throw new RuntimeException(e);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}


	}

}