package com.xbhy.workorder.exception.file;

/**
 * 文件名称超长限制异常类
 * 
 * @author ycp
 */
public class FileNameLengthLimitExceededException extends FileException
{
    private static final long serialVersionUID = 1L;

    public FileNameLengthLimitExceededException(int defaultFileNameLength)
    {
        super(6, new Object[] { defaultFileNameLength });
    }
}
