#!/bin/sh
#docker 镜像/容器名字
SERVER_NAME=jpa-test
BASE_PATH=docker/
cp target/JpaTest-0.1.jar $BASE_PATH
#容器id
CID=$(docker ps -a | grep "$SERVER_NAME" | awk '{print $1}')
#镜像id
IID=$(docker images | grep "$SERVER_NAME" | awk '{print $3}')
# 删除docker容器
if [ -n "$CID" ]; then
        echo "存在$SERVER_NAME容器，CID=$CID"
        docker stop $CID
        docker rm $CID
else
        echo "不存在$SERVER_NAME容器"
fi

# 删除docker镜像
if [ -n "$IID" ]; then
        echo "存在无用镜像，IID=$IID"
        docker rmi $IID
else
        echo "不存在已废弃镜像"
fi
cd $BASE_PATH
docker build -t  $SERVER_NAME .
# 运行docker容器
docker run --name $SERVER_NAME -e SPRING_PROFILES_ACTIVE="dev" -d -p 8081:8081 $SERVER_NAME
echo "$SERVER_NAME容器创建完成"