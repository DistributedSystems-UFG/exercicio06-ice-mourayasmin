#!/bin/bash

echo "Compiling Java files..."
javac -cp ".:ice-3.7.11.jar" Client.java Server.java Demo/*.java

if [ $? -eq 0 ]; then
    echo "Compilation successful!"
else
    echo "Compilation failed!"
    exit 1
fi
