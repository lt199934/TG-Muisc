# 基础镜像
FROM openjdk:8-jdk-alpine

# 指定工作目录
WORKDIR /app

# 将jar包添加到工作目录，比如target/
ADD target/TG-Music-0.0.1-SNAPSHOT.jar .

# 暴露端口
EXPOSE 8081

# 启动命令
ENTRYPOINT ["java","-jar","/app/TG-Music-0.0.1-SNAPSHOT.jar","--spring.profiles.active=prod"]