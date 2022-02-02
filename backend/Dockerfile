FROM maven:3.6.3-jdk-11 AS builder

COPY ./src/ /root/src
COPY ./pom.xml /root/
COPY ./checkstyle.xml /root/
WORKDIR /root
RUN mvn package
RUN java -Djarmode=layertools -jar /root/target/Assignment_1-0.0.1-SNAPSHOT.jar list
RUN java -Djarmode=layertools -jar /root/target/Assignment_1-0.0.1-SNAPSHOT.jar extract
RUN ls -l /root

FROM openjdk:11.0.6-jre

ENV TZ=UTC
ENV DB_IP=ec2-176-34-168-83.eu-west-1.compute.amazonaws.com
ENV DB_PORT=5432
ENV DB_USER=jokceulpfcfopy
ENV DB_PASSWORD=dc579b7d0a477832e8c0f4800fe9ef59a43f18d9214ed727ee039c1783d98d5d
ENV DB_DBNAME=d4rn5c60033058


COPY --from=builder /root/dependencies/ ./
COPY --from=builder /root/snapshot-dependencies/ ./

RUN sleep 10
COPY --from=builder /root/spring-boot-loader/ ./
COPY --from=builder /root/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher","-XX:+UseContainerSupport -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -XX:MaxRAMFraction=1 -Xms512m -Xmx512m -XX:+UseG1GC -XX:+UseSerialGC -Xss512k -XX:MaxRAM=72m"]