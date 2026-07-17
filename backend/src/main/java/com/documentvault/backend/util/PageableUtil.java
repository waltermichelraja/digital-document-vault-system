package com.documentvault.backend.util;

import java.util.Set;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public final class PageableUtil{
    private PageableUtil(){}

    public static Pageable createPageable(int page,int size,String sort,Set<String> allowedSortFields){
        String[] sortParts=sort.split(",");
        String sortField=sortParts[0];
        if(!allowedSortFields.contains(sortField)){
            throw new IllegalArgumentException("invalid sort field.");
        }
        if(sortParts.length>1){
            if(!sortParts[1].equalsIgnoreCase("asc")
                    && !sortParts[1].equalsIgnoreCase("desc")){
                throw new IllegalArgumentException("invalid sort direction.");
            }
        }
        Sort.Direction direction=sortParts.length>1 && sortParts[1].equalsIgnoreCase("asc")?Sort.Direction.ASC:Sort.Direction.DESC;
        return PageRequest.of(page,size,Sort.by(direction,sortField));
    }
}