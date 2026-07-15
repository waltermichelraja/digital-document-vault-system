package com.documentvault.backend.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name="documents")
@Getter
@Setter
@NoArgsConstructor
public class Document{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String documentTitle;

    @Column(nullable=false)
    private String originalFileName;

    @Column(nullable=false,unique=true)
    private String storedFileName;

    @Column(nullable=false)
    private String contentType;

    @Column(nullable=false)
    private Long fileSize;

    @Column(nullable=false)
    private String filePath;

    @Column(nullable=false)
    private String category;

    @Column(nullable=false)
    private LocalDateTime uploadedAt;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="owner_id")
    private User owner;

    @PrePersist
    public void prePersist(){
        uploadedAt=LocalDateTime.now();
    }
}