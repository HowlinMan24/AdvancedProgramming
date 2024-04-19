package mk.kolokvium2.ex37;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


class Post {
    public String userName;
    public String postContent;
    //    public List<Comment> commentsList;
    public Map<String, TreeSet<Comment>> commentListMap;
    //TODO if it doesn't work try with Map

    public Post(String userName, String postContent) {
        this.userName = userName;
        this.postContent = postContent;
//        this.commentsList = new ArrayList<>();
        this.commentListMap = new HashMap<>();
    }

    public void addComment(String username, String commentId, String content, String replyToId) {
//        if (replyToId == null) {
//            commentsList.add(new Comment(username, commentId, content, null));
//        } else {
//            commentsList.stream().filter(x -> x.commentID.equals(replyToId)).forEach(Comment::incrementNumberReplies);
//        }
        commentListMap.computeIfAbsent(commentId, x -> new TreeSet<>(Comparator.comparing(Comment::getTotalLikes))).add(new Comment(username, commentId, content, replyToId));
    }

    public void likeComment(String commentID) {
        commentListMap.values().forEach(x -> x.stream().filter(z -> z.commentID.equals(commentID)).forEach(Comment::incrementLikes));
    }

    @Override
    public String toString() {
        StringBuilder st = new StringBuilder();
        st.append("Post: ").append(postContent).append("\n");
        st.append("Written by: ").append(userName).append("\n");
        st.append("Comments:").append("\n");
        commentListMap.entrySet().stream()
                .flatMap(x -> x.getValue().stream())
                .sorted(Comparator.comparing(Comment::getTotalLikes).reversed())
                .forEach(y -> {
                    if (y.replyToId == null) {
                        st.append("    ").append(y.a());
                    } else {
                        st.append(y.a());
                    }
                });
        return st.toString();
    }

}

class Comment {
    public String username;
    public String commentID;
    public String content;
    public String replyToId;
    public int numberReplies;
    public int totalLikes;

    public Comment(String username, String commentID, String content, String replyToId) {
        this.username = username;
        this.commentID = commentID;
        this.content = content;
        if (replyToId != null)
            this.replyToId = replyToId;
        else
            this.replyToId = "Kiro";
        this.numberReplies = 0;
        this.totalLikes = 0;
    }

    public void incrementNumberReplies() {
        this.numberReplies++;
    }

    public void incrementLikes() {
        this.totalLikes++;
    }

    public int getTotalLikes() {
        return totalLikes;
    }

    public String a() {
        return "        Comment: " + content + "\n" +
                "        Written by: " + username + "\n" +
                "        Likes: " + totalLikes + "\n";
    }

    @Override
    public String toString() {
        return "        Comment: " + content + "\n" +
                "        Written by: " + username + "\n" +
                "        Likes: " + totalLikes + "\n";
    }
}

public class PostTester {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String postAuthor = sc.nextLine();
        String postContent = sc.nextLine();

        Post p = new Post(postAuthor, postContent);

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] parts = line.split(";");
            String testCase = parts[0];

            if (testCase.equals("addComment")) {
                String author = parts[1];
                String id = parts[2];
                String content = parts[3];
                String replyToId = null;
                if (parts.length == 5) {
                    replyToId = parts[4];
                }
                p.addComment(author, id, content, replyToId);
            } else if (testCase.equals("likes")) { //likes;1;2;3;4;1;1;1;1;1 example
                for (int i = 1; i < parts.length; i++) {
                    p.likeComment(parts[i]);
                }
            } else {
                System.out.println(p);
            }

        }
    }
}

