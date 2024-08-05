## 该镜像需要依赖的基础镜像
#FROM openjdk:17-jdk-alpine
## 将当前目录下的jar包复制到docker容器的/目录下
#ADD mall-admin-1.0-SNAPSHOT.jar /mall-admin-1.0-SNAPSHOT.jar
## 声明服务运行在8080端口
#EXPOSE 8080
## 指定docker容器启动时运行jar包
#ENTRYPOINT ["java", "-jar","/mall-admin-1.0-SNAPSHOT.jar"]
## 指定维护者的名字
#MAINTAINER macro

# 使用官方 OpenJDK 17 JDK 作为基础镜像
FROM openjdk:17

# 创建一个目录来存放应用的 jar 文件
VOLUME /tmp

# 定义 jar 文件的路径，假设它在 target 目录下
ARG JAR_FILE=target/*.jar

# 将 jar 文件复制到容器内
COPY ${JAR_FILE} app.jar

# 运行 jar 文件
ENTRYPOINT ["java","-jar","/app.jar"]