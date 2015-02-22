#!/bin/bash
if [ $1 = 'release' ]; then
	find ./src/main/java -name "*.java" -exec \
	sed -i '' -e 's/\/\/.*<#DEBUG_AREA>/\/\*<#DEBUG_OFF>/g' -e 's/\/\/.*<\/#DEBUG_AREA>/<\/#DEBUG_OFF>\*\//g' -e 's/ASDebuggerMacroLog\./\/\/ASDebuggerMacroLog\./g' {} +
	echo "DEBUGGER SCRIPT: RELEASE MODE"
fi

if [ $1 = 'debug' ]; then
	find ./src/main/java -name "*.java" -exec \
	sed -i '' -e 's/\/\*<#DEBUG_OFF>/\/\/<#DEBUG_AREA>/g' -e 's/<\/#DEBUG_OFF>\*\//\/\/<\/#DEBUG_AREA>/g' -e 's/\/\/ASDebuggerMacroLog\./ASDebuggerMacroLog\./g' {} +
	echo "DEBUGGER SCRIPT: DEBUG MODE"
fi

echo "DEBUGGER SCRIPT: DONE EDITING"
