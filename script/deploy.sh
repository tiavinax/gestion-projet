#!/bin/bash

cd ../app-test

APP_NAME="app-test"
SRC_DIR="src/main/java"
WEB_DIR="src/main/webapp"
BUILD_DIR="build"
LIB_DIR="lib"
TOMCAT_WEBAPPS="/home/tiavina-anjaranomena/tomcat/tomcat/webapps"

GREEN='\033[0;32m'
RED='\033[0;31m'
BLUE='\033[0;34m'
NC='\033[0m'

echo -e "${BLUE}========================================${NC}"
echo -e "${BLUE}Deploiement App Test - Sprint 7${NC}"
echo -e "${BLUE}========================================${NC}"

# Vérifier le framework JAR
if [ ! -f "$LIB_DIR/framework-1.0.jar" ]; then
    echo -e "${RED}❌ framework-1.0.jar non trouve${NC}"
    exit 1
fi

# Nettoyage
echo -e "${BLUE}[1/5] Nettoyage...${NC}"
rm -rf $BUILD_DIR
mkdir -p $BUILD_DIR/WEB-INF/classes
mkdir -p $BUILD_DIR/WEB-INF/lib

# Compilation avec -parameters (TRES IMPORTANT)
echo -e "${BLUE}[2/5] Compilation avec -parameters...${NC}"
find $SRC_DIR -name "*.java" > sources.txt

javac -parameters \
      -cp "$LIB_DIR/framework-1.0.jar:$LIB_DIR/servlet-api.jar:$LIB_DIR/gson-2.10.1.jar" \
      -d $BUILD_DIR/WEB-INF/classes @sources.txt

if [ $? -ne 0 ]; then
    echo -e "${RED}❌ Erreur de compilation${NC}"
    rm sources.txt
    exit 1
fi

rm sources.txt
echo -e "${GREEN}✓ Compilation reussie avec -parameters${NC}"

# Copier les fichiers web
echo -e "${BLUE}[3/5] Copie des fichiers web...${NC}"
cp -r $WEB_DIR/* $BUILD_DIR/

if [ -f "$BUILD_DIR/web.xml" ]; then
    mv $BUILD_DIR/web.xml $BUILD_DIR/WEB-INF/
fi

# Copier les librairies
echo -e "${BLUE}[4/5] Copie des librairies...${NC}"
cp $LIB_DIR/framework-1.0.jar $BUILD_DIR/WEB-INF/lib/
cp $LIB_DIR/gson-2.10.1.jar $BUILD_DIR/WEB-INF/lib/
cp $LIB_DIR/servlet-api.jar $BUILD_DIR/WEB-INF/lib/

# Créer le WAR
echo -e "${BLUE}[5/5] Creation du WAR...${NC}"
cd $BUILD_DIR
jar -cvf $APP_NAME.war *
cd ..

# Déployer
cp -f $BUILD_DIR/$APP_NAME.war $TOMCAT_WEBAPPS/

echo -e "${GREEN}========================================${NC}"
echo -e "${GREEN}✅ App deployee !${NC}"
echo -e "${GREEN}🌐 http://localhost:8080/app-test/${NC}"
echo -e "${GREEN}========================================${NC}"

cd ../script