package neu.edu.csye6220.finalproject.model;

import javax.persistence.*;

@Entity
@Table(name = "post_type")
public class PostType
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "type_name", nullable = false, unique = true)
    private String typeName;

    public String getTypeName()
    {
        return typeName;
    }

    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
    }

    public Long getId()
    {
        return id;
    }

}