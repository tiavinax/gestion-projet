#!/bin/bash

# ============================================
# SCRIPT POUR COMPILER LE FRAMEWORK EN .JAR
# ============================================

# Basculer ver le repertoire framework
cd framework

# Définition des variables
FRAMEWORK_NAME="framework"
VERSION="1.0"
JAR_NAME="${FRAMEWORK_NAME}-${VERSION}.jar"

SRC_DIR="src/main/java"
BUILD_DIR="build"
LIB_DIR="lib"
OUTPUT_DIR="dist"

# Fichiers de sortie
SERVLET_API_JAR="$LIB_DIR/servlet-api.jar"

# Couleurs pour l'affichage
RED='\033[0;31m'
GREEN='\033[0;32m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

echo -e "${BLUE}========================================${NC}"
echo -e "${BLUE}Compilation du Framework en .JAR${NC}"
echo -e "${BLUE}========================================${NC}"

# 1. Nettoyage des anciens builds
echo -e "${BLUE}[1/6] Nettoyage des anciens fichiers...${NC}"
rm -rf $BUILD_DIR
rm -rf $OUTPUT_DIR
mkdir -p $BUILD_DIR
mkdir -p $OUTPUT_DIR

# 2. Vérification des sources
echo -e "${BLUE}[2/6] Vérification des sources Java...${NC}"
if [ ! -d "$SRC_DIR" ]; then
    echo -e "${RED}Erreur: Dossier $SRC_DIR non trouvé !${NC}"
    exit 1
fi

# 3. Compilation des fichiers Java
echo -e "${BLUE}[3/6] Compilation des fichiers Java...${NC}"
find $SRC_DIR -name "*.java" > sources.txt

if [ ! -s sources.txt ]; then
    echo -e "${RED}Erreur: Aucun fichier .java trouvé dans $SRC_DIR !${NC}"
    rm sources.txt
    exit 1
fi

javac -cp "$SERVLET_API_JAR" -d $BUILD_DIR @sources.txt

if [ $? -ne 0 ]; then
    echo -e "${RED}Erreur lors de la compilation !${NC}"
    rm sources.txt
    exit 1
fi

rm sources.txt
echo -e "${GREEN}✓ Compilation réussie${NC}"

# 4. Création du fichier JAR
echo -e "${BLUE}[4/6] Création du fichier JAR...${NC}"
cd $BUILD_DIR || exit

# Créer le JAR avec les .class compilés
jar -cvf $JAR_NAME *

if [ $? -ne 0 ]; then
    echo -e "${RED}Erreur lors de la création du JAR !${NC}"
    cd ..
    exit 1
fi

mv $JAR_NAME ../$OUTPUT_DIR/
cd ..

echo -e "${GREEN}✓ JAR créé : $OUTPUT_DIR/$JAR_NAME${NC}"

# 5. Afficher le contenu du JAR (optionnel)
echo -e "${BLUE}[5/6] Contenu du JAR :${NC}"
jar -tf $OUTPUT_DIR/$JAR_NAME | head -20
echo "   ..."

# 6. Informations finales
echo -e "${BLUE}[6/6] Résumé :${NC}"
echo -e "${GREEN}========================================${NC}"
echo -e "${GREEN}✓ Framework compilé avec succès !${NC}"
echo -e "${GREEN}✓ Fichier JAR : $OUTPUT_DIR/$JAR_NAME${NC}"
echo -e "${GREEN}✓ Taille : $(du -h $OUTPUT_DIR/$JAR_NAME | cut -f1)${NC}"
echo -e "${GREEN}========================================${NC}"
echo ""
echo -e "${BLUE}Pour utiliser ce framework dans votre application de test :${NC}"
echo "1. Copier $OUTPUT_DIR/$JAR_NAME vers ../application-test/lib/"
echo "2. Ajouter le JAR dans le classpath de l'application test"
echo ""

cd ..