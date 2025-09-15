#!/usr/bin/env bash
set -euo pipefail

# Generate development TLS keystore for Artemis Financial
# This script creates a PKCS12 keystore with 4096-bit RSA keys for local development

KEYSTORE="src/main/resources/keystore.p12"
PASS="${PASS:-changeit}"

echo "🔐 Generating development TLS keystore for Artemis Financial..."

# Create resources directory if it doesn't exist
mkdir -p src/main/resources

# Remove existing keystore if present
if [ -f "$KEYSTORE" ]; then
    echo "⚠️  Removing existing keystore..."
    rm -f "$KEYSTORE"
fi

# Generate new PKCS12 keystore with 4096-bit RSA key
echo "🔑 Creating PKCS12 keystore with 4096-bit RSA key..."
keytool -genkeypair \
  -alias artemis-dev \
  -keyalg RSA \
  -keysize 4096 \
  -sigalg SHA256withRSA \
  -dname "CN=localhost, OU=Engineering, O=Artemis Financial, L=Atlanta, S=GA, C=US" \
  -validity 365 \
  -storetype PKCS12 \
  -storepass "$PASS" \
  -keypass "$PASS" \
  -keystore "$KEYSTORE"

echo "✅ Development keystore created successfully!"
echo "📍 Location: $KEYSTORE"
echo "🔑 Password: $PASS"
echo "📅 Valid for: 365 days"
echo ""

# Verify keystore
echo "🔍 Verifying keystore..."
keytool -list -keystore "$KEYSTORE" -storepass "$PASS" -storetype PKCS12

echo ""
echo "🚀 Running security scans..."
# Run security scans
if [ -f "scripts/scan.sh" ]; then
    chmod +x scripts/scan.sh
    ./scripts/scan.sh
else
    echo "⚠️  scan.sh not found, skipping security scans"
fi

echo ""
echo "🎉 Development environment ready!"
echo "💡 Start the application with: ./mvnw spring-boot:run"
echo "🌐 Access at: https://localhost:8443"
echo ""
echo "⚠️  IMPORTANT: This is a development keystore only!"
echo "   For production, use Let's Encrypt, AWS Certificate Manager, or corporate PKI"
echo "   Never commit private keys to version control!"
