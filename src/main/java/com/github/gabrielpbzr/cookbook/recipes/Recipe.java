package com.github.gabrielpbzr.cookbook.recipes;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Recipe entity
 * @author gabriel
 */
@Entity
@Table(name = "recipes")
public class Recipe implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="title", length=255, nullable=false)
    private String title;
    @Column(name="content", length=1024)
    private String content;
    @Column(name = "uuid", length=36, unique=true, nullable=false)
    private UUID uuid;

    protected Recipe() {
        this.uuid = UUID.randomUUID();
    }
    
    public Recipe(String title, String content) {
        this();
        this.title = title;
        this.content = content;
    }

    public Recipe(String title, String content, UUID uuid) {
        this.title = title;
        this.content = content;
        this.uuid = uuid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.uuid);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Recipe other = (Recipe) obj;
        return Objects.equals(this.id, other.id) || Objects.equals(this.uuid, other.uuid);
    }
}
