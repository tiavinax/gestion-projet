#!/bin/bash

APP_NAME="app-test"
SRC_DIR="src/main/java"
WEB_DIR="src/main/webapp"
BUILD_DIR="build"
LIB_DIR="lib"
TOMCAT_WEBAPPS="/home/tiavina-anjaranomena/tomcat/tomcat/webapps"

cd app-test

# S'assurer que le framework JAR existe dans lib
if [ ! -f "$LIB_DIR/framework-1.0.jar" ]; then
    echo "❌ Erreur: framework-1.0.jar non trouvé dans $LIB_DIR"
    echo "   Copiez-le depuis ../framework/dist/"
    exit 1
fi

# Nettoyage
rm -rf $BUILD_DIR
mkdir -p $BUILD_DIR/WEB-INF/classes
mkdir -p $BUILD_DIR/WEB-INF/lib

# Compilation avec le framework JAR et servlet-api
find $SRC_DIR -name "*.java" > sources.txt

javac -cp "$LIB_DIR/framework-1.0.jar:$LIB_DIR/servlet-api.jar" \
      -d $BUILD_DIR/WEB-INF/classes @sources.txt

if [ $? -ne 0 ]; then
    echo "❌ Erreur de compilation"
    rm sources.txt
    exit 1
fi

rm sources.txt

# Copier les fichiers web et les librairies
cp -r $WEB_DIR/* $BUILD_DIR/
cp $LIB_DIR/*.jar $BUILD_DIR/WEB-INF/lib/

# Créer le WAR
cd $BUILD_DIR
jar -cvf $APP_NAME.war *
cd ..

# Déployer vers Tomcat
cp -f $BUILD_DIR/$APP_NAME.war $TOMCAT_WEBAPPS/

echo "✅ App test déployée vers Tomcat"
echo "🌐 Accédez à: http://localhost:8080/app-test/test"

cd ..