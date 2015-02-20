#!/bin/bash
if [ $1 = 'release' ]; then
	find ./src/main/java -name "*.java" -exec \
	sed -i '' -e 's/\/\/<#DEBUG>/\/\*<#DEBUG_OFF>/g' -e 's/\/\/<\/#DEBUG>/<\/#DEBUG_OFF>\*\//g' {} +
	echo "DEBUGGER SCRIPT: RELEASE MODE"
fi

if [ $1 = 'debug' ]; then
	find ./src/main/java -name "*.java" -exec \
	sed -i '' -e 's/\/\*<#DEBUG_OFF>/\/\/<#DEBUG>/g' -e 's/<\/#DEBUG_OFF>\*\//\/\/<\/#DEBUG>/g' {} +
	echo "DEBUGGER SCRIPT: DEBUG MODE"
fi

echo "DEBUGGER SCRIPT: DONE EDITING"
