#!/bin/bash

# ============================================
# SCRIPT POUR COMPILER LE FRAMEWORK EN .JAR
# ============================================

# Basculer ver le repertoire framework
cd ../framework

APP_NAME="framework"
VERSION="1.0"
SRC_DIR="src/main/java"
BUILD_DIR="build"
LIB_DIR="lib"
OUTPUT_DIR="dist"

GREEN='\033[0;32m'
RED='\033[0;31m'
BLUE='\033[0;34m'
NC='\033[0m'

echo -e "${BLUE}========================================${NC}"
echo -e "${BLUE}Compilation Framework - Sprint 7${NC}"
echo -e "${BLUE}========================================${NC}"

rm -rf $BUILD_DIR $OUTPUT_DIR
mkdir -p $BUILD_DIR $OUTPUT_DIR

echo -e "${BLUE}[1/3] Compilation avec -parameters...${NC}"
find $SRC_DIR -name "*.java" > sources.txt

javac -parameters \
      -cp "$LIB_DIR/servlet-api.jar:$LIB_DIR/gson-2.10.1.jar:$LIB_DIR/postgresql-42.7.1.jar" \
      -d $BUILD_DIR @sources.txt

if [ $? -ne 0 ]; then
    echo -e "${RED}❌ Erreur de compilation${NC}"
    rm sources.txt
    exit 1
fi

rm sources.txt
echo -e "${GREEN}✓ Compilation reussie${NC}"

echo -e "${BLUE}[2/3] Creation JAR...${NC}"
cd $BUILD_DIR
jar -cvf $APP_NAME-$VERSION.jar * > /dev/null
mv $APP_NAME-$VERSION.jar ../$OUTPUT_DIR/
cd ..

echo -e "${GREEN}✓ JAR cree: $OUTPUT_DIR/$APP_NAME-$VERSION.jar${NC}"

echo -e "${GREEN}========================================${NC}"
echo -e "${GREEN}✅ Framework compile !${NC}"
echo -e "${GREEN}========================================${NC}"

cd ../script