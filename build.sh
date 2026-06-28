#!/bin/bash

# ============================================
# SCRIPT POUR COMPILER LE FRAMEWORK EN .JAR
# ============================================

# Basculer ver le repertoire framework
cd framework

APP_NAME="framework"
VERSION="1.0"
SRC_DIR="src/main/java"
WEB_DIR="src/main/webapp"
BUILD_DIR="build"
LIB_DIR="lib"
OUTPUT_DIR="dist"

# Couleurs
GREEN='\033[0;32m'
RED='\033[0;31m'
BLUE='\033[0;34m'
NC='\033[0m'

echo -e "${BLUE}========================================${NC}"
echo -e "${BLUE}Compilation Framework - Sprint 2${NC}"
echo -e "${BLUE}========================================${NC}"

# Nettoyage
rm -rf $BUILD_DIR $OUTPUT_DIR
mkdir -p $BUILD_DIR $OUTPUT_DIR

# Compilation
echo -e "${BLUE}[1/3] Compilation...${NC}"
find $SRC_DIR -name "*.java" > sources.txt

javac -cp "$LIB_DIR/servlet-api.jar" -d $BUILD_DIR @sources.txt

if [ $? -ne 0 ]; then
    echo -e "${RED}❌ Erreur de compilation${NC}"
    rm sources.txt
    exit 1
fi

rm sources.txt
echo -e "${GREEN}✓ Compilation réussie${NC}"

# Copier les fichiers web (JSP, etc.)
echo -e "${BLUE}[1.5/3] Copie des fichiers web...${NC}"
if [ -d "$WEB_DIR" ]; then
    cp -r $WEB_DIR/* $BUILD_DIR/
fi

# Création JAR
echo -e "${BLUE}[2/3] Création JAR...${NC}"
cd $BUILD_DIR
jar -cvf $APP_NAME-$VERSION.jar * > /dev/null
mv $APP_NAME-$VERSION.jar ../$OUTPUT_DIR/
cd ..

echo -e "${GREEN}✓ JAR créé: $OUTPUT_DIR/$APP_NAME-$VERSION.jar${NC}"

# Afficher contenu
echo -e "${BLUE}[3/3] Contenu du JAR:${NC}"
jar -tf $OUTPUT_DIR/$APP_NAME-$VERSION.jar | grep -E "\.class$" | head -10
echo "   ..."

echo -e "${GREEN}========================================${NC}"
echo -e "${GREEN}✅ Framework compilé avec succès !${NC}"
echo -e "${GREEN}📦 JAR: $OUTPUT_DIR/$APP_NAME-$VERSION.jar${NC}"
echo -e "${GREEN}========================================${NC}"
echo ""
echo -e "${BLUE}Pour utiliser ce framework dans votre application de test :${NC}"
echo "1. Copier $OUTPUT_DIR/$JAR_NAME vers ../application-test/lib/"
echo "2. Ajouter le JAR dans le classpath de l'application test"
echo ""

cd ..