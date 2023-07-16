@echo off
chcp 65001
title wd_label
echo 设置当前目录为工作目录
cd /d %~dp0

setlocal enabledelayedexpansion
set port=8080
echo "开始查找端口%port%"
for /f "tokens=1-5" %%a in ('netstat -ano ^| find ":%port%"') do (
    if "%%e%" == "" (
        set pid=%%d
    ) else (
         set pid=%%e
    )
    echo !pid!
)
if NOT "!pid!" == "" (
  echo "查找到端口%port%进程"+!pid!+"，开始杀进程"
   taskkill /f /pid !pid!
) else (
  echo "未查找到端口%port%,结束"
)

java -javaagent:app-encrypted.jar -jar -Xms512M -Xmx512M -XX:PermSize=256M -XX:MaxPermSize=512M -XX:+HeapDumpOnOutOfMemoryError -XX:+PrintGCDateStamps  -XX:+PrintGCDetails -XX:NewRatio=1 -XX:SurvivorRatio=30 -XX:+UseParallelGC -XX:+UseParallelOldGC app-encrypted.jar --logging.config=./config/logback.xml


pause
