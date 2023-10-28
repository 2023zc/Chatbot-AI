#基本镜像
FROM openjdk:17
#作者
MAINTAINER zc
#配置
ENV PARAMS=""
#时区
ENV TZ=PRC
RUN ln -snf /usrr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
#添加应用
ADD /chatbot-api-interfaces/target/chatbot-api-interfaces-1.0-SNAPSHOT.jar /chatbot-api.jar
#执行镜像
#ENTRYPOINT ["sh","-c","java -jar $JAVA_OPTS /chatbot-api.jar $PARRAMS"]
CMD java -jar chatbot-api.jar





