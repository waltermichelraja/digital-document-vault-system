package com.documentvault.backend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="documents")
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

    public Document(){}

    
}