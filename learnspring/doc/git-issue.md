## error setting certificate verify locations: CAfile: F:/gitt/Git/mingw64/ssl/certs/ca-bundle.crt

git config --system http.sslcainfo "D:\git\Git\mingw64\ssl\certs\ca-bundle.crt

## git配置记住用户名和密码

```
git config --global credential.helper store
git pull
```
这时会让你输入用户名的密码，在你输入了正确的用户名和密码后，下次再运行git pull或者git push的时候就会发现再也不用输入用户名和密码

## Git出现HttpRequestException encountered错误
使用Git下载或者更新代码时出现fatal：HttpRequestException encountered提示信息，但是它又不会影响Git的正常使用
出现该提示信息的主要原因是Github禁用了TLS v1.0 and v1.1这种弱加密标准，此时需要手动更新Windows的git凭证管理器，更新方式很简单，在网站下载Git在Window上的凭证管理器并默认安装



