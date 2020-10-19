# team-Rental

# ubuntu update
sudo apt-get update 명령 후 
sudo apt-get upgrade 명령을 통해 업데이트를 하고 사용


# unzip 설치 
sudo apt install unzip

출처: <https://itsfoss.com/unzip-linux/> 


# AWS CLI 설치 (버전2)
현재 AWS CLI가 설치되어 있는 경우 설치한 버전을 확인합니다.

aws --version
설치가 되어 있으면 설치과정 Skip

버전 1.18.143 이상 또는 버전 2.0.50 이상이 설치되어 있지 않으면 AWS CLI 버전 2를 설치합니다. 다른 설치 옵션을 확인하거나 현재 설치된 버전 2를 업그레이드하려면 Linux에서 AWS CLI 버전 2 업그레이드를 참조하십시오.

sudo curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"
sudo unzip awscliv2.zip
sudo ./aws/install


# AWS EKS 클러스터 생성
eksctl create cluster --name skccuser02-team --version 1.17 --nodegroup-name standard-workers --node-type t3.medium --nodes 3 --nodes-min 1 --nodes-max 3


