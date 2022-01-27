#!/bin/sh
./mvnw clean
rm *.zip
mkdir cleancode
cp -R ch* cleancode
cd cleancode
find . -type f \( -name "*.html" -o -name "*.java" -o -name "*.js" -o -name "*.ts" -o -name "*.xml"  \) | xargs sed -i '' '/[tag|end]\:\:.*\[\]/d'
zip -r ../ch01.zip ch01
zip -r ../ch02.zip ch02
zip -r ../ch03.zip ch03
zip -r ../ch04.zip ch04
zip -r ../ch05.zip ch05
zip -r ../ch06.zip ch06
zip -r ../ch07.zip ch07
zip -r ../ch08.zip ch08
zip -r ../ch12.zip ch12
zip ../sia-examples.zip ../ch*.zip
cd ..
rm -rf cleancode
