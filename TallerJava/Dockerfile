FROM quay.io/wildfly/wildfly:latest-jdk21
ADD config_docker.cli /opt/jboss/wildfly/bin/config.cli
ADD mariadb-java-client-3.3.3.jar /opt/jboss/wildfly/bin/
COPY target/TallerJava.war /opt/jboss/wildfly/standalone/deployments/
CMD ["/opt/jboss/wildfly/bin/jboss-cli.sh --file=/opt/jboss/wildfly/bin/config.cli"]
