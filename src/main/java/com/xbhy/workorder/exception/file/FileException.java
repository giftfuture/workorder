package com.xbhy.workorder.exception.file;


import com.xbhy.workorder.exception.BaseException;

/**
 * 文件信息异常类
 * 
 * @author
 */
public class FileException extends BaseException
{
    private static final long serialVersionUID = 1L;

    public FileException(Integer code, Object[] args)
    {
        super("file", code, args, null);
    }

}
