package test;

import guc.bttsBtngan.post.PostMain;
import guc.bttsBtngan.post.data.Comment;
import guc.bttsBtngan.post.data.Post;
import guc.bttsBtngan.post.data.Post.PostReport;
import guc.bttsBtngan.post.services.OrderService;
import guc.bttsBtngan.post.services.PostService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

	Post makePost() {

		Post post = new Post();

		post.setUserId("user btts");
		post.setContent("testing post");
		post.setDate(new Date(System.currentTimeMillis()));

		post.setNoOfFollwer(0);

		post.setComments(new ArrayList<Comment>());

		post.setPostVotes(new ArrayList<Post.PostVote>());

		post.setPostReports(new ArrayList<Post.PostReport>());

		post.setPostTags(new ArrayList<String>());

		post.setPostFollowers(new ArrayList<String>());

		return post;
	}

//	// given
//    DBObject objectToSave = BasicDBObjectBuilder.start()
//        .add("key", "value")
//        .get();
//
//    // when
//    mongoTemplate.save(objectToSave, "collection");
//
//    // then
//    assertThat(mongoTemplate.findAll(DBObject.class, "collection")).extracting("key")
//        .containsOnly("value");

	@Test
	public void createPostTest() {

		try {
			// given
			Post post = makePost();

			// when
			String realOut = postService.createPost(post);
			String expected = "DONE, created post is: " + (post).toString();

			// then
			Assert.assertEquals(expected, realOut);

		} catch (ExecutionException e) {
			throw new RuntimeException(e);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	@Test
	public void followPostTest() {
		try {
			// given
			String userId = "";
			String postId = postService.getValidPostId();
			boolean follow = true;

			// when
			String realOut = postService.followPost(userId, postId, follow);
			String expected = "request done!";

			// then
			Assert.assertEquals(expected, realOut);

		} catch (ExecutionException e) {
			throw new RuntimeException(e);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void reportPostTest() {
		try {
			// given
			String userId = "";
			String postId = postService.getValidPostId();
			String reportComment = "";

			// when
			String realOut = postService.reportPost(userId, postId, reportComment);
			String expected = "DONE, Potatoes report post";

			// then
			Assert.assertEquals(expected, realOut);

		} catch (ExecutionException e) {
			throw new RuntimeException(e);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void postRecommendTest() {
		try {
			// given
			String userId = "";

			// when
			List<Post> realOut = postService.postRecommend(userId);
//			String expected="";

			// then
//			Assert.assertEquals(expected, realOut);
			int lastFollowersCount = Integer.MAX_VALUE;
			for (Post post : realOut) {
				int curr = post.getNoOfFollwer();
				if (curr > lastFollowersCount) {
					throw new Exception("The recommended posts are incorrect");
				}

				lastFollowersCount = curr;
			}

		} catch (ExecutionException e) {
			throw new RuntimeException(e);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void createCommentTest() {

		try {
			// given
			Comment comment = new Comment("1234", "test comment");
			String id = postService.getValidPostId();
			// when
			String realOut = postService.commentPost("", id, "test comment");
			String expected = "DONE, Potatoes comment post";

			// then
			Assert.assertEquals(expected, realOut);

		} catch (ExecutionException e) {
			throw new RuntimeException(e);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	@Test
	public void likePostTest() {

		try {
			// given
			String id = postService.getValidPostId();
			Post post = postService.getPost(id);
			int likesBefore = post.getPostVotes().size();
			postService.postVote("1234", id, true);
			Post postAfter = postService.getPost(id);
			int likesAfter = postAfter.getPostVotes().size();
			// when

			// then
			Assert.assertEquals(likesBefore, likesAfter);

		} catch (ExecutionException e) {
			throw new RuntimeException(e);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	@Test
	public void dislikePostTest() {

		try {
			// given
			String id = postService.getValidPostId();
			Post post = postService.getPost(id);
			int likesBefore = post.getPostVotes().size();
			postService.postVote("1234", id, false);
			Post postAfter = postService.getPost(id);
			int likesAfter = postAfter.getPostVotes().size();
			// when

			// then
			Assert.assertEquals(likesBefore, likesAfter);

		} catch (ExecutionException e) {
			throw new RuntimeException(e);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	@Test
	public void likeCommentTest() {

		try {
			// given
			String id = postService.getValidPostId();
			Post post = postService.getPost(id);
			Comment commentBefore = post.getComments().get(0);
			int likesBefore = commentBefore.getCommentVotes().size();
			postService.commentVote("1234", id, commentBefore.getId(), true);
			Comment commentAfter = post.getComments().get(0);
			int likesAfter = commentAfter.getCommentVotes().size();
			// when

			// then
			Assert.assertEquals(likesBefore, likesAfter);

		} catch (ExecutionException e) {
			throw new RuntimeException(e);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	@Test
	public void dislikeCommentTest() {

		try {
			// given
			String id = postService.getValidPostId();
			Post post = postService.getPost(id);
			Comment commentBefore = post.getComments().get(0);
			int likesBefore = commentBefore.getCommentVotes().size();
			postService.commentVote("1234", id, commentBefore.getId(), false);
			Comment commentAfter = post.getComments().get(0);
			int likesAfter = commentAfter.getCommentVotes().size();
			// when

			// then
			Assert.assertEquals(likesBefore, likesAfter);

		} catch (ExecutionException e) {
			throw new RuntimeException(e);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	@Test
	public void tagInPostTest() {

		try {
			// given
			String postId = postService.getValidPostId();
			String[] userIdsToBeTagged = new String[0];
			Post post = postService.getPost(postId);
			String userIdSending = post.getUserId();

			// when
			String realOut = postService.tagInPost(postId, userIdsToBeTagged, userIdSending);
			String expected = "DONE, Potatoes tag in post : " + (post).toString();

			// then
			Assert.assertEquals(expected, realOut);

		} catch (ExecutionException e) {
			throw new RuntimeException(e);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void commentTagInPostTest() {

		try {
			// given
			String postId = postService.getValidPostId();
			Post post = postService.getPost(postId);

			postService.commentPost(post.getUserId(), postId, "gmdan el btngan comment");

			post = postService.getPost(postId);
			String commentId = post.getComments().get(post.getComments().size() - 1).getId();
			String[] userIdsToBeTagged = new String[0];
			String userIdSending = post.getUserId();

			// when
			String realOut = postService.commentTagInPost(postId, commentId, userIdsToBeTagged, userIdSending);
			String expected = "DONE, Potatoes tag in post : " + (post).toString();

			// then
			Assert.assertEquals(expected, realOut);

		} catch (ExecutionException e) {
			throw new RuntimeException(e);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void delTagInPostTest() {

		try {
			// given
			String postId = postService.getValidPostId();
			String[] userIdsToBeTagged = new String[0];
			Post post = postService.getPost(postId);

			// when
			String realOut = postService.delTagInPost(postId, userIdsToBeTagged, post.getUserId());
			String expected = "DONE, Potatoes tag in post : " + (post).toString();

			// then
			Assert.assertEquals(expected, realOut);

		} catch (ExecutionException e) {
			throw new RuntimeException(e);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void delCommentTagInPostTest() {

		try {
			// given
			String postId = postService.getValidPostId();
			Post post = postService.getPost(postId);

			postService.commentPost(post.getUserId(), postId, "gmdan el btngan comment to delete tag");

			post = postService.getPost(postId);
			String commentId = post.getComments().get(post.getComments().size() - 1).getId();
			String[] userIdsToBeTagged = new String[0];

			// when
			String realOut = postService.delCommentTagInPost(postId, commentId, userIdsToBeTagged, post.getComments().get(post.getComments().size() - 1).getCommenterId());
			String expected = "DONE, Potatoes tag in post : " + (post).toString();

			// then
			Assert.assertEquals(expected, realOut);

		} catch (ExecutionException e) {
			throw new RuntimeException(e);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void modTest() {

		try {
			// given
			this.createPostTest();

			String postId = postService.getValidPostId();
			Post post = postService.getPost(postId);
			String modId = post.getModeratorId();
			String userId = post.getUserId();
			int report_count_prev = post.getPostReports().size();

			// when
			postService.reportPost(userId, postId, "Manga 3alameya");
			ArrayList<PostReport> postReports = postService.checkPostReports(postId, modId);

			// then
			Assert.assertEquals(report_count_prev + 1, postReports.size());

		} catch (ExecutionException e) {
			throw new RuntimeException(e);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}