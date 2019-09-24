##### 1.  File.renameTo()与 FileChannel.transferTo()的区别

FileChannel.transferTo() 实际上将FileChannel（例如文件）的内容（即字节）复制到另一个位置。         
File.renameTo（）更改文件的名称。如果目标文件名在另一个文件系统上，它可能会复制该文件的内容，但这取决于平台

#####  2.  通过零拷贝实现有效数据传输
>https://www.ibm.com/developerworks/cn/java/j-zerocopy/#artdownload


