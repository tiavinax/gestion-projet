#!/bin/bash

# Basculer ver le repertoire framework
cd framework

# Script pour installer le framework JAR dans l'application de test

FRAMEWORK_JAR="dist/framework-1.0.jar"
TEST_APP_DIR="../app-test"  # Chemin vers votre app de test
TEST_APP_LIB="$TEST_APP_DIR/lib"

echo "📦 Installation du framework vers l'application de test..."

# Vérifier si le JAR existe
if [ ! -f "$FRAMEWORK_JAR" ]; then
    echo "❌ Erreur: $FRAMEWORK_JAR non trouvé. Lancez d'abord ./deploy.sh"
    exit 1
fi

# Créer le dossier lib si nécessaire
mkdir -p $TEST_APP_LIB

# Copier le JAR
cp $FRAMEWORK_JAR $TEST_APP_LIB/

echo "✅ Framework installé dans $TEST_APP_LIB/"
echo "📋 Votre application de test peut maintenant utiliser:"
echo "   import com.votrepackage.*;"

cd ..