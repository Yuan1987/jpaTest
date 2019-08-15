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
  
docker-elk
#logstash 配置
input {
        tcp {
                port => 5000
                mode => "server"
                codec => json_lines
        }
}

## Add your filters / logstash plugins configuration here

output {
        elasticsearch {
                hosts => "elasticsearch:9200"
                user => "elastic"
                password => "tv4gVGllEQXIIgrA"
                index => "%{appname}-%{+YYYY.MM.dd}"
                #index => "%{[fields][appname]}-%{+YYYY.MM.dd}"
        }
        #stdout{codec => rubydebug}
}



1、curl -L -O https://artifacts.elastic.co/downloads/beats/filebeat/filebeat-7.2.1-x86_64.rpm
sudo rpm -vi filebeat-7.2.1-x86_64.rpm
2、/etc/filebeat/filebeat.yml
 output.elasticsearch:
  hosts: ["<es_url>"]
  username: "elastic"
  password: "<password>"
setup.kibana:
  host: "<kibana_url>"
3、sudo filebeat modules enable nginx 或者/etc/filebeat/modules.d/nginx.yml 

sudo filebeat setup
sudo service filebeat start

#filebeat 指定nginx 日志路径，默认 /var/log/nginx/*log
通过在modules.d/nginx.yml中指定变量设置
- module: nginx
  access:
    enabled: true
    var.paths: ["/path/to/log/nginx/access.log*"]
  error:
    enabled: true
    var.paths: ["/path/to/log/nginx/error.log*"]
    
或者通过命令 ./filebeat -e -M "nginx.access.var.paths=[/path/to/log/nginx/access.log*]" -M "nginx.error.var.paths=[/path/to/log/nginx/error.log*]"


#nginx log
log_format  main  '$remote_addr - $remote_user [$time_local] "$request  $request_body "'
                      '$status $body_bytes_sent "$http_referer" '
                      '"$http_user_agent" "$http_x_forwarded_for"  $http_X_Auth_Token';



