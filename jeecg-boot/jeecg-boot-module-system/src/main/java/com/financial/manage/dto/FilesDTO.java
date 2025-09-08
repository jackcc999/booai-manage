package com.financial.manage.dto;

import lombok.Data;

@Data
public class FilesDTO
{
    private String id;
    private String filename;
    private String url;
    private String absoluteFile;
    private String contentType;
    private String size;
    private int canView;
    private String createTime;

    public static FilesDTO empty()
    {
        return new FilesDTO();
    }
}
