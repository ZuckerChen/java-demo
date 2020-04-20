package algorithm.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @author zucker
 * @description
 * @date: 2020/4/13 11:09 AM
 */
public class Twitter {
    /**
     * userId-tweet
     */
    Map<Integer, Twitt> twitter;

    /**
     * userId-followers
     */
    Map<Integer, HashSet<Integer>> followers;

    Queue<Twitt> maxHeap;

    int timestamp;

    class Twitt {
        int tweetId;
        long timestamp;
        Twitt next;

        public Twitt(int tweetId, long timestamp) {
            this.tweetId = tweetId;
            this.timestamp = timestamp;

        }
    }

    /**
     * Initialize your data structure here.
     */
    public Twitter() {
        twitter = new HashMap();
        followers = new HashMap<>();
        timestamp = 0;
        maxHeap = new PriorityQueue<>((o1, o2) -> (int) (o2.timestamp - o1.timestamp));
    }

    /**
     * Compose a new tweet.
     */
    public void postTweet(int userId, int tweetId) {
        timestamp++;
        Twitt twitt = twitter.get(userId);
        if (null == twitt) {
            twitter.put(userId, new Twitt(tweetId, timestamp));
        } else {
            Twitt head_twitt = new Twitt(tweetId, timestamp);
            head_twitt.next = twitt;
            twitter.put(userId, head_twitt);
        }
    }

    /**
     * Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
     */
    public List<Integer> getNewsFeed(int userId) {
        maxHeap.clear();

        HashSet<Integer> followerList = followers.get(userId);

        Twitt twitt = twitter.get(userId);
        if (null != twitt) {
            maxHeap.offer(twitt);
        }

        if (null != followerList && !followerList.isEmpty()) {
            for (Integer followee : followerList) {
                if (twitter.containsKey(followee)) {
                    maxHeap.offer(twitter.get(followee));
                }
            }
        }

        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            if (null != maxHeap && !maxHeap.isEmpty()) {
                Twitt head = maxHeap.poll();
                res.add(head.tweetId);
                if (null != head.next) {
                    maxHeap.offer(head.next);
                }
            } else {
                break;
            }
        }

        return res;
    }

    /**
     * Follower follows a followee. If the operation is invalid, it should be a no-op.
     */
    public void follow(int followerId, int followeeId) {
        if (followeeId == followerId) {
            return;
        }
        HashSet<Integer> followerList = followers.get(followerId);
        if (null == followerList || followerList.isEmpty()) {
            HashSet<Integer> follower = new HashSet<>();
            follower.add(followeeId);
            followers.put(followerId, follower);
        } else {
            followerList.add(followeeId);
        }
    }

    /**
     * Follower unfollows a followee. If the operation is invalid, it should be a no-op.
     */
    public void unfollow(int followerId, int followeeId) {
        if (followeeId == followerId) {
            return;
        }

        HashSet<Integer> followerList = followers.get(followerId);
        if (null == followerList || followerList.isEmpty()) {
            return;
        }

        if (followerList.contains(followeeId)) {
            followerList.remove(followeeId);
        }
    }

    public static void main(String[] args) {

        Twitter twitter = new Twitter();

// 用户1发送了一条新推文 (用户id = 1, 推文id = 5).
//        twitter.postTweet(1, 001);
//        twitter.postTweet(1, 002);
//        twitter.postTweet(1, 003);
//        twitter.postTweet(1, 004);
//        twitter.postTweet(1, 005);
//        twitter.postTweet(2, 006);
//        twitter.postTweet(2, 007);
//        twitter.postTweet(2, 112);
//        twitter.postTweet(2, 113);

// 用户1的获取推文应当返回一个列表，其中包含一个id为5的推文.
//        twitter.getNewsFeed(1);

// 用户1关注了用户2.
//        twitter.follow(1, 2);

// 用户2发送了一个新推文 (推文id = 6).
//        twitter.postTweet(2, 6);

// 用户1的获取推文应当返回一个列表，其中包含两个推文，id分别为 -> [6, 5].
// 推文id6应当在推文id5之前，因为它是在5之后发送的.
        twitter.getNewsFeed(1);

// 用户1取消关注了用户2.
        twitter.unfollow(1, 2);

// 用户1的获取推文应当返回一个列表，其中包含一个id为5的推文.
// 因为用户1已经不再关注用户2.
        twitter.getNewsFeed(1);

    }
}
