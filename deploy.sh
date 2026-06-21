#!/bin/bash

cd app-test

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
echo -e "${BLUE}Déploiement App Test - Sprint 1${NC}"
echo -e "${BLUE}========================================${NC}"

# Vérifier le framework JAR
if [ ! -f "$LIB_DIR/framework-1.0.jar" ]; then
    echo -e "${RED}❌ Erreur: framework-1.0.jar non trouvé dans $LIB_DIR${NC}"
    echo "   Copiez-le depuis ../framework/dist/"
    exit 1
fi

# Nettoyage
echo -e "${BLUE}[1/5] Nettoyage...${NC}"
rm -rf $BUILD_DIR
mkdir -p $BUILD_DIR/WEB-INF/classes
mkdir -p $BUILD_DIR/WEB-INF/lib

# Compilation
echo -e "${BLUE}[2/5] Compilation des classes...${NC}"
find $SRC_DIR -name "*.java" > sources.txt

# Compiler avec les bons packages
javac -cp "$LIB_DIR/framework-1.0.jar:$LIB_DIR/servlet-api.jar" \
      -d $BUILD_DIR/WEB-INF/classes @sources.txt

if [ $? -ne 0 ]; then
    echo -e "${RED}❌ Erreur de compilation${NC}"
    cat sources.txt
    rm sources.txt
    exit 1
fi

rm sources.txt
echo -e "${GREEN}✓ Compilation réussie${NC}"

# Afficher les classes compilées
echo -e "${BLUE}📁 Classes compilées :${NC}"
find $BUILD_DIR/WEB-INF/classes -name "*.class" | head -10

# Copier les fichiers web
echo -e "${BLUE}[3/5] Copie des fichiers web...${NC}"
cp -r $WEB_DIR/* $BUILD_DIR/

# Déplacer web.xml dans WEB-INF s'il est à la racine
if [ -f "$BUILD_DIR/web.xml" ]; then
    echo -e "${BLUE}   Déplacement de web.xml dans WEB-INF/${NC}"
    mv $BUILD_DIR/web.xml $BUILD_DIR/WEB-INF/
fi

# Copier les librairies
echo -e "${BLUE}[4/5] Copie des librairies...${NC}"
cp $LIB_DIR/*.jar $BUILD_DIR/WEB-INF/lib/

# Créer le WAR
echo -e "${BLUE}[5/5] Création du WAR...${NC}"
cd $BUILD_DIR
jar -cvf $APP_NAME.war * > /dev/null
cd ..

echo -e "${GREEN}✓ WAR créé: $BUILD_DIR/$APP_NAME.war${NC}"

# Déployer vers Tomcat
cp -f $BUILD_DIR/$APP_NAME.war $TOMCAT_WEBAPPS/

echo -e "${GREEN}========================================${NC}"
echo -e "${GREEN}✅ App test déployée !${NC}"
echo -e "${GREEN}🌐 http://localhost:8080/app-test/${NC}"
echo -e "${GREEN}========================================${NC}"

cd ..