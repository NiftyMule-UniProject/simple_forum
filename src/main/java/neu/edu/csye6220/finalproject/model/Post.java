package neu.edu.csye6220.finalproject.model;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "post")
public class Post
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "type_id", nullable = false)
    private Long typeId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "creation_time", nullable = false)
    private Timestamp creationTime;

    @Column(name = "last_comment_time")
    private Timestamp lastCommentTime;

    @Column(name = "upvote", nullable = false)
    @ColumnDefault("0")
    private Integer upvote;

    @Lob
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "title")
    private String title;

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public void setCreationTime(Timestamp creationTime)
    {
        this.creationTime = creationTime;
    }

    public Integer getUpvote()
    {
        return upvote;
    }

    public void setUpvote(Integer upvote)
    {
        this.upvote = upvote;
    }

    public Timestamp getLastCommentTime()
    {
        return lastCommentTime;
    }

    public void setLastCommentTime(Timestamp lastCommentTime)
    {
        this.lastCommentTime = lastCommentTime;
    }

    public Timestamp getCreationTime()
    {
        return creationTime;
    }

    public Long getUserId()
    {
        return userId;
    }

    public Long getTypeId()
    {
        return typeId;
    }

    public void setTypeId(Long typeId)
    {
        this.typeId = typeId;
    }

    public Long getId()
    {
        return id;
    }

}