# Notification System
___
## 1) How To Create a Notification ?

##  - run CreateNotificationCommand.execute(HashMap<String, Object> map)
### - Inputs : 
#### a) String type : 
#####    Here is the possible types of a notification and their description :
###### 1) "comment" : You have a new comment !
###### 2) "message" : You have a new message !
###### 3) "tagged" : You are tagged in a new post !
###### 4) "joinGroup" : Say Hi to your new mates
###### 5) "changeAdmin" : New Admin to the group
###### 6) "likePhoto" : You have a new like on your photo
###### 7) "commentPhoto" : You have a new comment on your photo
###### 8) "newFollower" : You got a new follower

#### b) Object userIDs :
##### Here is an Example :
###### ArrayList\<String> userIDs = new ArrayList\<String>();
###### userIDs.add("ID1");
###### userIDs.add("ID2");
###### userIDs.add("ID3");
###### ......etc.
\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
## 2) How To Update a Notification ?

##  - run UpdateNotificationCommand.execute(HashMap<String, Object> map)
### - Inputs :
### 1) String notificationID :
##### Here is an Example :
###### String notificationID = "notificationID1";
### 2) Notifications :
#### a) String type :
#####    Here is the possible types of a notification and their description :
###### 1) "comment" : You have a new comment !
###### 2) "message" : You have a new message !
###### 3) "tagged" : You are tagged in a new post !
###### 4) "joinGroup" : Say Hi to your new mates
###### 5) "changeAdmin" : New Admin to the group
###### 6) "likePhoto" : You have a new like on your photo
###### 7) "commentPhoto" : You have a new comment on your photo
###### 8) "newFollower" : You got a new follower

#### b) Object userIDs :
##### Here is an Example :
###### ArrayList\<String> userIDs = new ArrayList\<String>();
###### userIDs.add("ID1");
###### userIDs.add("ID2");
###### userIDs.add("ID3");
###### ......etc.
\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
## 3) How To Delete a Notification ?

##  - run DeleteNotificationCommand.execute(HashMap<String, Object> map)
### - Inputs :
#### a) String notificationID :
#####   Here is an Example :
###### String notificationID = "notificationID1";

#### b) String userID :
##### Here is an Example :
###### String userID = "userID1";
\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
## 4) How To Get a Notification ?

##  - run GetNotificationCommand.execute(HashMap<String, Object> map)
### - Input :

#### a) String userID :
##### Here is an Example :
###### String userID = "userID1";

\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\