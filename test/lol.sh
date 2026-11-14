#!/bin/bash
cd ..
mvn clean package -o
cp target/UnderCore-1.0.1.jar test/UnderCore-1.0.1.jar
cd test
java -jar UnderCore-1.0.1.jar
