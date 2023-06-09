#!/bin/bash

#docker_iter.sh aquarius-api 9023:9023 prod 9024:9024
projectName=$1
if [ -z "$projectName" ]; then
  echo "you must input a projectName"
  exit 0
fi

ports=$2
if [ -z "$ports" ]; then
  echo "you must input a ports"
  exit 0
fi

envType=$3
if [ -z "$envType" ]; then
  echo "you must input a envType"
  exit 0
fi

dPorts=$4
if [ -z "$dPorts" ]; then
  echo "you must input a dPorts"
  exit 0
fi

dockerhub_url="registry.cn-hangzhou.aliyuncs.com"
namespace="my_repository"
docker_logs="/www/docker_logs/$projectName:/logs/$projectName"
echo "$docker_logs"
imageName=$dockerhub_url/$namespace/$projectName

containerIdOld=$(docker ps -a | grep -w "${projectName}" | awk '{print $1}')
imageNameOld=$(docker ps -a | grep -w "${projectName}" | awk '{print $2}')
#读取镜像tag
# shellcheck disable=SC2034
tagOld=$(docker images --filter=reference="${imageNameOld}" --format "{{.Tag}}")

# shellcheck disable=SC2034
tagNew=$((tagOld + 1))

echo "打印新tag"
echo $tagNew
#构建本次镜像
# shellcheck disable=SC2154
imageNameNew=${imageName}:${tagNew}
echo "$imageNameNew"
#构建镜像
# shellcheck disable=SC2154
docker build -t "${imageNameNew}" --build-arg envType="${envType}" .
# shellcheck disable=SC2181
if [ $? != 0 ]; then
  echo "新构建镜像失败"
  exit 2
fi
echo "构建镜像成功"

#关闭上次容器
containerIdOld=$(docker ps -aq --filter name="${projectName}")
if [ "$containerIdOld" != "" ]; then
  #停掉容器
  docker stop "$containerIdOld"
  #删除容器
  # shellcheck disable=SC2086
  docker rm $containerIdOld
  echo "停止删除旧容器成功"
else
  echo "旧容器不存在"
fi

#启动本次容器

# 启动新容器
# --net=common_net --restart=always
docker run -di --name "$projectName" --net=common_net -v "$docker_logs" -v /etc/localtime:/etc/localtime:ro --restart=always -p "$ports" -p "$dPorts" "$imageNameNew"
# shellcheck disable=SC2181
if [ $? == 0 ]; then
  echo "新容器启动成功"
  if [ "$imageNameOld" != "" ]; then
    docker rmi -f "$imageNameOld"
    if [ $? == 0 ]; then
      echo "删除旧镜像成功"
    else
      echo "删除旧镜像失败"
    fi
    exit 0
  else
    echo "旧镜像不存在"
  fi

else
  echo "新容器启动失败"
  containerIdNew=$(docker ps -a | grep -w "${projectName}" | awk '{print $1}')
  #删除新容器
  if [ "$containerIdNew" != "" ]; then
    #停掉容器
    docker stop "$containerIdNew"
    docker rm "$containerIdNew"
    echo "停止删除新容器成功"
    #启动旧容器
    # 启动新容器
    # --net=common_net --restart=always
    docker run -di --name "$projectName" --net=common_net -v "$docker_logs" -v /etc/localtime:/etc/localtime:ro --restart=always -p "$ports" -p "$dPorts" "$imageNameOld"
    if [ $? == 0 ]; then
      echo "部署回滚成功"
    else
      echo "部署回滚失败"
      exit 1
    fi
    #删除旧镜像
    docker rmi -f "$imageNameNew"
    if [ $? == 0 ]; then
      echo "删除新镜像成功"
    else
      echo "删除新镜像失败"
    fi
  fi
fi
