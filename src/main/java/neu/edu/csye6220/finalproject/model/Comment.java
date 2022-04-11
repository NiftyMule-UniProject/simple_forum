package neu.edu.csye6220.finalproject.model;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "comment")
public class Comment
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "post_id", nullable = false)
    private Long postId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "creation_time", nullable = false)
    private Timestamp creationTime;

    @Lob
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @ColumnDefault("0")
    @Column(name = "upvote")
    private Integer upvote;

    @Column(name = "refer_to_comment_id")
    private Long referToCommentId;

    @Column(name = "username", nullable = false)
    private String username;

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public Long getReferToCommentId()
    {
        return referToCommentId;
    }

    public void setReferToCommentId(Long referToCommentId)
    {
        this.referToCommentId = referToCommentId;
    }

    public Integer getUpvote()
    {
        return upvote;
    }

    public void setUpvote(Integer upvote)
    {
        this.upvote = upvote;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public Timestamp getCreationTime()
    {
        return creationTime;
    }

    public void setCreationTime(Timestamp creationTime)
    {
        this.creationTime = creationTime;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getPostId()
    {
        return postId;
    }

    public void setPostId(Long postId)
    {
        this.postId = postId;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }
}