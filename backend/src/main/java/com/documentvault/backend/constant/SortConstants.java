package com.documentvault.backend.constant;

import java.util.Set;

public final class SortConstants{
    public static final Set<String> DOCUMENT_SORT_FIELDS=Set.of(
            "documentTitle",
            "uploadedAt",
            "category",
            "fileSize"
    );

    private SortConstants(){}
}