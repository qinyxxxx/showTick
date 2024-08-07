# 使用官方 OpenJDK 17 JDK 作为基础镜像
FROM openjdk:17-jdk-slim

# 创建一个目录来存放应用的 jar 文件
VOLUME /tmp

# 定义 jar 文件的路径，假设它在 target 目录下
ARG JAR_FILE=target/*.jar

# 将 jar 文件复制到容器内
COPY ${JAR_FILE} app.jar

# 运行 jar 文件
ENTRYPOINT ["java","-jar","/app.jar"]