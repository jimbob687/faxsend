#!/bin/bash

mvn clean package

#java -Xmx400M -cp "libs/*:target/faxprocessing-1.0-SNAPSHOT.jar" com.tannfe.faxprocess.App $1
#java -Xmx400M -cp "libs/*:target/faxprocessing-1.0-SNAPSHOT.jar" com.tannfe.faxprocess.TiffSplitTIFF $1
#java -Xmx400M -cp "libs/*:target/rendertif-1.0-SNAPSHOT.jar" com.tannfe.rendertif.RenderFax 

#pkill -f com.tannfe.freeswitchesl.MyESLTest

java -Xmx400M -Dlog4j.configuration=file:./libs/log4j.properties -cp "libs/config.properties:../libs/*:target/freeswitchesl-1.0-SNAPSHOT.jar" com.tannfe.sendfaxlib.MyESLTest 

#java -Xmx400M -Dlog4j.configuration=file:./libs/log4j.properties -cp "libs/config.properties:../libs/*:target/freeswitchesl-1.0-SNAPSHOT.jar" com.tannfe.freeswitchesl.ProcessMessages
