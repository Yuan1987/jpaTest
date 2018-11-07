#设置主从
slave of ip port

#master  设置密码
config set requirepass 123456

#如果 master 有密码 从应设置 master的密码
config set masterauth 123456

#状态查看
info


