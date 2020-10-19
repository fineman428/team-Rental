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


### 설치 eksctl
Linux에서 curl을 사용하여 eksctl을 설치하거나 업그레이드하려면
다음 명령으로 eksctl 최신 릴리스를 다운로드하여 압축 해제합니다.

sudo curl --silent --location "https://github.com/weaveworks/eksctl/releases/latest/download/eksctl_$(uname -s)_amd64.tar.gz" | tar xz -C /tmp
 . 압축 해제된 이진 파일을 /usr/local/bin으로 옮깁니다.

sudo mv /tmp/eksctl /usr/local/bin
다음 명령으로 설치가 제대로 되었는지 테스트합니다.

eksctl version

### kuberctl  설치 work flowy
apt-get update && apt-get install -y apt-transport-https
curl -s https://packages.cloud.google.com/apt/doc/apt-key.gpg | apt-key add -
echo "deb https://apt.kubernetes.io/ kubernetes-xenial main" | tee -a /etc/apt/sources.list.d/kubernetes.list
  . " 문자 주의 , 실제 입력된 값에는 " 없이 파일이 등록되어 있어야 함 
  . kubernetes.list 잘못 생성된 경우 경로 폴더에 접근하여 sudo nano 로 편집
 . work flowy 에서 붙여 넣을시 중간에 ? 문자 생성 확인  후 제거 
apt-get update
apt-get install -y kubectl


# AWS EKS 클러스터 생성
eksctl create cluster --name skccuser02-team --version 1.17 --nodegroup-name standard-workers --node-type t3.medium --nodes 3 --nodes-min 1 --nodes-max 3


# App gir 클론 받기
cd ~
mkdir project
git clone https://github.com/fineman428/team-Rental.git
git clone https://github.com/fineman428/team-Product.git
git clone https://github.com/fineman428/team-Information.git
git clone https://github.com/fineman428/team-gateway.git
git clone https://github.com/fineman428/team-Delivery.git


# 메이븐 lib 받기 
각 application 폴더별로 들어가서 
mvn package


# ECR Repository 만들기

# team-Delivery
Cd team-Delivery
aws ecr create-repository --repository-name skccuser02-delivery --image-scanning-configuration scanOnPush=true --region  ap-northeast-1
docker login --username AWS -p $(aws ecr get-login-password --region ap-northeast-1) 496278789073.dkr.ecr.ap-northeast-1.amazonaws.com/skccuser02-delivery
docker build -t 496278789073.dkr.ecr.ap-northeast-1.amazonaws.com/skccuser02-delivery:v1 .
docker push 496278789073.dkr.ecr.ap-northeast-1.amazonaws.com/skccuser02-delivery:v1

#team-information
Cd team-information
aws ecr create-repository --repository-name skccuser02-information --image-scanning-configuration scanOnPush=true --region  ap-northeast-1
docker login --username AWS -p $(aws ecr get-login-password --region ap-northeast-1) 496278789073.dkr.ecr.ap-northeast-1.amazonaws.com/skccuser02-information
docker build -t 496278789073.dkr.ecr.ap-northeast-1.amazonaws.com/skccuser02-information:v1 .
docker push 496278789073.dkr.ecr.ap-northeast-1.amazonaws.com/skccuser02-information:v1

# team-product
Cd team-product
aws ecr create-repository --repository-name skccuser02-product --image-scanning-configuration scanOnPush=true --region  ap-northeast-1
docker login --username AWS -p $(aws ecr get-login-password --region ap-northeast-1) 496278789073.dkr.ecr.ap-northeast-1.amazonaws.com/skccuser02-product
docker build -t 496278789073.dkr.ecr.ap-northeast-1.amazonaws.com/skccuser02-product:v1 .
docker push 496278789073.dkr.ecr.ap-northeast-1.amazonaws.com/skccuser02-product:v1

## team-Rental
cd team-Rental
aws ecr create-repository --repository-name skccuser02-rental --image-scanning-configuration scanOnPush=true --region  ap-northeast-1
docker login --username AWS -p $(aws ecr get-login-password --region ap-northeast-1) 496278789073.dkr.ecr.ap-northeast-1.amazonaws.com/skccuser02-rental
docker build -t 496278789073.dkr.ecr.ap-northeast-1.amazonaws.com/skccuser02-rental:v1 .
docker push 496278789073.dkr.ecr.ap-northeast-1.amazonaws.com/skccuser02-rental:v1
-메시지 no basic auth credentials

# team-gateway
Cd team-gateway
aws ecr create-repository --repository-name skccuser02-gateway --image-scanning-configuration scanOnPush=true --region  ap-northeast-1
docker login --username AWS -p $(aws ecr get-login-password --region ap-northeast-1) 496278789073.dkr.ecr.ap-northeast-1.amazonaws.com/skccuser02-gateway
docker build -t 496278789073.dkr.ecr.ap-northeast-1.amazonaws.com/skccuser02-gateway:v1 .
docker push 496278789073.dkr.ecr.ap-northeast-1.amazonaws.com/skccuser02-gateway:v1



# 네임스페이스 생성 
kubectl create namespace team-rent


# nano 설치 
apt-get install nano


### 헬름설치
EKS 클러스터 구성후 설치 가능

helm version
# 설치
sudo curl https://raw.githubusercontent.com/kubernetes/helm/master/scripts/get | bash

## Helm 초기화 설정
# helm 의 설치관리자를 위한 시스템 사용자 생성
kubectl --namespace kube-system create sa tiller
# 계정 롤 만들기
kubectl create clusterrolebinding tiller --clusterrole cluster-admin --serviceaccount=kube-system:tiller

helm init --service-account tiller
kubectl patch deploy --namespace kube-system tiller-deploy -p '{"spec":{"template":{"spec":{"serviceAccount":"tiller"}}}}'
helm repo add incubator http://storage.googleapis.com/kubernetes-charts-incubator
helm repo update

# helm으로 kafka 설치
- namespace
helm install --name my-kafka --namespace kafka incubator/kafka



## 팀 과제 k8s deploy 띄우기 (no yaml )
# rental 
# 올린 이미지를 통해 container 생성
kubectl create deploy rental --image=496278789073.dkr.ecr.ap-northeast-1.amazonaws.com/skccuser02-rental:v1 -n team-rent
kubectl expose deploy rental --port=8080 -n team-rent

# product 
kubectl create deploy product --image=496278789073.dkr.ecr.ap-northeast-1.amazonaws.com/skccuser02-product:v1 -n team-rent
kubectl expose deploy product --port=8080 -n team-rent
# information
kubectl create deploy information --image=496278789073.dkr.ecr.ap-northeast-1.amazonaws.com/skccuser02-team-information:v1 -n team-rent
kubectl expose deploy information --port=8080 -n team-rent
# delivery
kubectl create deploy delivery --image=496278789073.dkr.ecr.ap-northeast-1.amazonaws.com/skccuser02-delivery:v1 -n team-rent
kubectl expose deploy delivery --port=8080 -n team-rent
# gateway
kubectl create deploy gateway --image=496278789073.dkr.ecr.ap-northeast-1.amazonaws.com/skccuser02-gateway:v1 -n team-rent
kubectl expose deploy gateway --type=LoadBalancer --port=8080 -n team-rent

kubectl delete deploy rental -n team-rent
kubectl delete service rental -n team-rent


======================
서비스 테스트 - team ver 2 
======================
## 데이터 조회
http GET http://localhost:8081/products/1

http GET http://product:8080/product
http GET http://product:8080/products

http http://Product:8080
http http://product:8080
http http://Rental:8080
http http://Delivery:8080
http http://Information:8080
http http://gateway:8080
