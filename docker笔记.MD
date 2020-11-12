#systemctl start docker 

#查看容器
docker ps -a

#删除容器
docker rm 

#docker start xxx

#镜像
docker images

docker rmi



#mysql
docker run --name mysql5.7 -v /alidata1/mysql5.7/data:/var/lib/mysql -p 3308:3306 -e MYSQL_ROOT_PASSWORD=tkaZtvF8jjXo83xqdAJDu0Gj1KEkGWbh -d registry.cn-hangzhou.aliyuncs.com/hhzz/mysql

docker run -p 3307:3306 --name mysql8 \
-v /alidata1/mysql8/logs:/logs  \
-v /alidata1/mysql8/my.cnf:/etc/mysql/conf.d   \
-v /alidata1/mysql8/data:/var/lib/mysql  \
-e MYSQL_ROOT_PASSWORD=tkaZtvF8jjXo83xqdAJDu0Gj1KEkGWbh -d mysql

docker run  -d --name mysql -v /aldata1/mysql8/data:/var/lib/mysql -v /etc/mysql/my.cnf:/etc/mysql/my.cnf -p 3306:3306 -e MYSQL_ROOT_PASSWORD=123456 mysql:tag

docker run -it --name jpa-test -p 8081:8081 -d jpa-test
docker run -itd -p 8080:8080 -p 50000:50000 --name jenkins --privileged=true \
-v /opt/jenkins:/var/jenkins_home \
-v /var/run/docker.sock:/var/run/docker.sock \
-v /usr/bin/docker:/usr/bin/docker \
-v /usr/lib64/libltdl.so.7:/usr/lib/x86_64-linux-gnu/libltdl.so.7  jenkins
sudo chown -R 1000:1000 jenkins
sudo chown -R 1000:1000 /var/run/docker.sock

#git ssh key
ssh-keygen -t rsa -C "tony@rhmg.cx"

#maven 指定子项目
-pl medical-user -am clean install

docker logs xxx


#进入容器内部
docker exec -it xx  /bin/bash


systemctl daemon-reload


#centos7 firewalld 

firewall-cmd --zone=public --add-port=80/tcp --permanent

firewall-cmd --list-ports

#fastdfs
docker run -d -p 22122:22122 --name tracker --net=host morunchang/fastdfs sh tracker.sh
docker run -d --name storage --net=host -e TRACKER_IP=192.168.23.14:22122 -v /fastdfsDocker:/data/fast_data -e GROUP_NAME=group1 morunchang/fastdfs sh storage.sh


docker run -itd -p 9000:9000 --name portainer portainer/portainer 

docker run -itd --name spark -p 8088:8088 -p 8042:8042 -p 4040:4040 -h sandbox sequenceiq/spark:1.6.0 bash


#docker export /import (导出容器，但导入的是image，需要带命令才来启动)
docker export -o jap-test.tar jpa-test

#docker save/load (针对image的备份)

#zookeeper
docker run -d --name zookeeper -p 2181:2181 -t wurstmeister/zookeeper

#kafka
	docker run  --name kafka \
	-p 9092:9092 \
	-e KAFKA_BROKER_ID=0 \
	-e KAFKA_ZOOKEEPER_CONNECT=192.168.50.155:2181 \
	-e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://192.168.50.155:9092 \
	-e KAFKA_LISTENERS=PLAINTEXT://0.0.0.0:9092 -t wurstmeister/kafka

	
	
	docker run -p 2181:2181 -p 9092:9092 \
    --env ADVERTISED_HOST=`boot2docker ip` \
    --env ADVERTISED_PORT=9092 \
    --env CONSUMER_THREADS=1 \
    --env TOPICS=my-topic,some-other-topic \
    --env ZK_CONNECT=kafka7zookeeper:2181/root/path \
    --env GROUP_ID=mymirror \
    spotify/kafkaproxy
    
    docker run --name kafka -d -p 2181:2181 -p 9092:9092 \
    --env ADVERTISED_HOST=211.149.188.241 \
    --env ADVERTISED_PORT=9092 \
    --env CONSUMER_THREADS=1 \
    --env TOPICS=my-topic,some-other-topic \
    --env ZK_CONNECT=kafka7zookeeper:2181/root/path \
    --env GROUP_ID=mymirror \
    spotify/kafkaproxy
    
    
docker run -p 5672:5672 -p 15672:15672 --name rabbitmq -d rabbitmq:3.8.7-management
#guest guest

docker run -p 27017:27017 --name mongo -v /mydata/mongo/db:/data/db -d mongo:4.2.5
       
