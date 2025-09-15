#!/usr/bin/env bash
set -euo pipefail

# Security scanning script for Artemis Financial
# Runs OWASP Dependency-Check and SpotBugs/FindSecBugs

echo "🔍 Starting security scans for Artemis Financial..."

# Create reports directory
mkdir -p reports

# Build the project first
echo "🔨 Building project..."
./mvnw -q -DskipTests package

# Run OWASP Dependency-Check
echo "🛡️  Running OWASP Dependency-Check..."
./mvnw -q org.owasp:dependency-check-maven:check -Dformat=ALL

# Run SpotBugs with FindSecBugs
echo "🐛 Running SpotBugs with FindSecBugs..."
./mvnw -q spotbugs:check -Dspotbugs.failOnError=true || {
    echo "⚠️  SpotBugs found issues (this is expected for security scanning)"
    echo "   Check the report for details"
}

# Copy reports to reports directory
echo "📋 Copying reports to reports/ directory..."

# Copy dependency check reports
if [ -f "target/dependency-check-report.html" ]; then
    cp -f target/dependency-check-report.html reports/
    echo "✅ OWASP Dependency-Check HTML report copied"
fi

if [ -f "target/dependency-check-report.json" ]; then
    cp -f target/dependency-check-report.json reports/
    echo "✅ OWASP Dependency-Check JSON report copied"
fi

if [ -f "target/dependency-check-report.xml" ]; then
    cp -f target/dependency-check-report.xml reports/
    echo "✅ OWASP Dependency-Check XML report copied"
fi

# Copy SpotBugs reports
if [ -f "target/spotbugsXml.xml" ]; then
    cp -f target/spotbugsXml.xml reports/
    echo "✅ SpotBugs XML report copied"
fi

if [ -f "target/spotbugs.html" ]; then
    cp -f target/spotbugs.html reports/
    echo "✅ SpotBugs HTML report copied"
fi

# List generated reports
echo ""
echo "📊 Security scan reports generated:"
ls -la reports/

echo ""
echo "🔍 Report locations:"
echo "   📄 OWASP Dependency-Check: reports/dependency-check-report.html"
echo "   🐛 SpotBugs/FindSecBugs: reports/spotbugsXml.xml"
echo ""

# Check for critical vulnerabilities
echo "🚨 Checking for critical vulnerabilities..."

# Check dependency check results
if [ -f "reports/dependency-check-report.xml" ]; then
    CRITICAL_COUNT=$(grep -c "severity=\"CRITICAL\"" reports/dependency-check-report.xml || echo "0")
    HIGH_COUNT=$(grep -c "severity=\"HIGH\"" reports/dependency-check-report.xml || echo "0")
    
    echo "📈 Vulnerability Summary:"
    echo "   🔴 Critical: $CRITICAL_COUNT"
    echo "   🟠 High: $HIGH_COUNT"
    
    if [ "$CRITICAL_COUNT" -gt 0 ] || [ "$HIGH_COUNT" -gt 0 ]; then
        echo ""
        echo "⚠️  WARNING: Critical or High severity vulnerabilities found!"
        echo "   Please review the reports and address these issues before deployment."
        echo "   Open reports/dependency-check-report.html for details."
    else
        echo "✅ No critical or high severity vulnerabilities found!"
    fi
fi

# Check SpotBugs results
if [ -f "reports/spotbugsXml.xml" ]; then
    BUG_COUNT=$(grep -c "<BugInstance" reports/spotbugsXml.xml || echo "0")
    echo "🐛 SpotBugs found $BUG_COUNT potential issues"
    
    if [ "$BUG_COUNT" -gt 0 ]; then
        echo "   Review reports/spotbugsXml.xml for details"
    fi
fi

echo ""
echo "🎉 Security scanning completed!"
echo "💡 Next steps:"
echo "   1. Review the generated reports"
echo "   2. Address any critical or high severity issues"
echo "   3. Re-run scans after fixes"
echo "   4. Commit reports to version control for tracking"
echo ""
echo "📚 For more information, see docs/Verification.md"
