package guc.bttsBtngan.post.services;

import guc.bttsBtngan.post.amqp.RabbitMQConfig;
import guc.bttsBtngan.post.data.Comment;
import guc.bttsBtngan.post.data.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class OrderService {
    @Autowired
    PostService postService ;

    public OrderService( PostService postService) {
        this.postService = postService;
    }

    public String createPost(Post post) throws Exception {
        return postService.createPost(post);
    }
    public String followPost(String userId, String postId, boolean follow)throws Exception {
        return postService.followPost(userId,postId,follow);
    }
    public String reportPost(String userId, String postId, String reportComment)throws Exception {
        return postService.reportPost(userId,postId,reportComment);
    }
    public String commentPost(String userId, String postId, String comment)throws Exception {
        return postService.commentPost(userId,postId,comment);
    }
    public String commentVote(String userId, String postId, String commentId,boolean vote)throws Exception {
        return postService.commentVote(userId,postId,commentId,vote);
    }
    public String postVote(String userId, String postId, boolean vote)throws Exception {
        return postService.postVote(userId,postId,vote);
    }
    public String tagInPost(String postId, String[]userIds,String userIdSending)throws Exception {
        return postService.tagInPost(postId,userIds,userIdSending);
    }

    public String delTagInPost(String postId, String[]userIds,String user_id)throws Exception {
        return postService.delTagInPost(postId,userIds,user_id);
    }

    public String commentTagInPost(String postId, String commentId, String[]userIds,String userIdSending)throws Exception {
        return postService.commentTagInPost(postId,commentId,userIds,userIdSending);
    }

    public String delCommentTagInPost(String postId, String commentId, String[]userIds, String user_id)throws Exception {
        return postService.delCommentTagInPost(postId,commentId,userIds,user_id);
    }
    public String searchPosts(String subContent,String userId) throws Exception {
        return postService.searchPosts(subContent,userId);
    }
    public String assignModerator(String postId, String userId, String modId)throws Exception {
        return postService.assignModerator(postId,userId,modId);
    }

//    public String checkPostReports(String postId, String userId)throws Exception {
//        return postService.checkPostReports(postId,userId);
//    }
    public String postRecommend(String userId) throws Exception  {
        return postService.postRecommend(userId).toString();
    }
}
