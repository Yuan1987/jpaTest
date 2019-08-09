docker run -dit --name elasticsearch  --hostname  elasticsearch-host  -v /data:/data -p 9200:9200 -p 9300:9300 -v /opt/es/es.yml:/usr/share/elasticsearch/config/elasticsearch.yml elasticsearch:latest
#docker run -dit --name kibana  --hostname  kibana-host  -v /data:/data -v /opt/es/kibana.yml:/etc/kibana/kibana.yml -p 5601:5601 kibana:latest
docker run -itd --name kibana -p 5601:5601 --link elasticsearch -e ELASTICSEARCH_URL=http://elasticsearch:9200 kibana:latest

docker run -itd --name logstash -p 4560:4560 --link elasticsearch -v /opt/logstash/pipeline/:/usr/share/logstash/pipeline/ -v /opt/logstash/config/:/usr/share/logstash/config/ logstash


#kibana 本身没有密码权限控制 用nginx做
1、
server {
  listen       80;
  server_name localhost;
  location / {
     auth_basic "secret";
     auth_basic_user_file /usr/local/nginx/db/passwd.db;
     proxy_pass http://localhost:5601;
     proxy_set_header Host $host:5601;
     proxy_set_header X-Real-IP $remote_addr;
     proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
     proxy_set_header Via "nginx";
  }
}
2、mkdir /usr/local/nginx/db

3、安装 yum install -y httpd
4、htpasswd -c /usr/local/nginx/db/passwd.db user
5、htpasswd -b /usr/local/nginx/db/passwd.db admin tv4gVGllEQXIIgrA


新建logstash文件夹，并在其下新建config、pipeline两个文件夹

其中config文件夹下有文件logstash.yml、pipelines.yml。内容分别为：

logstash.yml：

config:
  reload:
    automatic: true
    interval: 3s
xpack:
  management.enabled: false
  monitoring.enabled: false
  
pipelines.yml：

- pipeline.id: test
  path.config: "/usr/share/logstash/pipeline/logstash-test.conf"
然后pipeline文件夹下有文件logstash-test.conf，内容为：

input {
    file {
        path => ["/usr/share/logstash/pipeline/logs/test.log"]
        start_position => "beginning"
    }
}

filter {
  mutate {
    gsub => ["message", "\r", ""]
  }
  dissect {
    mapping => {"message" => "%{date} %{+date} [%{task} %{+task}] [%{type}] %{class} - %{info}"}
  }
}

output {
    elasticsearch { hosts => ["127.0.0.1:9200"] }
    stdout { codec => rubydebug }
}

或者

input {
    tcp {
        port => 4560   //从本地的4560端口取日志。这里笔者将Logstash部署在了虚拟机192.168.226.132上，所以取的是本地地址。
        codec => json_lines  //需要安装logstash-codec-json_lines插件
    }
}
output{
  elasticsearch { hosts => ["localhost:9200"] }  //输出到ElasticSearch
  stdout { codec => rubydebug }  //若不需要在控制台中输出，此行可以删除
}





