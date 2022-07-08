package com.xbhy.workorder.exception.file;

/**
 * 文件名大小限制异常类
 * 
 * @author ycp
 */
public class FileSizeLimitExceededException extends FileException
{
    private static final long serialVersionUID = 1L;

    public FileSizeLimitExceededException(long defaultMaxSize)
    {
        super(7, new Object[] { defaultMaxSize });
    }
}
