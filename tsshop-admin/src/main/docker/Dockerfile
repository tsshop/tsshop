#基础镜像使用java
FROM openjdk:8
# 作者
MAINTAINER fengyun

ARG envType

# 将jar包添加到容器中并更名为app.jar
COPY *.jar app.jar

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo 'Asia/Shanghai' >/etc/timezone
RUN ls -al
RUN echo ${envType}

ENV spring.profiles.active=${envType}

EXPOSE 9071
# 运行jar包
ENTRYPOINT ["java", "-jar", "/app.jar"]
