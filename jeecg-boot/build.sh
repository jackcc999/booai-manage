#!/bin/bash

# 脚本执行异常时停止往下执行其他脚本
set -e

# 显示执行的命令
# set -v

branch=$1
env=$2

if [[ "$branch" == "" ]]; then
    branch="master"
fi

if [[ "$env" == "" ]];then
    env="prod"
fi

# 获取当前分支名
currBranch=`git branch | grep "*"`
currBranch=`echo ${currBranch:2}`
# 这是一个tag分支 (HEAD detached at release_v2.0.0_20240201)
if [[ $currBranch =~ "HEAD detached" ]];then
    currBranch=`echo ${currBranch:18} | sed 's/)//'`
fi


# 当前目录
curDir="$(cd "$(dirname "$0")" && pwd)"

echo -e "1.进入项目主目录 ${curDir}"
cd $curDir

echo -e "\n2.切换到${branch}分支"
if [[ "$branch" != "$currBranch" ]]; then
    git checkout $branch
fi


echo -e "\n3.获取Git最新代码"
git pull

log=`git log -1 --pretty=format:'[%an][%ad][%H] %s' --date=format:"%Y-%m-%d %H:%M:%S"`
echo -e "\n4.最新一条提交日志: ${log}"


# 获取提交信息
git log -1 --pretty=format:'{"commitHash":"%H","commitAuthor":"%an","commitMessage":"%s","commitTime":"%ad","buildBranch":"BRANCH"}' --date={local,format:'%Y-%m-%d %H:%M:%S'} | sed "s/BRANCH/$branch/g" > commit_info.json



echo -e "\n5.mvn编译打包[ Git分支: $branch, 环境: $env ]\n"
sleep 2


mvn clean package -P $env -Dmaven.test.skip=true

rm -f commit_info.json


echo -e "\n6.Git分支还原到${currBranch}"
if [[ "$branch" != "$currBranch" ]]; then
    git checkout $currBranch
fi


