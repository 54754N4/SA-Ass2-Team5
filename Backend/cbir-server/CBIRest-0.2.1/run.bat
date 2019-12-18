@echo off
@rem update location if you run on another laptop
set LOCATION=D:\Users\Satsana\Desktop\Workspaces\Java\Eclipse Workspace 2019\SA-Ass2-Team5\SA-Ass2-Team5\Backend\cbir-server\CBIRest-0.2.1

echo Starting CBIR REST API server..
java -jar "%LOCATION%\retrieval-0.2.1-SNAPSHOT.war" --spring.profiles.active=prod --retrieval.store.name=MEMORY
@pause