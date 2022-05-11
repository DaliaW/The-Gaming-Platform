package guc.bttsBtngan.user.services;

import guc.bttsBtngan.user.data.UserPostInteraction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserPostRepository extends MongoRepository<UserPostInteraction, String> {
    @Query("{'userId': ?0, 'postId': ?1}")
    UserPostInteraction findByUserIdAndPostId(String userId, String postId);

    @Query("{'userId': ?0}")
    UserPostInteraction findByUserId(String userId);

    @Query("{'postId': ?0}")
    UserPostInteraction findByPostId(String postId);
}
